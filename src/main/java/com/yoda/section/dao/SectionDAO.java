package com.yoda.section.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.section.model.Section;
import com.yoda.util.Constants;

@Repository
public class SectionDAO extends BaseDAO<Section> {
	private static final String GET_SECTION_BY_SITEID = "from section in class Section where siteId = ? and sectionParentId = 0 order by seqNum";

	private static final String GET_SECTION_BY_SECTIONPARENTID = "from Section where sectionParentId = ?";

	private static final String GET_SECTION_BY_SITEID_AND_SECTIONPARENTID = "from section in class Section where siteId = ? and sectionParentId = ? order by seqNum";

	private static final String GET_SECTION_BY_SITEID_AND_SECTIONPARENTID_PUBLISHED = "from section in class Section where siteId = ? and sectionParentId = ? and published = ? order by seqNum";

	private static final String GET_SECTION_BY_SITEID_AND_NATURALKEY = "from Section where siteId = ? and sectionNaturalKey = ?";

	private static final String SELECT_MAX_SEQNUM_BY_SITEID_AND_SECTIONPARENTID = "select max(seqNum) from Section where  siteId = ? and sectionParentId = ?";

	private static final String UPDATE_MENU_SEQNUM = "update Section set seqNum = seqNum + 1 where siteId = ? and Section = ? and seqNum >= ?";

	public int selectMaxSeqNumBySiteIdParentMenuId(long siteId, long sectionParentId) {
		List<Integer> seqNum = (List<Integer>)getHibernateTemplate().find(SELECT_MAX_SEQNUM_BY_SITEID_AND_SECTIONPARENTID, siteId, sectionParentId);

		if (seqNum.get(0) == null) {
			return 0;
		}
		else {
			return seqNum.get(0);
		}
	}

	public void updateSeqNum(long siteId, long sectionParentId, int seqNum) {
		Query query = createQuery(UPDATE_MENU_SEQNUM);

		query.setLong("siteId", siteId);
		query.setLong("menuParentId", sectionParentId);
		query.setInteger("seqNum", seqNum);

		query.executeUpdate();

		getSession().flush();
	}

	public Section load(long siteId, long sectionId)
		throws SecurityException, Exception {

		Section section = getById(sectionId);

		if (section.getSiteId() != siteId) {
			throw new SecurityException();
		}

		return section;
	}

	public Section getSectionBySiteId_SectionId(long siteId, long sectionId) {
		Section seciton = getById(sectionId);

		if (seciton.getSiteId() != siteId) {
			throw new SecurityException();
		}
		return seciton;
	}

	public Section getSectionBySiteId(long siteId) {
		List<Section> sections = (List<Section>)getHibernateTemplate().find(GET_SECTION_BY_SITEID, siteId);

		if (sections.size() == 0) {
			return null;
		}
		else {
			return sections.get(0);
		}
	}

	public Section getSectionBySiteId_NaturalKey(long siteId, String naturalKey) {
		List<Section> sections = (List<Section>)getHibernateTemplate().find(GET_SECTION_BY_SITEID_AND_NATURALKEY, siteId, naturalKey);

		if (sections.size() == 0) {
			return null;
		}
		else {
			return sections.get(0);
		}
	}

	public List<Section> getSectionBySectionParentId(long sectionParentId) {
		List<Section> sections = (List<Section>)getHibernateTemplate().find(GET_SECTION_BY_SECTIONPARENTID, sectionParentId);

		if (sections.size() == 0) {
			return new ArrayList<Section>();
		}
		else {
			return sections;
		}
	}

	public List<Section> getBySI_SPI(long siteId, long sectionParentId) {
		List<Section> sections = (List<Section>)getHibernateTemplate().find(GET_SECTION_BY_SITEID_AND_SECTIONPARENTID, siteId, sectionParentId);

		if (sections.size() == 0) {
			return new ArrayList<Section>();
		}
		else {
			return sections;
		}
	}

	public List<Section> getBySI_SPI_Published(long siteId, long sectionParentId, char pulished) {
		List<Section> sections = (List<Section>)getHibernateTemplate().find(GET_SECTION_BY_SITEID_AND_SECTIONPARENTID_PUBLISHED, siteId, sectionParentId, pulished);

		if (sections.size() == 0) {
			return new ArrayList<Section>();
		}
		else {
			return sections;
		}
	}

	public List<Section> search(String sql, String sortBy) {
		String value = sortBy;

		if (value == null || value.length() == 0) {
			value = String.valueOf(Constants.SECTION_SORT_DESC_ASC);
		}

		char sortByValue = value.charAt(0);

		switch (sortByValue) {
			case Constants.SECTION_SORT_DESC_ASC:
				sql += "order by description";
				break;
			case Constants.SECTION_SORT_DESC_DSC:
				sql += "order by description desc";
				break;
			case Constants.SECTION_SORT_PRICE_ASC:
				sql += "order by type desc, price asc";
				break;
			case Constants.SECTION_SORT_PRICE_DSC:
				sql += "order by type desc, price desc";
				break;
		}

		SQLQuery sqlQuery = getSession().createSQLQuery(sql);

		sqlQuery.addScalar("type", StandardBasicTypes.STRING);
		sqlQuery.addScalar("objectId", StandardBasicTypes.STRING);
		sqlQuery.addScalar("description", StandardBasicTypes.STRING);
		sqlQuery.addScalar("price", StandardBasicTypes.STRING);

		return (List<Section>)sqlQuery.list();
	}
}
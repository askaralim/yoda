package com.yoda;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;

public class BaseDAO<T> {

	private Class<T> entityClass;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	/**
	 * reflection for child T type
	 */
	public BaseDAO() {
		Type genType = getClass().getGenericSuperclass();

		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

		entityClass = (Class)params[0];
	}

	public T getById(Serializable id) {
		return (T)getHibernateTemplate().get(entityClass, id);
	}


	public List<T> getAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	public List find(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	public List find(String hql, Object... params) {
		return this.getHibernateTemplate().find(hql, params);
	}

	public void initialize(Object entity) {
		this.getHibernateTemplate().initialize(entity);
	}

//	/**
//	 * pagination, use hql
//	 * 
//	 * @param pageNo page number start from 1
//	 */
//	public Page pagedQuery(String hql, int pageNo, int pageSize,
//			Object... values) {
//		Assert.hasText(hql);
//		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
//		// Count query
//		String countQueryString = " select count (*) "
//				+ removeSelect(removeOrders(hql));
//		List countlist = getHibernateTemplate().find(countQueryString, values);
//		long totalCount = (Long) countlist.get(0);
//
//		if (totalCount < 1)
//			return new Page();
//		// 
//		int startIndex = Page.getStartOfPage(pageNo, pageSize);
//		Query query = createQuery(hql, values);
//		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
//				.list();
//
//		return new Page(startIndex, totalCount, pageSize, list);
//	}

	/**
	 * 
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);

		Query query = getSession().createQuery(hql);

		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		return query;
	}

	/**
	 * remove select from hql, no consider union, use for pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);

		int beginPos = hql.toLowerCase().indexOf("from");

		Assert.isTrue(beginPos != -1, " hql : " + hql
			+ " must has a keyword 'from'");

		return hql.substring(beginPos);
	}

	/**
	 * remove orderby from hql，use for pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);

		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
			Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(hql);

		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			m.appendReplacement(sb, "");
		}

		m.appendTail(sb);

		return sb.toString();
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public Session getSession() {
		return SessionFactoryUtils.getSession(
			hibernateTemplate.getSessionFactory(), true);
	}
}
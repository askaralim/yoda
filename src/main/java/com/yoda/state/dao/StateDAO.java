//package com.yoda.state.dao;
//
//import java.util.List;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//
//import com.yoda.BaseDAO;
//import com.yoda.state.model.State;
//
////@Repository
//public class StateDAO extends BaseDAO<State> {
//	private static final String FINDER_COLUNN_SITE_ID = "FROM State state left join fetch state.country country where state.siteId =? order by country.countryId, state.stateName";
//	private static final String FINDER_COLUNN_SITE_ID_STATE_CODE = "FROM State WHERE siteId =? AND stateCode =?";
//
//	public List<State> getBySiteId(long siteId) {
//		return find(FINDER_COLUNN_SITE_ID, siteId);
//	}
//
//	public State getByS_SC(long siteId, String stateCode) {
//		Session session = getSession();
//
//		Query query = session.createQuery(FINDER_COLUNN_SITE_ID_STATE_CODE);
//
//		query.setLong(0, siteId);
//		query.setString(1, stateCode);
//
//		List list = query.list();
//
//		State state = null;
//
//		if (list.size() > 0) {
//			state = (State)list.iterator().next();
//		}
//
//		return state;
//	}
//
////	public State load(long siteId, long stateId)
////			throws SecurityException, Exception {
////		Session session = HibernateConnection.getInstance().getCurrentSession();
////		State state = (State) session.get(State.class, stateId);
////		if (state.getSiteId() != siteId) {
////			throw new SecurityException();
////		}
////		return state;
////	}
////
////	public State loadByStateName(String siteId, String stateName)
////			throws SecurityException, Exception {
////		Session session = HibernateConnection.getInstance().getCurrentSession();
////		Query query = session
////				.createQuery("from State where siteId = :siteId and stateName = :stateName");
////		query.setString("siteId", siteId);
////		query.setString("stateName", stateName);
////		Iterator iterator = query.iterate();
////		if (iterator.hasNext()) {
////			return (State) iterator.next();
////		}
////		return null;
////	}
////
////	public State loadByStateCode(String siteId, String stateCode)
////			throws SecurityException, Exception {
////		Session session = HibernateConnection.getInstance().getCurrentSession();
////		Query query = session
////				.createQuery("from State where siteId = :siteId and stateCode = :stateCode");
////		query.setString("siteId", siteId);
////		query.setString("stateCode", stateCode);
////		Iterator iterator = query.iterate();
////		if (iterator.hasNext()) {
////			return (State) iterator.next();
////		}
////		return null;
////	}
//}
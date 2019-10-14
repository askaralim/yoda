package com.taklip.yoda.mapper;

import java.io.Serializable;

public interface BaseMapper<T> {
//	public T get(String id);

	T getById(Serializable id);

	T get(T entity);

	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
//	public List<T> findList(T entity);
//
//	public List<T> findAllList(T entity);

	int insert(T entity);

	int update(T entity);

	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	int delete(T entity);
}

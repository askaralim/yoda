package com.taklip.yoda.service.impl;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.UserFolloweeMapper;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.UserFollowee;
import com.taklip.yoda.service.UserFolloweeService;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserFolloweeServiceImpl implements UserFolloweeService {
	@Autowired
	UserFolloweeMapper userFolloweeMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private IdService idService;

	@Override
	public UserFollowee getUserFollowee(Long id) {
		return userFolloweeMapper.getById(id);
	}

	@Override
	public UserFollowee save(UserFollowee userFollowee) {
		userFollowee.setId(idService.generateId());
		userFollowee.setCreateDate(new Date());

		userFolloweeMapper.insert(userFollowee);

		return userFollowee;
	}

	@Override
	public void deleteUserFollowee(Long userId, Long followeeId) {
		userFolloweeMapper.deleteUserFollowee(userId, followeeId);
	}

	@Override
	public List<UserFollowee> getUserFollowees(Long userId) {
		return userFolloweeMapper.getUserFollowees(userId);
	}

	@Override
	public int getUserFolloweeCount(Long userId) {
		return userFolloweeMapper.getUserFolloweeCount(userId).get(0);
	}

	@Override
	public Pagination<UserFollowee> getUserFollowees(Long userId, RowBounds rowBounds) {
		List<UserFollowee> followees = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.UserFolloweeMapper.getUserFollowers", userId, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.UserFolloweeMapper.getUserFolloweeCount", userId);

		Pagination<UserFollowee> page = new Pagination<>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), followees);

		return page;
	}
}
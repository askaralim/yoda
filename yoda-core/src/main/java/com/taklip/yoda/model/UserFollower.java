package com.taklip.yoda.model;

/*
 * User and Follower whom he/she follows
 */
public class UserFollower extends BaseEntity {
	private Long id;
	private Long userId;
	private Long followerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(Long followerId) {
		this.followerId = followerId;
	}
}
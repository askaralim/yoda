package com.taklip.yoda.model;

/*
 * User and the Followee who follows the User
 */
public class UserFollowee extends BaseEntity {
	private Long id;
	private Long userId;
	private Long followeeId;

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

	public Long getFolloweeId() {
		return followeeId;
	}

	public void setFolloweeId(Long followeeId) {
		this.followeeId = followeeId;
	}
}
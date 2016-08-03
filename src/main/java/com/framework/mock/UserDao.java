package com.framework.mock;

public interface UserDao {
	/**
	 * 新增用戶
	 */
	void insertUser(User user);

	/**
	 * 查詢用戶
	 */
	User queryUser(Long id);
}
package com.paulandcode.dao;

import java.util.Map;

import com.paulandcode.entity.SysUserEntity;

/**
 * 用户
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午3:24:41
 */
public interface SysUserDao {

	/**
	 * 根据用户名查找用户
	 * 
	 * @param map
	 * @return
	 */
	SysUserEntity getUserByName(Map<String, String> map);

}
package com.paulandcode.entity;

import java.io.Serializable;

/**
 * 用户
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午3:26:11
 */
public class SysUserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

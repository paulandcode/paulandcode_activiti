package com.paulandcode.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数,支持分页
 * 
 * @author 黄建峰
 * @date 2017年11月1日 下午2:03:39
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	/**
	 * 当前页码
	 */
	private int page;
	/**
	 * 每页条数
	 */
	private int limit;

	public Query() {

	}

	public Query(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		this.page = Integer.parseInt(params.get("page").toString());
		this.limit = Integer.parseInt(params.get("limit").toString());
		this.put("offset", (page - 1) * limit);
		this.put("page", page);
		this.put("limit", limit);
	}

	public Query(int page, int limit) {
		this.page = page;
		this.limit = limit;
		// 分页参数
		this.put("offset", (page - 1) * limit);
		this.put("page", page);
		this.put("limit", limit);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
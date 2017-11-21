package com.paulandcode.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回的JSON对象
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午2:09:05
 */
public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public Result() {
		put("code", 1);
	}

	public static Result error() {
		return error(0, "未知异常,请联系管理员.");
	}

	public static Result error(String message) {
		return error(0, message);
	}

	public static Result error(int code, String message) {
		Result r = new Result();
		r.put("code", code);
		r.put("message", message);
		return r;
	}

	public Result remove(String key) {
		super.remove(key);
		return this;
	}

	public static Result ok(String message) {
		Result r = new Result();
		r.put("message", message);
		return r;
	}

	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}

	public static Result ok() {
		return new Result();
	}

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
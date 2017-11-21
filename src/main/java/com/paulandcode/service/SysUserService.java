package com.paulandcode.service;

import org.springframework.web.multipart.MultipartFile;

import com.paulandcode.utils.Result;

/**
 * 用户
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午3:26:39
 */
public interface SysUserService {

	/**
	 * 根据用户名字查找用户
	 * 
	 * @param name
	 * @return
	 */
	Result getUserByName(String name);

	/**
	 * 根据附件缓存路径上传附件
	 * 
	 * @param file
	 * @param baseURL
	 * @return
	 */
	Result fileUpload(MultipartFile[] file);

}
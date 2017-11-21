package com.paulandcode.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.paulandcode.service.SysUserService;
import com.paulandcode.utils.Result;

/**
 * 用户与附件上传
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午2:10:27
 */
@Controller
@RequestMapping("sys")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * controller不要写逻辑,只接收数据,调用service和返回信息(可以适当处理request中的参数,如:request.getParameter("parameter").toString())
	 * 不要把request传进service里,那样会破坏依赖分离的初衷
	 * 
	 * @param name
	 * @return 实体用户对象
	 */
	@RequestMapping("getUserByName")
	@ResponseBody
	public Result getUserByName(String name) {
		return sysUserService.getUserByName(name);
	}

	/**
	 * 附件上传
	 * 
	 * @param file
	 * @param request
	 * @return 附件地址
	 */
	@RequestMapping("fileUpload")
	@ResponseBody
	public Result fileUpload(MultipartFile[] file, HttpServletRequest request) {
		// 此处是处理参数,不算是处理业务逻辑
		return sysUserService.fileUpload(file);
	}

}
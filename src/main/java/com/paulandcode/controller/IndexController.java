package com.paulandcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午2:10:27
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String login() {
		return "index";
	}

}
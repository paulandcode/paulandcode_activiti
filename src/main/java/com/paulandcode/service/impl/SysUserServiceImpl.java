package com.paulandcode.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.paulandcode.dao.SysUserDao;
import com.paulandcode.service.SysUserService;
import com.paulandcode.utils.HttpContextUtils;
import com.paulandcode.utils.PropertiesLoader;
import com.paulandcode.utils.Result;

/**
 * 用户
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午3:26:27
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	public static PropertiesLoader loader = new PropertiesLoader("config/config.properties");

	private static Logger logger = Logger.getLogger(SysUserServiceImpl.class);

	@Autowired
	SysUserDao sysUserDao;

	@Override
	public Result getUserByName(String name) {
		System.out.println(loader.getConfig("content"));
		logger.info("perfect");
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("name", name);
		return Result.ok().put("user", sysUserDao.getUserByName(map));
	}

	@Override
	public Result fileUpload(MultipartFile[] file) {
		// http://localhost:8080/paulandcode_base/
		String basePath = HttpContextUtils.getHttpServletRequest().getRequestURL().toString()
				.split("sys/fileUpload")[0];
		// fileUpload/now.txt.txt
		String filePath = "fileUpload/" + file[0].getOriginalFilename();
		// D:/java/tomcat/webapps/paulandcode_base/
		String webPath = System.getProperty("webPath").replace("\\", "/");
		try {
			file[0].transferTo(new File(webPath + filePath));
		} catch (IllegalStateException e) {
			// 未捕获的异常会自己打印到控制台并由ExceptionHandler记日志和返回
			// 捕获了的异常一定要自己答应控制台,记日志并返回
			e.printStackTrace();
			logger.error(e.getMessage());
			return Result.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Result.error(e.getMessage());
		}
		logger.info("附件上传成功!");
		return Result.ok().put("fileURL", basePath + filePath);
	}

}
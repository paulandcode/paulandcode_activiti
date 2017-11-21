package com.paulandcode.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 未捕获异常拦截
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午2:58:57
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Result r = new Result();
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");

			// 自定义异常处理
			// if (ex instanceof RRException) {
			// r.put("code", ((RRException) ex).getCode());
			// r.put("msg", ((RRException) ex).getMessage());
			// r.put("tip", ((RRException) ex).getMessage());
			// }else
			// 调用持久层类的进行保存或更新时,主键或唯一性约束冲突异常处理
			if (ex instanceof DuplicateKeyException) {
				r = Result.error("数据库中已存在该记录");
			}
			// 权限异常处理
			// else if(ex instanceof AuthorizationException){
			// r = R.error("没有权限，请联系管理员授权").put("tip",
			// "没有权限，请联系管理员授权").put("content", null);
			// }
			// 其他未知异常
			else {
				r = Result.error();
			}

			// 记录异常日志
			logger.error(ex.getMessage(), ex);

			String json = JSON.toJSONString(r);
			response.getWriter().print(json);
		} catch (Exception e) {
			logger.error("ExceptionHandler 异常处理失败", e);
		}
		return new ModelAndView();
	}

}
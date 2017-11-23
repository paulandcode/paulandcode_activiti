package com.paulandcode.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户与附件上传
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午2:10:27
 */
@Controller
@RequestMapping("activiti")
public class SysUserController {

	@RequestMapping("startProcess")
	@ResponseBody
	public void startProcess() {
		// 使用自定义配置文件创建流程引擎,此处databaseSchemaUpdate属性值设为create-drop,创建流程引擎的同时会新建表
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/activiti.xml", "processEngineConfiguration")
				.buildProcessEngine();
		// 获取流程仓库Service
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 部署流程定义文件
		repositoryService.createDeployment().addClasspathResource("activiti/designer/leave.bpmn").deploy();
		// 验证已部署的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
		System.out.println(processDefinition.getKey());
		// 获取运行时Service
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 启动流程并返回流程实例
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave");
		System.out.println(processInstance);
		System.out.println("pid=" + processInstance.getId() + ",pdid=" + processInstance.getProcessDefinitionId());
		// 销毁流程引擎,此处databaseSchemaUpdate属性值设为create-drop,销毁流程引擎的同时会删表
		processEngine.close();
	}

}
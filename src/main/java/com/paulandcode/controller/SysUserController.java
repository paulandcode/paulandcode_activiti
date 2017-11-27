package com.paulandcode.controller;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public void startProcess() {
		// 使用自定义配置文件创建流程引擎,此处databaseSchemaUpdate属性值设为create-drop,创建流程引擎的同时会新建表
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/activiti.xml", "processEngineConfiguration")
				.buildProcessEngine();
		try {
			// 获取流程仓库Service
			RepositoryService repositoryService = processEngine.getRepositoryService();
			// 部署流程定义文件
			repositoryService.createDeployment().addClasspathResource("activiti/designer/leave.bpmn").deploy();
			// 验证已部署的流程定义
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
			// 输出流程定义的key
			System.out.println(processDefinition.getKey());
			// 获取运行时Service
			RuntimeService runtimeService = processEngine.getRuntimeService();
			// 添加流程变量
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("applyUser", "employee1");
			variables.put("days", 3);
			// 启动流程并返回流程实例
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
			// 输出流程实例
			System.out.println(processInstance);
			// 输出流程实例id,输出pdid(流程定义的key:版本号:流程定义的id)
			System.out.println("pid=" + processInstance.getId() + ",pdid=" + processInstance.getProcessDefinitionId());
			// 获取任务Service
			TaskService taskService = processEngine.getTaskService();
			// 查询组(deptLeader)的未签收任务
			Task taskOfDeptLeader = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
			// 输出任务名
			System.out.println(taskOfDeptLeader);
			// 签收任务,签收人id:leaderUser
			taskService.claim(taskOfDeptLeader.getId(), "leaderUser");
			// 添加流程变量
			variables = new HashMap<String, Object>();
			variables.put("approved", true);
			// 完成任务:通过审批
			taskService.complete(taskOfDeptLeader.getId(), variables);
			// 再次查询组的未签收任务
			taskOfDeptLeader = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
			System.out.println(taskOfDeptLeader);
			// 获取历史Service
			HistoryService historyService = processEngine.getHistoryService();
			// 统计已完成的流程实例数量
			long count = historyService.createHistoricProcessInstanceQuery().finished().count();
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 销毁流程引擎,此处databaseSchemaUpdate属性值设为create-drop,销毁流程引擎的同时会删表
			processEngine.close();
		}
	}

}
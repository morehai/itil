package com.wao.itil.action.api;

import org.ironrhino.core.metadata.AutoConfig;
import org.ironrhino.core.metadata.JsonConfig;
import org.ironrhino.core.struts.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wao.itil.service.TaskService;

/**
 * 接收客户端代理器提交上来的监控数据
 */
@AutoConfig
public class AgentAction extends BaseAction {

	private static final long serialVersionUID = 544037031911725466L;
	private static Logger logger = LoggerFactory.getLogger(AgentAction.class);

	// 返回的json格式消息
	private String msg;
	@Autowired
	private TaskService glancesTaskService;

	/**
	 * 校验客户端代理器的版本
	 */
	@JsonConfig
	public String checkVer() {
		logger.info(requestBody);
		return JSON;
	}

	/**
	 * 客户端代理器的设置
	 */
	@JsonConfig
	public String setting() {
		logger.info(requestBody);
		return JSON;
	}

	/**
	 * 客户端代理器日志
	 */
	@JsonConfig
	public String logs() {
		logger.info(requestBody);
		return JSON;
	}

	/**
	 * 客户端代理器数据的提交，手工测试入口
	 */
	public String post() {
		glancesTaskService.batchSyncServerInfoForTask();
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

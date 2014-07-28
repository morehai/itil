package com.wao.itil.action.api;

import java.io.IOException;

import org.ironrhino.core.metadata.AutoConfig;
import org.ironrhino.core.metadata.JsonConfig;
import org.ironrhino.core.struts.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wao.itil.service.support.GlancesService;

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
	private GlancesService glancesServer;

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
	 * 客户端代理器数据的提交
	 */
	@JsonConfig(root = "msg")
	public String post() {
		try {
			msg = glancesServer.client();
			logger.info(msg);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return JSON;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

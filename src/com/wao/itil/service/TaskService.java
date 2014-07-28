package com.wao.itil.service;

import java.io.IOException;

public interface TaskService {

	/**
	 * 通过单次监控任务配置获取到服务器相关的监控项信息
	 */
	public void batchSyncServerInfoForTask();

	/**
	 * 根据服务器IP地址和API方法名获取对应的信息
	 * 
	 * @param host
	 *            服务器地址
	 * @param method
	 *            API方法名
	 * @return 服务器运行时信息
	 * @throws IOException
	 */
	public String getServerInfoByAgent(String host, String method)
			throws IOException;

}

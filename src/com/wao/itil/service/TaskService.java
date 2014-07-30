package com.wao.itil.service;

import java.io.IOException;
import java.util.Map;

public interface TaskService {

	/**
	 * 通过单次监控任务配置获取到服务器相关的监控项信息
	 */
	public void batchSyncServerInfoForTask();

	/**
	 * 根据服务器IP地址和单一方法名获取API对应的信息
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

	/**
	 *  根据服务器IP地址和多个方法名获取API对应的信息列表
	 * @param host  服务器地址
	 * @param methods API方法名集合
	 * @return 服务器运行时信息集合
	 * @throws IOException
	 */
	public Map<String, String> getServerInfoByAgent(String host,
			String[] methods) throws IOException;
	
	/**
	 * 将单台服务器的多项运行时信息封装成持久对象入队
	 * @param host 服务器地址
	 * @param respMap 运行信息
	 */
	public void transferServerInfo(String host, Map<String,String> respMap);
}

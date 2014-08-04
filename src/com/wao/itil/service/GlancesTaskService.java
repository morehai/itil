package com.wao.itil.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.ironrhino.core.coordination.LockService;
import org.ironrhino.core.metadata.Trigger;
import org.ironrhino.core.util.ErrorMessage;
import org.ironrhino.core.util.HttpClientUtils;
import org.ironrhino.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wao.itil.model.Task;
import com.wao.itil.queue.RedisSimpleCoreMessageQueue;
import com.wao.itil.queue.RedisSimpleCpuMessageQueue;
import com.wao.itil.queue.RedisSimpleDiskioMessageQueue;
import com.wao.itil.queue.RedisSimpleMemoryMessageQueue;
import com.wao.itil.queue.RedisSimpleProcessMessageQueue;
import com.wao.itil.queue.RedisSimpleSystemMessageQueue;

/**
 * 任务通过Glances代理获取被监控服务器信息
 */
@Component
public class GlancesTaskService {

	@Autowired
	private LockService lockService;
	@Autowired
	private ExecutorService executorService;
	@Autowired
	private TaskManager taskManager;
	@Autowired
	private RedisSimpleCoreMessageQueue redisSimpleCoreMessageQueue;
	@Autowired
	private RedisSimpleCpuMessageQueue redisSimpleCpuMessageQueue;
	@Autowired
	private RedisSimpleDiskioMessageQueue redisSimpleDiskioMessageQueue;
	@Autowired
	private RedisSimpleMemoryMessageQueue redisSimpleMemoryMessageQueue;
	@Autowired
	private RedisSimpleProcessMessageQueue redisSimpleProcessMessageQueue;
	@Autowired
	private RedisSimpleSystemMessageQueue redisSimpleSystemMessageQueue;

	/**
	 * 根据服务器IP地址和多个方法名获取API对应的信息列表
	 * 
	 * @param host
	 *            服务器地址
	 * @param methods
	 *            API方法名集合
	 * @return 服务器运行时信息集合
	 * @throws IOException
	 */
	public Map<String, String> getServerInfoByAgent(String host,
			String[] methods) {
		if (methods == null || methods.length == 0) {
			return new HashMap<String, String>();
		}
		Map<String, String> respMap = new HashMap<String, String>();
		for (String method : methods) {
			try {
				respMap.put(method, getServerInfoByAgent(host, method));
			} catch (IOException e) {
				e.printStackTrace();
				//  TODO 将任务的服务器信息写入网络连接异常表中
				continue;
			}
		}
		return respMap;
	}

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
			throws IOException {

		if (StringUtils.isNotBlank(host)) {
			host = "http://" + host + ":61209/RPC2";
		}
		if (StringUtils.isNotBlank(method)) {
			host = "<methodName>" + method + "</methodName>";
		}

		String resp = HttpClientUtils.post(host, method);
		if (resp.indexOf("<string>") > 0) {
			resp = resp.substring(resp.indexOf("<string>") + 8,
					resp.indexOf("</string>"));
		} else {
			return null;
		}
		return resp;
	}

	private String lockName = "TaskGlancesServiceImpl.batchSyncServerInfoForTask";
	private int batchSyncSize = 5;

	/**
	 * 通过单次监控任务配置获取到服务器相关的监控项信息
	 */
	@Scheduled(cron = "5 * * * * ?")
	@Trigger
	public void batchSyncServerInfoForTask() {
		if (lockService.tryLock(lockName)) {
			try {
				List<Task> list = taskManager.findByUnExecuted();
				int index = 0;
				while (index < list.size()) {
					int acturalSize = Math.min(batchSyncSize, list.size()
							- index);
					final CountDownLatch cdl = new CountDownLatch(acturalSize);
					for (int i = 0; i < acturalSize; i++) {
						final Task task = list.get(index++);
						executorService.execute(new Runnable() {
							@Override
							public void run() {
								String[] methods = task
										.getServerMonitorsAsString().split(",");
								Map<String, String> respMap = getServerInfoByAgent(
										task.getHost(), methods);
								try {
									transferServerInfo(task, respMap);
								} catch (JsonParseException e) {
									e.printStackTrace();
								} catch (JsonMappingException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
									// TODO 将任务的服务器信息写入网络连接异常表中
								}
								cdl.countDown();
							}
						});
					}
					try {
						cdl.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} finally {
				lockService.unlock(lockName);
			}
		} else {
			throw new ErrorMessage("try lock failed");
		}
	}

	@PreDestroy
	public void destroy() {
		lockService.unlock(lockName);
	}

	/**
	 * 将单台服务器的多项运行时信息封装成持久对象入队
	 * 
	 * @param task
	 *            任务
	 * @param respMap
	 *            运行信息
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public void transferServerInfo(Task task, Map<String, String> respMap)
			throws JsonParseException, JsonMappingException, IOException {
		for (String methodName : respMap.keySet()) {
			switch (methodName) {
			// 1、将map中的JSON串转换成相应的对象；
			// 2、将对象放入消息队列发送；
			case "getCore":
				if (respMap.get("getCore") != null) {
					com.wao.itil.model.glances.Core coreGlances = JsonUtils
							.fromJson(respMap.get("getCore"),
									com.wao.itil.model.glances.Core.class);
					com.wao.itil.model.Core core = new com.wao.itil.model.Core(
							coreGlances);
					redisSimpleCoreMessageQueue.produce(core);
				}
				break;
			case "getCpu":
				// TODO 
				break;
			case "getLoad":
				// TODO 
				break;
			case "getDiskIO":
				// TODO 
				break;
			case "getFs":
				// TODO 
				break;
			case "getMem":
				// TODO 
				break;
			case "getMemSwap":
				// TODO 
				break;
			case "getNetwork":
				// TODO
				break;
			case "getSystem":
				// TODO 
				break;
			case "getProcessCount":
				// TODO 
				break;
			case "getProcessList":
				// TODO 
				break;
			default:
			}
		}

	}
}

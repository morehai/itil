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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wao.itil.model.Task;
import com.wao.itil.queue.RedisSimpleCpuMessageQueue;
import com.wao.itil.queue.RedisSimpleDiskioMessageQueue;
import com.wao.itil.queue.RedisSimpleMemoryMessageQueue;
import com.wao.itil.queue.RedisSimpleProcessMessageQueue;
import com.wao.itil.queue.RedisSimpleSystemMessageQueue;

/**
 * 任务通过Glances代理获取被监控服务器信息
 */
@Component
public class GlancesTaskServiceImpl implements TaskService {

	@Autowired
	private LockService lockService;
	@Autowired
	private ExecutorService executorService;
	@Autowired
	private TaskManager taskManager;
	@Autowired
	private RedisSimpleCpuMessageQueue redisSimpleCpuMessageQueue;
	@Autowired
	private RedisSimpleDiskioMessageQueue redisSimpleDisksMessageQueue;
	@Autowired
	private RedisSimpleMemoryMessageQueue redisSimpleMemoryMessageQueue;
	@Autowired
	private RedisSimpleProcessMessageQueue redisSimpleProcessesMessageQueue;
	@Autowired
	private RedisSimpleSystemMessageQueue redisSimpleSystemsMessageQueue;

	@Override
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
				// TODO 记录错误日志
				continue;
			}
		}
		return respMap;
	}

	@Override
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

	@Override
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
										task.getMonitorIp(), methods);
								transferServerInfo(task.getMonitorIp(), respMap);
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

	@Override
	public void transferServerInfo(String host, Map<String, String> respMap) {
		for (String methodName : respMap.keySet()) {
			switch (methodName) {
			case "getCore":
				redisSimpleCpuMessageQueue.produce(respMap.get("getCore"));
				break;
			case "getCpu":
				redisSimpleCpuMessageQueue.produce(respMap.get("getCpu"));
				break;
			case "getLoad":
				redisSimpleCpuMessageQueue.produce(respMap.get("getLoad"));
				break;
			case "getDiskIO":
				redisSimpleDisksMessageQueue.produce(respMap.get("getDiskIO"));
				break;
			case "getFs":
				redisSimpleDisksMessageQueue.produce(respMap.get("getFs"));
				break;
			case "getMem":
				redisSimpleMemoryMessageQueue.produce(respMap.get("getMem"));
				break;
			case "getMemSwap":
				redisSimpleMemoryMessageQueue
						.produce(respMap.get("getMemSwap"));
				break;
			case "getNetwork":
				// TODO
				break;
			case "getSystem":
				redisSimpleSystemsMessageQueue
						.produce(respMap.get("getSystem"));
				break;
			case "getProcessCount":
				redisSimpleProcessesMessageQueue.produce(respMap
						.get("getProcessCount"));
				break;
			case "getProcessList":
				redisSimpleProcessesMessageQueue.produce(respMap
						.get("getProcessList"));
				break;
			default:
			}
		}

	}
}

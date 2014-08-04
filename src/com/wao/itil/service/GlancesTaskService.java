package com.wao.itil.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.ironrhino.core.coordination.LockService;
import org.ironrhino.core.util.ErrorMessage;
import org.ironrhino.core.util.HttpClientUtils;
import org.ironrhino.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wao.itil.model.Task;
import com.wao.itil.queue.RedisSimpleCoreMessageQueue;
import com.wao.itil.queue.RedisSimpleCpuMessageQueue;
import com.wao.itil.queue.RedisSimpleDiskioMessageQueue;
import com.wao.itil.queue.RedisSimpleFileSystemMessageQueue;
import com.wao.itil.queue.RedisSimpleLoadMessageQueue;
import com.wao.itil.queue.RedisSimpleMemoryMessageQueue;
import com.wao.itil.queue.RedisSimpleMemorySwapMessageQueue;
import com.wao.itil.queue.RedisSimpleNetworkMessageQueue;
import com.wao.itil.queue.RedisSimpleProcessCountMessageQueue;
import com.wao.itil.queue.RedisSimpleProcessorMessageQueue;
import com.wao.itil.queue.RedisSimpleSystemsMessageQueue;

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
	private RedisSimpleFileSystemMessageQueue redisSimpleFileSystemMessageQueue;
	@Autowired
	private RedisSimpleLoadMessageQueue redisSimpleLoadMessageQueue;
	@Autowired
	private RedisSimpleMemoryMessageQueue redisSimpleMemoryMessageQueue;
	@Autowired
	private RedisSimpleMemorySwapMessageQueue redisSimpleMemorySwapMessageQueue;
	@Autowired
	private RedisSimpleProcessorMessageQueue redisSimpleProcessMessageQueue;
	@Autowired
	private RedisSimpleProcessCountMessageQueue redisSimpleProcessCountMessageQueue;
	@Autowired
	private RedisSimpleNetworkMessageQueue redisSimpleNetworkMessageQueue;
	@Autowired
	private RedisSimpleSystemsMessageQueue redisSimpleSystemMessageQueue;

	private static final TypeReference<LinkedList<com.wao.itil.model.glances.Process>> PROCESS_LIST_TYPE = new TypeReference<LinkedList<com.wao.itil.model.glances.Process>>() {
	};
	private static final TypeReference<LinkedList<com.wao.itil.model.glances.Network>> NETWORK_LIST_TYPE = new TypeReference<LinkedList<com.wao.itil.model.glances.Network>>() {
	};

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
				// TODO 将任务的服务器信息写入网络连接异常表中
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
//	@Scheduled(cron = "5 * * * * ?")
//	@Trigger
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
					core.setTask(task);
					redisSimpleCoreMessageQueue.produce(core);
				}
				break;
			case "getCpu":
				if (respMap.get("getCpu") != null) {
					com.wao.itil.model.glances.Cpu cpuGlances = JsonUtils
							.fromJson(respMap.get("getCpu"),
									com.wao.itil.model.glances.Cpu.class);
					com.wao.itil.model.Cpu cpu = new com.wao.itil.model.Cpu(
							cpuGlances);
					cpu.setTask(task);
					redisSimpleCpuMessageQueue.produce(cpu);
				}
				break;
			case "getDiskIO":
				if (respMap.get("getDiskIO") != null) {
					com.wao.itil.model.glances.Diskio diskioGlances = JsonUtils
							.fromJson(respMap.get("getDiskIO"),
									com.wao.itil.model.glances.Diskio.class);
					com.wao.itil.model.Diskio diskio = new com.wao.itil.model.Diskio(
							diskioGlances);
					diskio.setTask(task);
					redisSimpleDiskioMessageQueue.produce(diskio);
				}
				break;
			case "getFs":
				if (respMap.get("getFs") != null) {
					com.wao.itil.model.glances.FileSystem fileSystemGlances = JsonUtils
							.fromJson(respMap.get("getFs"),
									com.wao.itil.model.glances.FileSystem.class);
					com.wao.itil.model.FileSystem fileSystem = new com.wao.itil.model.FileSystem(
							fileSystemGlances);
					fileSystem.setTask(task);
					redisSimpleFileSystemMessageQueue.produce(fileSystem);
				}
				break;
			case "getLoad":
				if (respMap.get("getLoad") != null) {
					com.wao.itil.model.glances.Load loadGlances = JsonUtils
							.fromJson(respMap.get("getLoad"),
									com.wao.itil.model.glances.Load.class);
					com.wao.itil.model.Load load = new com.wao.itil.model.Load(
							loadGlances);
					load.setTask(task);
					redisSimpleLoadMessageQueue.produce(load);
				}
				break;
			case "getMem":
				if (respMap.get("getMem") != null) {
					com.wao.itil.model.glances.Memory memoryGlances = JsonUtils
							.fromJson(respMap.get("getMem"),
									com.wao.itil.model.glances.Memory.class);
					com.wao.itil.model.Memory memory = new com.wao.itil.model.Memory(
							memoryGlances);
					memory.setTask(task);
					redisSimpleMemoryMessageQueue.produce(memory);
				}
				break;
			case "getMemSwap":
				if (respMap.get("getMemSwap") != null) {
					com.wao.itil.model.glances.MemorySwap memorySwapGlances = JsonUtils
							.fromJson(respMap.get("getMemSwap"),
									com.wao.itil.model.glances.MemorySwap.class);
					com.wao.itil.model.MemorySwap memorySwap = new com.wao.itil.model.MemorySwap(
							memorySwapGlances);
					memorySwap.setTask(task);
					redisSimpleMemorySwapMessageQueue.produce(memorySwap);
				}
				break;
			case "getProcessCount":
				if (respMap.get("getProcessCount") != null) {
					com.wao.itil.model.glances.ProcessCount processCountGlances = JsonUtils
							.fromJson(
									respMap.get("getProcessCount"),
									com.wao.itil.model.glances.ProcessCount.class);
					com.wao.itil.model.ProcessCount processCount = new com.wao.itil.model.ProcessCount(
							processCountGlances);
					processCount.setTask(task);
					redisSimpleProcessCountMessageQueue.produce(processCount);
				}
				break;
			case "getProcessList":
				if (respMap.get("getProcessList") != null) {
					List<com.wao.itil.model.glances.Process> processGlancesList = JsonUtils
							.fromJson(respMap.get("getProcessList"),
									PROCESS_LIST_TYPE);
					for (com.wao.itil.model.glances.Process processGlances : processGlancesList) {
						com.wao.itil.model.Processor processor = new com.wao.itil.model.Processor(
								processGlances);
						processor.setTask(task);
						redisSimpleProcessMessageQueue.produce(processor);
					}
				}
				break;
			case "getNetwork":
				if (respMap.get("getNetwork") != null) {
					List<com.wao.itil.model.glances.Network> networkGlancesList = JsonUtils
							.fromJson(respMap.get("getNetwork"),
									NETWORK_LIST_TYPE);
					LinkedList<com.wao.itil.model.Network> networkList = new LinkedList<com.wao.itil.model.Network>();
					for (com.wao.itil.model.glances.Network networkGlances : networkGlancesList) {
						com.wao.itil.model.Network network = new com.wao.itil.model.Network(
								networkGlances);
						network.setTask(task);
						redisSimpleNetworkMessageQueue.produce(network);
					}
				}
				break;
			case "getSystem":
				if (respMap.get("getSystem") != null) {
					com.wao.itil.model.glances.System systemGlances = JsonUtils
							.fromJson(respMap.get("getSystem"),
									com.wao.itil.model.glances.System.class);
					com.wao.itil.model.Systems system = new com.wao.itil.model.Systems(
							systemGlances);
					system.setTask(task);
					redisSimpleSystemMessageQueue.produce(system);
				}
				break;
			default:
				// TODO
			}
		}

	}
}

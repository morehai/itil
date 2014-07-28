package com.wao.itil.service;

import java.io.IOException;
import java.util.List;
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

/**
 * 任务通过Glances代理获取被监控服务器信息
 */
@Component
public class GlancesTaskServiceImpl implements TaskService {

	private String lockName = "TaskGlancesServiceImpl.batchSyncServerInfoForTask";
	private int batchSyncSize = 5;
	
	@Autowired
	private LockService lockService;

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private TaskManager taskManager;

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
								try { // TODO 分次获取服务器运行的各项监控信息
									getServerInfoByAgent(task.getMonitorIp(),
											task.getServerMonitorsAsString());
								} catch (IOException e) {
									e.printStackTrace();
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

	private String getMethod(String name){
		
		
		return null;
	}
}

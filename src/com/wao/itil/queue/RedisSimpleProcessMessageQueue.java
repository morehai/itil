package com.wao.itil.queue;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ironrhino.core.redis.RedisQueue;
import org.ironrhino.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wao.itil.model.Process;
import com.wao.itil.service.ProcessManager;

@Component
public class RedisSimpleProcessMessageQueue extends RedisQueue<String>
		implements SimpleMessageQueue {

	@Autowired(required = false)
	private ExecutorService executorService;

	@Autowired
	private ProcessManager processManager;

	private boolean stop;

	private static final TypeReference<Process> REQUEST_TYPE = new TypeReference<Process>() {
	};

	@Override
	@PostConstruct
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Runnable task = new Runnable() {

			@Override
			public void run() {
				while (!stop) {
					try {
						consume(queue.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		};
		if (executorService != null)
			executorService.execute(task);
		else
			new Thread(task).start();
	}

	@PreDestroy
	public void destroy() {
		stop = true;
	}

	@Override
	public void consume(String processMsg) {
		// TODO 实现消息入库操作
		try {
			Process process = JsonUtils.fromJson(processMsg, REQUEST_TYPE);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

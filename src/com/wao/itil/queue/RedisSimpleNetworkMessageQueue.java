package com.wao.itil.queue;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ironrhino.core.redis.RedisQueue;
import org.ironrhino.core.service.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wao.itil.model.Network;
import com.wao.itil.service.TaskManager;

@Component
public class RedisSimpleNetworkMessageQueue extends RedisQueue<Network> {

	@Autowired(required = false)
	private ExecutorService executorService;

	@Autowired
	private BaseManager<Network> baseManager;
	@Autowired
	private TaskManager taskManager;

	private boolean stop;

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
	public void consume(Network network) {
		baseManager.save(network);
	}

}

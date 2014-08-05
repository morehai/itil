package com.wao.itil.queue;

import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ironrhino.core.redis.RedisQueue;
import org.ironrhino.core.service.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wao.itil.model.Load;

@Component
public class RedisSimpleLoadMessageQueue extends RedisQueue<Load> {

	@Autowired(required = false)
	private ExecutorService executorService;

	@Autowired
	private EntityManager<Load> entityManager;

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
	public void consume(Load load) {
		entityManager.save(load);
	}

}

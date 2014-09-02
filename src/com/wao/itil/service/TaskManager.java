package com.wao.itil.service;

import java.util.List;

import org.ironrhino.core.service.BaseManager;

import com.wao.itil.model.Task;

public interface TaskManager extends BaseManager<Task> {

	/**
	 * 默认每次调用返回500条需要更新的任务
	 * @return
	 */
	public List<Task> findByUnExecuted();
	
}

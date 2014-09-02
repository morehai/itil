package com.wao.itil.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.ironrhino.core.service.BaseManagerImpl;
import org.springframework.stereotype.Component;

import com.wao.itil.model.Task;

@Component
public class TaskManagerImpl extends BaseManagerImpl<Task> implements
		TaskManager {

	@Override
	public List<Task> findByUnExecuted() {
		DetachedCriteria dc = detachedCriteria();
		dc.add(Restrictions.eq("successExecuted", false));
		return findListByCriteria(dc, 1, 500);
	}
}

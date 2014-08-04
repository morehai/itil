package com.wao.itil.model.glances;

import java.io.Serializable;

/**
 * 服务器进程使用情况模型 <code>
 * {"running": 1, "total": 81, "thread": 299, "sleeping": 80}
 * </code>
 */
public class ProcessCount implements Serializable {

	private static final long serialVersionUID = -2465151848049819546L;

	/**
	 * 进程总数量
	 */
	private long thread;

	/**
	 * 已创建的进程数
	 */
	private long total;

	/**
	 * 休眠进程数量
	 */
	private long sleeping;

	/**
	 * 运行进程数量
	 */
	private int running;

	public ProcessCount() {

	}

	public long getThread() {
		return thread;
	}

	public void setThread(long thread) {
		this.thread = thread;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getSleeping() {
		return sleeping;
	}

	public void setSleeping(long sleeping) {
		this.sleeping = sleeping;
	}

	public int getRunning() {
		return running;
	}

	public void setRunning(int running) {
		this.running = running;
	}

}

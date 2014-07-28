package com.wao.itil.model.glances;

import java.io.Serializable;

/**
 * 服务器CPU使用状态百分比模型 <code>
 * {"softirq": 0.0, "iowait": 0.0, "system": 2.3, "guest": 0.0, "idle": 93.2, 
 * "user": 4.5, "guest_nice": 0.0, "irq": 0.0, "steal": 0.0, "nice": 0.0}
 * </code>
 */
public class Cpu implements Serializable {

	private static final long serialVersionUID = 3193153184083228751L;

	/**
	 * 用户态使用的cpu百分比,不包括虚拟处理器
	 */
	private float user;

	/**
	 * 内核态占用的cpu百分比
	 */
	private float system;

	/**
	 * 花费在虚拟处理器的cpu百分比
	 */
	private float guest;

	/**
	 * 花费在硬件中断上的cpu百分比
	 */
	private float irq;

	/**
	 * 花费在软件中断上的cpu百分比
	 */
	private float softirq;

	/**
	 * 有io请求冲突、cpu空闲时间百分比
	 */
	private float iowait;

	/**
	 * 没有io操作、cpu处于空闲百分比
	 */
	private float idle;

	/**
	 * 等待系统进程调度的百分比
	 */
	private float steal;

	/**
	 * 用户态nice优先级进程占用的cpu百分比
	 */
	private float nice;

	/**
	 * 虚拟处理器用户态nice优先级进程占用的cpu百分比
	 */
	private float guest_nice;

	public Cpu() {

	}

	public float getUser() {
		return user;
	}

	public void setUser(float user) {
		this.user = user;
	}

	public float getSystem() {
		return system;
	}

	public void setSystem(float system) {
		this.system = system;
	}

	public float getGuest() {
		return guest;
	}

	public void setGuest(float guest) {
		this.guest = guest;
	}

	public float getIrq() {
		return irq;
	}

	public void setIrq(float irq) {
		this.irq = irq;
	}

	public float getSoftirq() {
		return softirq;
	}

	public void setSoftirq(float softirq) {
		this.softirq = softirq;
	}

	public float getIowait() {
		return iowait;
	}

	public void setIowait(float iowait) {
		this.iowait = iowait;
	}

	public float getIdle() {
		return idle;
	}

	public void setIdle(float idle) {
		this.idle = idle;
	}

	public float getSteal() {
		return steal;
	}

	public void setSteal(float steal) {
		this.steal = steal;
	}

	public float getNice() {
		return nice;
	}

	public void setNice(float nice) {
		this.nice = nice;
	}

	public float getGuest_nice() {
		return guest_nice;
	}

	public void setGuest_nice(float guest_nice) {
		this.guest_nice = guest_nice;
	}

}

package com.wao.itil.model.glances;

import java.io.Serializable;

/**
 * 服务器CPU使用负载模型 <code>
 * {"cpucore": 2, "min1": 0.0, "min5": 0.01, "min15": 0.05}
 * </code>
 */
public class Load implements Serializable {

	private static final long serialVersionUID = -9169885892032875285L;

	/**
	 * CPU内核数量
	 */
	private int cpucore;

	/**
	 * 1分钟平均负载
	 */
	private float min1;

	/**
	 * 5分钟平均负载
	 */
	private float min5;

	/**
	 * 15分钟平均负载
	 */
	private float min15;

	public Load() {

	}

	public int getCpucore() {
		return cpucore;
	}

	public void setCpucore(int cpucore) {
		this.cpucore = cpucore;
	}

	public float getMin1() {
		return min1;
	}

	public void setMin1(float min1) {
		this.min1 = min1;
	}

	public float getMin5() {
		return min5;
	}

	public void setMin5(float min5) {
		this.min5 = min5;
	}

	public float getMin15() {
		return min15;
	}

	public void setMin15(float min15) {
		this.min15 = min15;
	}

}

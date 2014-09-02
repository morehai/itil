package com.wao.itil.model.glances;

import java.io.Serializable;

/**
 * 服务器交换内存空间使用情况模型 <code>
 * {"used": 0, "percent": 0.0, "free": 8493461504, "sout": 0, "total": 8493461504, "sin": 0}
 * </code>
 */
public class MemorySwap implements Serializable {

	private static final long serialVersionUID = -2204919687123983153L;

	/**
	 * 总容量
	 */
	private long total;
	
	/**
	 * 剩余容量
	 */
	private long free;
	
	/**
	 * 已使用容量
	 */
	private long used;
	private long sin;
	private long sout;
	
	/**
	 * 使用率百分比
	 */
	private float percent;

	public MemorySwap() {

	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getFree() {
		return free;
	}

	public void setFree(long free) {
		this.free = free;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public long getSin() {
		return sin;
	}

	public void setSin(long sin) {
		this.sin = sin;
	}

	public long getSout() {
		return sout;
	}

	public void setSout(long sout) {
		this.sout = sout;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

}

package com.wao.itil.model.glances;

import java.io.Serializable;

/**
 * 服务器内存使用情况模型 <code>
 * {"available": 7158685696, "used": 1124143104, "cached": 278761472, "percent": 13.6, "free": 7158685696, 
 * "inactive": 178905088, "active": 1125826560, "total": 8282828800, "buffers": 49258496}
 * </code>
 */
public class Memory implements Serializable {

	private static final long serialVersionUID = -2897957081730578827L;

	/**
	 * 物理内存总容量
	 */
	private long total;

	/**
	 * 可用内存容量
	 */
	private long available;

	/**
	 * 活跃内存容量
	 */
	private long active;

	/**
	 * 非活跃内存容量
	 */
	private long inactive;

	/**
	 * 已使用内存容量
	 */
	private long used;

	/**
	 * 已被缓存容量
	 */
	private long cached;

	/**
	 * 空闲容量
	 */
	private long free;

	/**
	 * 缓冲区容量
	 */
	private long buffers;

	/**
	 * 内存容量使用占比
	 */
	private float percent;

	public Memory() {

	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getAvailable() {
		return available;
	}

	public void setAvailable(long available) {
		this.available = available;
	}

	public long getActive() {
		return active;
	}

	public void setActive(long active) {
		this.active = active;
	}

	public long getInactive() {
		return inactive;
	}

	public void setInactive(long inactive) {
		this.inactive = inactive;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public long getCached() {
		return cached;
	}

	public void setCached(long cached) {
		this.cached = cached;
	}

	public long getFree() {
		return free;
	}

	public void setFree(long free) {
		this.free = free;
	}

	public long getBuffers() {
		return buffers;
	}

	public void setBuffers(long buffers) {
		this.buffers = buffers;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

}

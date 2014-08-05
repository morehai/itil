package com.wao.itil.model.glances;

import java.io.Serializable;

import org.ironrhino.core.metadata.NotInCopy;

/**
 * 服务器磁盘使用情况模型 <code>
 * [{"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "dm-1"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda5"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda2"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "dm-0"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda1"}]
 * </code>
 */
public class Diskio implements Serializable {

	private static final long serialVersionUID = -8970404097979036902L;

	/**
	 * 磁盘分区名称
	 */
	@NotInCopy
	private String disk_name;

	/**
	 * 读取字节数
	 */
	@NotInCopy
	private long read_bytes;

	/**
	 * 写入字节数
	 */
	@NotInCopy
	private long write_bytes;

	/**
	 * 读取的时间点
	 */
	@NotInCopy
	private double time_since_update;

	public Diskio() {

	}

	public double getTime_since_update() {
		return time_since_update;
	}

	public void setTime_since_update(double time_since_update) {
		this.time_since_update = time_since_update;
	}

	public long getRead_bytes() {
		return read_bytes;
	}

	public void setRead_bytes(long read_bytes) {
		this.read_bytes = read_bytes;
	}

	public long getWrite_bytes() {
		return write_bytes;
	}

	public void setWrite_bytes(long write_bytes) {
		this.write_bytes = write_bytes;
	}

	public String getDisk_name() {
		return disk_name;
	}

	public void setDisk_name(String disk_name) {
		this.disk_name = disk_name;
	}

}

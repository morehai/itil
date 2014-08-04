package com.wao.itil.model.glances;

import java.io.Serializable;

import org.ironrhino.core.metadata.NotInCopy;

/**
 * 服务器进程详细模型 <code>
 * [{"username": "root", "status": "S", "cpu_times": [0.13, 0.62], "name": "init", "memory_percent": 0.028434729931880277, 
 * "cpu_percent": 0.0, "pid": 1, "io_counters": [0, 0, 0, 0, 0], "cmdline": "/sbin/init", "memory_info": [2355200, 24928256], 
 * "time_since_update": 0.23090100288391113, "nice": 0},
 *  {"username": "root", "status": "S", "cpu_times": [0.0, 0.0], "name": "kthreadd", "memory_percent": 0.0, 
 *  "cpu_percent": 0.0, "pid": 2, "io_counters": [0, 0, 0, 0, 0], "cmdline": "", "memory_info": [0, 0], 
 *  "time_since_update": 0.23090100288391113, "nice": 0}, 
 *  {"username": "root", "status": "S", "cpu_times": [0.02, 0.0], "name": "ksoftirqd/0", "memory_percent": 0.0, 
 *  "cpu_percent": 0.0, "pid": 3, "io_counters": [0, 0, 0, 0, 0], "cmdline": "", "memory_info": [0, 0], 
 *  "time_since_update": 0.23090100288391113, "nice": 0}... ...] 
 * </code>
 */
public class Process implements Serializable {

	private static final long serialVersionUID = -3374793017511245605L;

	private String username;
	private String status;
	@NotInCopy
	private double[] cpu_times;
	private String name;
	@NotInCopy
	private double memory_percent;
	@NotInCopy
	private double cpu_percent;
	private int pid;
	/*
	 * io_counters: [read_bytes, write_bytes, read_bytes_old, write_bytes_old, io_tag] 
	 * If io_tag = 0 : Access denied 
	 * If io_tag = 1 : No access denied (display the IO rate)
	 */
	@NotInCopy
	private long[] io_counters;
	private String cmdline;
	@NotInCopy
	private long[] memory_info;
	@NotInCopy
	private double time_since_update;
	private long nice;

	public Process() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double[] getCpu_times() {
		return cpu_times;
	}

	public void setCpu_times(double[] cpu_times) {
		this.cpu_times = cpu_times;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMemory_percent() {
		return memory_percent;
	}

	public void setMemory_percent(double memory_percent) {
		this.memory_percent = memory_percent;
	}

	public double getCpu_percent() {
		return cpu_percent;
	}

	public void setCpu_percent(double cpu_percent) {
		this.cpu_percent = cpu_percent;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public long[] getIo_counters() {
		return io_counters;
	}

	public void setIo_counters(long[] io_counters) {
		this.io_counters = io_counters;
	}

	public String getCmdline() {
		return cmdline;
	}

	public void setCmdline(String cmdline) {
		this.cmdline = cmdline;
	}

	public long[] getMemory_info() {
		return memory_info;
	}

	public void setMemory_info(long[] memory_info) {
		this.memory_info = memory_info;
	}

	public double getTime_since_update() {
		return time_since_update;
	}

	public void setTime_since_update(double time_since_update) {
		this.time_since_update = time_since_update;
	}

	public long getNice() {
		return nice;
	}

	public void setNice(long nice) {
		this.nice = nice;
	}

}

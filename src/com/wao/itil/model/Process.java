package com.wao.itil.model;

import java.util.Date;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.ironrhino.core.metadata.Authorize;
import org.ironrhino.core.metadata.AutoConfig;
import org.ironrhino.core.metadata.Hidden;
import org.ironrhino.core.metadata.NotInJson;
import org.ironrhino.core.metadata.UiConfig;
import org.ironrhino.core.search.elasticsearch.annotations.Searchable;
import org.ironrhino.core.search.elasticsearch.annotations.SearchableId;
import org.ironrhino.core.security.role.UserRole;
import org.ironrhino.core.util.BeanUtils;

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
@Entity
@Table(name = "process")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Process extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -8611389360432926850L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "process_entity_seq")
	@SequenceGenerator(name = "process_entity_seq", sequenceName = "process_entity_seq")
	private Long id;

	/**
	 * 进程状态
	 */
	private String status;

	/**
	 * CPU耗时
	 */
	private double[] cpuTimes;

	/**
	 * 启动进程的用户名
	 */
	private String username;

	/**
	 * 进程名称
	 */
	private String name;

	/**
	 * 内存占用百分比
	 */
	private double memoryPercent;

	/**
	 * CPU占用百分比
	 */
	private double cpuPercent;

	/**
	 * 进程编号
	 */
	private int pid;
	/*
	 * io_counters: [read_bytes, write_bytes, read_bytes_old, write_bytes_old,
	 * io_tag] If io_tag = 0 : Access denied If io_tag = 1 : No access denied
	 * (display the IO rate)
	 */
	private long[] ioCounters;
	private String cmdline;
	private long[] memoryInfo;
	private long nice;

	/**
	 * 持续运行时间长
	 */
	@UiConfig(hiddenInView = @Hidden(true), hiddenInInput = @Hidden(true), hiddenInList = @Hidden(true))
	private double timeSinceUpdate;

	/**
	 * 数据请求的时间点
	 */
	@UiConfig(hidden = true)
	private Date createDate = new Date();

	// 关联的任务
	@NotInJson
	@UiConfig(hiddenInView = @Hidden(true))
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Task task;

	public Process() {
	}
	
	public Process(com.wao.itil.model.glances.Process processGlances) {
		BeanUtils.copyProperties(processGlances, this);
		cpuTimes = processGlances.getCpu_times();
		memoryPercent = processGlances.getMemory_percent();
		cpuPercent = processGlances.getCpu_percent();
		ioCounters = processGlances.getIo_counters();
		memoryInfo = processGlances.getMemory_info();
		timeSinceUpdate = processGlances.getTime_since_update();
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double[] getCpuTimes() {
		return cpuTimes;
	}

	public void setCpuTimes(double[] cpuTimes) {
		this.cpuTimes = cpuTimes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMemoryPercent() {
		return memoryPercent;
	}

	public void setMemoryPercent(double memoryPercent) {
		this.memoryPercent = memoryPercent;
	}

	public double getCpuPercent() {
		return cpuPercent;
	}

	public void setCpuPercent(double cpuPercent) {
		this.cpuPercent = cpuPercent;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public long[] getIoCounters() {
		return ioCounters;
	}

	public void setIoCounters(long[] ioCounters) {
		this.ioCounters = ioCounters;
	}

	public String getCmdline() {
		return cmdline;
	}

	public void setCmdline(String cmdline) {
		this.cmdline = cmdline;
	}

	public long[] getMemoryInfo() {
		return memoryInfo;
	}

	public void setMemoryInfo(long[] memoryInfo) {
		this.memoryInfo = memoryInfo;
	}

	public long getNice() {
		return nice;
	}

	public void setNice(long nice) {
		this.nice = nice;
	}

	public double getTimeSinceUpdate() {
		return timeSinceUpdate;
	}

	public void setTimeSinceUpdate(double timeSinceUpdate) {
		this.timeSinceUpdate = timeSinceUpdate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	@NotInJson
	public boolean isNew() {
		return id == null || id == 0;
	}

}

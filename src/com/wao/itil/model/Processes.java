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

/**
 * 服务器进程使用情况模型
 */
@Entity
@Table(name = "processes")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Processes extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -8611389360432926850L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "processes_entity_seq")
	@SequenceGenerator(name = "processes_entity_seq", sequenceName = "processes_entity_seq", allocationSize = 1)
	private Long id;

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
	 * 读取的时间点
	 */
	@UiConfig(hiddenInView = @Hidden(true), hiddenInInput = @Hidden(true), hiddenInList = @Hidden(true))
	private Date timeSinceUpdate;

	// 关联的主机系统
	@NotInJson
	@UiConfig(hiddenInView = @Hidden(true))
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "systemId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Systems system;
	
	public Processes() {

	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getTimeSinceUpdate() {
		return timeSinceUpdate;
	}

	public void setTimeSinceUpdate(Date timeSinceUpdate) {
		this.timeSinceUpdate = timeSinceUpdate;
	}

	@Override
	@NotInJson
	public boolean isNew() {
		return id == null || id == 0;
	}

}

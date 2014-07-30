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
 * 服务器CPU使用状态百分比模型 <code>
 * {"softirq": 0.0, "iowait": 0.0, "system": 2.3, "guest": 0.0, "idle": 93.2, 
 * "user": 4.5, "guest_nice": 0.0, "irq": 0.0, "steal": 0.0, "nice": 0.0}
 * </code>
 */
@Entity
@Table(name = "cpu")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Cpu extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -485467448045298243L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "cpu_entity_seq")
	@SequenceGenerator(name = "cpu_entity_seq", sequenceName = "cpu_entity_seq", allocationSize = 1)
	private Long id;

	/**
	 * 用户态使用的cpu百分比,不包括虚拟处理器
	 */
	private float userStatus;

	/**
	 * 内核态占用的cpu百分比
	 */
	private float systemPercent;

	/**
	 * 花费在虚拟处理器的cpu百分比
	 */
	private float guestStatus;

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
	private float guestNice;

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
	private System system;

	public Cpu() {

	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getSystemPercent() {
		return systemPercent;
	}

	public void setSystemPercent(float systemPercent) {
		this.systemPercent = systemPercent;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public float getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(float userStatus) {
		this.userStatus = userStatus;
	}

	public float getGuestStatus() {
		return guestStatus;
	}

	public void setGuestStatus(float guestStatus) {
		this.guestStatus = guestStatus;
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

	public float getGuestNice() {
		return guestNice;
	}

	public void setGuestNice(float guestNice) {
		this.guestNice = guestNice;
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

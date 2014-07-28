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
 * 服务器内存使用情况模型
 */
@Entity
@Table(name = "memory")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Memory extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = 913379597228395999L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "memory_entity_seq")
	@SequenceGenerator(name = "memory_entity_seq", sequenceName = "memory_entity_seq", allocationSize = 1)
	private Long id;

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

	/**
	 * 总容量
	 */
	private long swpTotal;

	/**
	 * 交换内存剩余容量
	 */
	private long swpFree;

	/**
	 * 交换内存已使用容量
	 */
	private long swpUsed;
	private long swpIn;
	private long swpOut;

	/**
	 * 交换内存使用率百分比
	 */
	private float swpPercent;

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
	
	public Memory() {

	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public long getSwpTotal() {
		return swpTotal;
	}

	public void setSwpTotal(long swpTotal) {
		this.swpTotal = swpTotal;
	}

	public long getSwpFree() {
		return swpFree;
	}

	public void setSwpFree(long swpFree) {
		this.swpFree = swpFree;
	}

	public long getSwpUsed() {
		return swpUsed;
	}

	public void setSwpUsed(long swpUsed) {
		this.swpUsed = swpUsed;
	}

	public long getSwpIn() {
		return swpIn;
	}

	public void setSwpIn(long swpIn) {
		this.swpIn = swpIn;
	}

	public long getSwpOut() {
		return swpOut;
	}

	public void setSwpOut(long swpOut) {
		this.swpOut = swpOut;
	}

	public float getSwpPercent() {
		return swpPercent;
	}

	public void setSwpPercent(float swpPercent) {
		this.swpPercent = swpPercent;
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

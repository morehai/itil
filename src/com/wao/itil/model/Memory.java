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
 * 服务器内存使用情况模型 <code>
 * {"available": 7158685696, "used": 1124143104, "cached": 278761472, "percent": 13.6, "free": 7158685696, 
 * "inactive": 178905088, "active": 1125826560, "total": 8282828800, "buffers": 49258496}
 * </code>
 */
@Entity
@Table(name = "itil_memory")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Memory extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -9055059905140176093L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "memory_entity_seq")
	@SequenceGenerator(name = "memory_entity_seq", sequenceName = "memory_entity_seq")
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

	public Memory() {
	}

	public Memory(com.wao.itil.model.glances.Memory memoryGlances) {
		BeanUtils.copyProperties(memoryGlances, this);
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

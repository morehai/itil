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
import org.ironrhino.core.metadata.Readonly;
import org.ironrhino.core.metadata.Richtable;
import org.ironrhino.core.metadata.UiConfig;
import org.ironrhino.core.search.elasticsearch.annotations.Searchable;
import org.ironrhino.core.search.elasticsearch.annotations.SearchableId;
import org.ironrhino.core.security.role.UserRole;
import org.ironrhino.core.util.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 服务器交换内存空间使用情况模型 <code>
 * {"used": 0, "percent": 0.0, "free": 8493461504, "sout": 0, "total": 8493461504, "sin": 0}
 * </code>
 */
@Entity
@Table(name = "itil_memory_swap")
@Searchable
@AutoConfig(namespace = "/itil")
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
@Richtable(searchable = true, order = "createDate desc", celleditable = false, readonly = @Readonly(true))
public class MemorySwap extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -5738180139545204796L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "memoryswap_entity_seq")
	@SequenceGenerator(name = "memoryswap_entity_seq", sequenceName = "memoryswap_entity_seq")
	private Long id;

	/**
	 * 交换内存剩余容量
	 */
	private long free;

	/**
	 * 交换内存已使用容量
	 */
	private long used;
	private long sin;
	private long sout;

	/**
	 * 交换内存使用率百分比
	 */
	private float percent;

	/**
	 * 数据请求的时间点
	 */
	@UiConfig(hidden = true)
	private Date createDate = new Date();

	// 关联的任务
	@JsonIgnore
	@UiConfig(hiddenInView = @Hidden(true))
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Task task;

	public MemorySwap() {
	}

	public MemorySwap(com.wao.itil.model.glances.MemorySwap memorySwapGlances) {
		BeanUtils.copyProperties(memorySwapGlances, this);
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public long getFree() {
		return free;
	}

	public void setFree(long free) {
		this.free = free;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
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
	@JsonIgnore
	public boolean isNew() {
		return id == null || id == 0;
	}

}

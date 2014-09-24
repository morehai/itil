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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 服务器CPU物理内核和逻辑内核模型 <code>
 * {"phys": 1, "log": 2}
 * </code>
 */
@Entity
@Table(name = "itil_core")
@Searchable
@AutoConfig(namespace = "/itil")
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
@Richtable(searchable = true, order = "createDate desc", celleditable = false, readonly = @Readonly(true))
public class Core extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -6503346495032442387L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "core_entity_seq")
	@SequenceGenerator(name = "core_entity_seq", sequenceName = "core_entity_seq", allocationSize = 1)
	private Long id;

	/**
	 * 物理内核数量
	 */
	private int phys;

	/**
	 * 逻辑内核数量
	 */
	private int cpucore;

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

	public Core() {
	}

	public Core(com.wao.itil.model.glances.Core coreGlances) {
		phys = coreGlances.getPhys();
		cpucore = coreGlances.getLog();
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPhys() {
		return phys;
	}

	public void setPhys(int phys) {
		this.phys = phys;
	}

	public int getCpucore() {
		return cpucore;
	}

	public void setCpucore(int cpucore) {
		this.cpucore = cpucore;
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

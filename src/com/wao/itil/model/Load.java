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
 * 服务器CPU使用负载模型 <code>
 * {"cpucore": 2, "min1": 0.0, "min5": 0.01, "min15": 0.05}
 * </code>
 */
@Entity
@Table(name = "itil_load")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Load extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -3642929446480395590L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "load_entity_seq")
	@SequenceGenerator(name = "load_entity_seq", sequenceName = "load_entity_seq")
	private Long id;

	/**
	 * 逻辑内核数量
	 */
	private String cpucore;

	/**
	 * 1分钟平均负载
	 */
	private float min1;

	/**
	 * 5分钟平均负载
	 */
	private float min5;

	/**
	 * 15分钟平均负载
	 */
	private float min15;

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

	public Load() {
	}
	
	public Load(com.wao.itil.model.glances.Load loadGlances){
		BeanUtils.copyProperties(loadGlances, this);
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpucore() {
		return cpucore;
	}

	public void setCpucore(String cpucore) {
		this.cpucore = cpucore;
	}

	public float getMin1() {
		return min1;
	}

	public void setMin1(float min1) {
		this.min1 = min1;
	}

	public float getMin5() {
		return min5;
	}

	public void setMin5(float min5) {
		this.min5 = min5;
	}

	public float getMin15() {
		return min15;
	}

	public void setMin15(float min15) {
		this.min15 = min15;
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

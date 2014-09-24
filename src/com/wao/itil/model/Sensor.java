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
 * 服务器硬件传感器模型 <code>
 * {"label": "temp", "value": 40}
 * </code>
 */
@Entity
@Table(name = "itil_sensor")
@Searchable
@AutoConfig(namespace = "/itil")
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
@Richtable(searchable = true, order = "createDate desc", celleditable = false, readonly = @Readonly(true))
public class Sensor extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -740027425813534494L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sensor_entity_seq")
	@SequenceGenerator(name = "sensor_entity_seq", sequenceName = "sensor_entity_seq")
	private Long id;

	/**
	 * 名称
	 */
	private String label;

	/**
	 * 数值
	 */
	private int value;

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

	public Sensor() {
	}

	public Sensor(com.wao.itil.model.glances.Sensor sensorGlances) {
		BeanUtils.copyProperties(sensorGlances, this);
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
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

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
 * 服务器硬件传感器模型 <code>
 * {"label": "temp", "value": 40}
 * </code>
 */
@Entity
@Table(name = "sensor")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Sensor extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -6446899462992594476L;

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

	public Sensor() {

	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimeSinceUpdate() {
		return timeSinceUpdate;
	}

	public void setTimeSinceUpdate(Date timeSinceUpdate) {
		this.timeSinceUpdate = timeSinceUpdate;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
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

	@Override
	@NotInJson
	public boolean isNew() {
		return id == null || id == 0;
	}
	
}

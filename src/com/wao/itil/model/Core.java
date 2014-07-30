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
 * 服务器CPU物理内核和逻辑内核模型 <code>
 * {"phys": 1, "log": 2}
 * </code>
 */
@Entity
@Table(name = "core")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Core extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -485467448045298243L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "core_entity_seq")
	@SequenceGenerator(name = "core_entity_seq", sequenceName = "core_entity_seq")
	private Long id;

	/**
	 * 物理内核数量
	 */
	private String phys;

	/**
	 * 逻辑内核数量
	 */
	private String cpucore;

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

	public Core() {

	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhys() {
		return phys;
	}

	public void setPhys(String phys) {
		this.phys = phys;
	}

	public String getCpucore() {
		return cpucore;
	}

	public void setCpucore(String cpucore) {
		this.cpucore = cpucore;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
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

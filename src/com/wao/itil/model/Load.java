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
 * 服务器CPU使用负载模型 <code>
 * {"cpucore": 2, "min1": 0.0, "min5": 0.01, "min15": 0.05}
 * </code>
 */
@Entity
@Table(name = "load")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Load extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -485467448045298243L;

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

	public Load() {

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

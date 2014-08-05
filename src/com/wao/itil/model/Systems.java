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
import org.ironrhino.core.metadata.Readonly;
import org.ironrhino.core.metadata.Richtable;
import org.ironrhino.core.metadata.UiConfig;
import org.ironrhino.core.search.elasticsearch.annotations.Searchable;
import org.ironrhino.core.search.elasticsearch.annotations.SearchableId;
import org.ironrhino.core.security.role.UserRole;
import org.ironrhino.core.util.BeanUtils;

/**
 * 服务器系统基本信息模型 <code>
 * {"linux_distro": "Ubuntu 12.04", "platform": "64bit", "os_name": "Linux", "hostname": "gringserver", "os_version": "3.2.0-64-generic"}
 * </code>
 */
@Entity
@Table(name = "itil_systems")
@Searchable
@AutoConfig(namespace = "/itil")
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
@Richtable(searchable = true, order = "createDate desc", celleditable = false, readonly = @Readonly(true))
public class Systems extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -5551694684870577298L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "systems_entity_seq")
	@SequenceGenerator(name = "systems_entity_seq", sequenceName = "systems_entity_seq", allocationSize = 1)
	private Long id;

	/**
	 * 服务器地址
	 */
	private String host;

	/**
	 * 服务器名称
	 */
	private String hostName;

	/**
	 * 操作系统名称
	 */
	private String osName;

	/**
	 * 发行版名称
	 */
	private String linuxDistro;

	/**
	 * 平台位数
	 */
	private String platform;

	/**
	 * 内核版本
	 */
	private String osVersion;

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

	public Systems() {
	}

	public Systems(com.wao.itil.model.glances.System systemGlances) {
		BeanUtils.copyProperties(systemGlances, this);
		osName = systemGlances.getOs_name();
		linuxDistro = systemGlances.getLinux_distro();
		osVersion = systemGlances.getOs_version();
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getLinuxDistro() {
		return linuxDistro;
	}

	public void setLinuxDistro(String linuxDistro) {
		this.linuxDistro = linuxDistro;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
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

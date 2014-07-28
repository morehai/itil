package com.wao.itil.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * 服务器IP地址主机名称操作系统相关信息模型
 */
@Entity
@Table(name = "systems")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Systems extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = 8726302574819622122L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "systems_entity_seq")
	@SequenceGenerator(name = "systems_entity_seq", sequenceName = "systems_entity_seq", allocationSize = 1)
	private Long id;

	/**
	 * 服务器IP地址
	 */
	private String hostIp;

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
	 * 读取的时间点
	 */
	@UiConfig(hiddenInView = @Hidden(true), hiddenInInput = @Hidden(true), hiddenInList = @Hidden(true))
	private Date timeSinceUpdate = new Date();

	public Systems() {

	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
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

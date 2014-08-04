package com.wao.itil.model.glances;

import java.io.Serializable;

import org.ironrhino.core.metadata.NotInCopy;

/**
 * 服务器系统基本信息模型 <code>
 * {"linux_distro": "Ubuntu 12.04", "platform": "64bit", "os_name": "Linux", "hostname": "gringserver", "os_version": "3.2.0-64-generic"}
 * </code>
 */
public class System implements Serializable {

	private static final long serialVersionUID = 6529178386903725262L;

	/**
	 * 服务器名称
	 */
	private String hostname;

	/**
	 * 操作系统名称
	 */
	@NotInCopy
	private String os_name;

	/**
	 * 发行版名称
	 */
	@NotInCopy
	private String linux_distro;

	/**
	 * 平台位数
	 */
	private String platform;

	/**
	 * 内核版本
	 */
	private String os_version;

	public System() {

	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getOs_name() {
		return os_name;
	}

	public void setOs_name(String os_name) {
		this.os_name = os_name;
	}

	public String getLinux_distro() {
		return linux_distro;
	}

	public void setLinux_distro(String linux_distro) {
		this.linux_distro = linux_distro;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

}

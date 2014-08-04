package com.wao.itil.model.glances;

import java.io.Serializable;

import org.ironrhino.core.metadata.NotInCopy;

/**
 * 磁盘文件系统使用情况模型 <code>
 * [{"mnt_point": "/", "used": 14453231616, "percent": 20.6, "device_name": "/dev/mapper/gringserver-root", "fs_type": "ext4", "size": 70084247552}, 
 * {"mnt_point": "/boot", "used": 217944064, "percent": 91.3, "device_name": "/dev/sda1", "fs_type": "ext2", "size": 238787584}]
 * </code>
 */
public class FileSystem implements Serializable {

	private static final long serialVersionUID = 4492508243683814464L;

	/**
	 * 文件系统名称
	 */
	@NotInCopy
	private String device_name;

	/**
	 * 文件系统类型
	 */
	@NotInCopy
	private String fs_type;

	/**
	 * 挂载名称
	 */
	@NotInCopy
	private String mnt_point;

	/**
	 * 挂载容量
	 */
	private long size;

	/**
	 * 已使用容量
	 */
	private long used;

	public FileSystem() {

	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getFs_type() {
		return fs_type;
	}

	public void setFs_type(String fs_type) {
		this.fs_type = fs_type;
	}

	public String getMnt_point() {
		return mnt_point;
	}

	public void setMnt_point(String mnt_point) {
		this.mnt_point = mnt_point;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

}

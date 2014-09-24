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
 * 磁盘文件系统使用情况模型 <code>
 * [{"mnt_point": "/", "used": 14453231616, "percent": 20.6, "device_name": "/dev/mapper/gringserver-root", "fs_type": "ext4", "size": 70084247552}, 
 * {"mnt_point": "/boot", "used": 217944064, "percent": 91.3, "device_name": "/dev/sda1", "fs_type": "ext2", "size": 238787584}]
 * </code>
 */
@Entity
@Table(name = "itil_filesystem")
@Searchable
@AutoConfig(namespace = "/itil")
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
@Richtable(searchable = true, order = "createDate desc", celleditable = false, readonly = @Readonly(true))
public class FileSystem extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -5764283519412629706L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "filesystem_entity_seq")
	@SequenceGenerator(name = "filesystem_entity_seq", sequenceName = "filesystem_entity_seq")
	private Long id;

	/**
	 * 文件系统名称
	 */
	private String deviceName;

	/**
	 * 文件系统类型
	 */
	private String fsType;

	/**
	 * 挂载名称
	 */
	private String mntPoint;

	/**
	 * 挂载容量
	 */
	private long size;

	/**
	 * 已使用容量
	 */
	private long used;

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

	public FileSystem() {
	}

	public FileSystem(com.wao.itil.model.glances.FileSystem fileSystemGlances) {
		BeanUtils.copyProperties(fileSystemGlances, this);
		deviceName = fileSystemGlances.getDevice_name();
		fsType = fileSystemGlances.getFs_type();
		mntPoint = fileSystemGlances.getMnt_point();
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getFsType() {
		return fsType;
	}

	public void setFsType(String fsType) {
		this.fsType = fsType;
	}

	public String getMntPoint() {
		return mntPoint;
	}

	public void setMntPoint(String mntPoint) {
		this.mntPoint = mntPoint;
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

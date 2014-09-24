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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 服务器磁盘使用情况模型 <code>
 * [{"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "dm-1"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda5"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda2"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "dm-0"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda1"}]
 * </code>
 */
@Entity
@Table(name = "itil_diskio")
@Searchable
@AutoConfig(namespace = "/itil")
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
@Richtable(searchable = true, order = "createDate desc", celleditable = false, readonly = @Readonly(true))
public class Diskio extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = 896528351463834696L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "diskio_entity_seq")
	@SequenceGenerator(name = "diskio_entity_seq", sequenceName = "diskio_entity_seq")
	private Long id;

	/**
	 * 磁盘分区名称
	 */
	private String diskName;

	/**
	 * 读取字节数
	 */
	private long readBytes;

	/**
	 * 写入字节数
	 */
	private long writeBytes;

	/**
	 * 持续运行时间长
	 */
	@UiConfig(hiddenInView = @Hidden(true), hiddenInInput = @Hidden(true), hiddenInList = @Hidden(true))
	private double timeSinceUpdate;

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

	public Diskio() {
	}

	public Diskio(com.wao.itil.model.glances.Diskio diskioGlances) {
		diskName = diskioGlances.getDisk_name();
		readBytes = diskioGlances.getRead_bytes();
		writeBytes = diskioGlances.getWrite_bytes();
		timeSinceUpdate = diskioGlances.getTime_since_update();
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDiskName() {
		return diskName;
	}

	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	public long getReadBytes() {
		return readBytes;
	}

	public void setReadBytes(long readBytes) {
		this.readBytes = readBytes;
	}

	public long getWriteBytes() {
		return writeBytes;
	}

	public void setWriteBytes(long writeBytes) {
		this.writeBytes = writeBytes;
	}

	public double getTimeSinceUpdate() {
		return timeSinceUpdate;
	}

	public void setTimeSinceUpdate(double timeSinceUpdate) {
		this.timeSinceUpdate = timeSinceUpdate;
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

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
 * 服务器磁盘使用情况模型 <code>
 * [{"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "dm-1"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda5"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda2"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "dm-0"}, 
 * {"time_since_update": 0.22548890113830566, "read_bytes": 0, "write_bytes": 0, "disk_name": "sda1"}]
 * </code>
 */
@Entity
@Table(name = "diskio")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Diskio extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -3347052763935939009L;

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
	
	public Diskio() {

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

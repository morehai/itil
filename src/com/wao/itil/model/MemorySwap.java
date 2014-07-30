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
 * 服务器交换内存空间使用情况模型 <code>
 * {"used": 0, "percent": 0.0, "free": 8493461504, "sout": 0, "total": 8493461504, "sin": 0}
 * </code>
 */
@Entity
@Table(name = "memory_swap")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class MemorySwap extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = 913379597228395999L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "memoryswap_entity_seq")
	@SequenceGenerator(name = "memoryswap_entity_seq", sequenceName = "memoryswap_entity_seq")
	private Long id;

	/**
	 * 总容量
	 */
	private long total;

	/**
	 * 交换内存剩余容量
	 */
	private long free;

	/**
	 * 交换内存已使用容量
	 */
	private long used;
	private long sin;
	private long sout;

	/**
	 * 交换内存使用率百分比
	 */
	private float percent;

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

	public MemorySwap() {

	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public long getFree() {
		return free;
	}

	public void setFree(long free) {
		this.free = free;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public long getSin() {
		return sin;
	}

	public void setSin(long sin) {
		this.sin = sin;
	}

	public long getSout() {
		return sout;
	}

	public void setSout(long sout) {
		this.sout = sout;
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

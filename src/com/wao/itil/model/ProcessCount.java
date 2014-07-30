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
 * 服务器进程使用情况模型 <code>
 * {"running": 1, "total": 81, "thread": 299, "sleeping": 80}
 * </code>
 */
@Entity
@Table(name = "processes_count")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class ProcessCount extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -8611389360432926850L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "processescount_entity_seq")
	@SequenceGenerator(name = "processescount_entity_seq", sequenceName = "processescount_entity_seq")
	private Long id;

	/**
	 * 进程总数量
	 */
	private long thread;

	/**
	 * 已创建的进程数
	 */
	private long total;

	/**
	 * 休眠进程数量
	 */
	private long sleeping;

	/**
	 * 运行进程数量
	 */
	private int running;

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

	public ProcessCount() {

	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getThread() {
		return thread;
	}

	public void setThread(long thread) {
		this.thread = thread;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getSleeping() {
		return sleeping;
	}

	public void setSleeping(long sleeping) {
		this.sleeping = sleeping;
	}

	public int getRunning() {
		return running;
	}

	public void setRunning(int running) {
		this.running = running;
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

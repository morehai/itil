package com.wao.itil.model;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.ironrhino.core.metadata.Authorize;
import org.ironrhino.core.metadata.AutoConfig;
import org.ironrhino.core.metadata.Hidden;
import org.ironrhino.core.metadata.NotInCopy;
import org.ironrhino.core.metadata.NotInJson;
import org.ironrhino.core.metadata.UiConfig;
import org.ironrhino.core.search.elasticsearch.annotations.Searchable;
import org.ironrhino.core.search.elasticsearch.annotations.SearchableId;
import org.ironrhino.core.security.role.UserRole;
import org.ironrhino.security.model.User;

import com.wao.itil.model.enums.AlarmNoticeType;
import com.wao.itil.model.enums.MonitorFrequenceType;
import com.wao.itil.model.enums.SequenceAlarmType;
import com.wao.itil.model.enums.ServerMonitorType;

/**
 * 服务器监控任务模型
 */
@Entity
@Table(name = "itil_task")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Task extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -3599196510760852871L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "task_entity_seq")
	@SequenceGenerator(name = "task_entity_seq", sequenceName = "task_entity_seq")
	private Long id;

	/**
	 * 被监控服务器地址
	 */
	@Column(nullable = false)
	private String host;

	/**
	 * 任务名称
	 */
	@Column(nullable = false)
	private String taskName;

	/**
	 * 监控类型（复选）
	 */
	@Enumerated
	private ServerMonitorType serverMonitorType;

	/**
	 * 监控频率（单选）
	 */
	@Enumerated
	private MonitorFrequenceType monitorFrequenceType;

	/**
	 * 持续报警时长（单选）
	 */
	@Enumerated
	private SequenceAlarmType sequenceAlarmType;

	/**
	 * 告警通知（复选）
	 */
	@Enumerated
	private AlarmNoticeType alarmNoticeType;

	/**
	 * 是否保存为模板
	 */
	private boolean templatable;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 任务是否被成功调度
	 */
	private boolean successExecuted;

	/**
	 * 上次执行时间点
	 */
	private Date lastExecuted;

	/**
	 * 创建的时间点
	 */
	@NotInCopy
	@NotInJson
	@Column(updatable = false)
	@UiConfig(hidden = true)
	private Date createTime = new Date();

	// 监控类型集合
	@UiConfig(hiddenInList = @Hidden(true))
	@Transient
	private Set<String> serverMonitors = new HashSet<String>(0);

	// 创建者
	@NotInJson
	@UiConfig(hiddenInView = @Hidden(true))
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	public Task() {

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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ServerMonitorType getServerMonitorType() {
		return serverMonitorType;
	}

	public void setServerMonitorType(ServerMonitorType serverMonitorType) {
		this.serverMonitorType = serverMonitorType;
	}

	public MonitorFrequenceType getMonitorFrequenceType() {
		return monitorFrequenceType;
	}

	public void setMonitorFrequenceType(
			MonitorFrequenceType monitorFrequenceType) {
		this.monitorFrequenceType = monitorFrequenceType;
	}

	public SequenceAlarmType getSequenceAlarmType() {
		return sequenceAlarmType;
	}

	public void setSequenceAlarmType(SequenceAlarmType sequenceAlarmType) {
		this.sequenceAlarmType = sequenceAlarmType;
	}

	public AlarmNoticeType getAlarmNoticeType() {
		return alarmNoticeType;
	}

	public void setAlarmNoticeType(AlarmNoticeType alarmNoticeType) {
		this.alarmNoticeType = alarmNoticeType;
	}

	public boolean isTemplatable() {
		return templatable;
	}

	public void setTemplatable(boolean templatable) {
		this.templatable = templatable;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean isSuccessExecuted() {
		return successExecuted;
	}

	public void setSuccessExecuted(boolean successExecuted) {
		this.successExecuted = successExecuted;
	}

	public Date getLastExecuted() {
		return lastExecuted;
	}

	public void setLastExecuted(Date lastExecuted) {
		this.lastExecuted = lastExecuted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<String> getServerMonitors() {
		return serverMonitors;
	}

	public void setServerMonitors(Set<String> serverMonitors) {
		this.serverMonitors = serverMonitors;
	}

	@NotInCopy
	@NotInJson
	@Column(name = "serverMonitors", length = 500)
	@UiConfig(hiddenInList = @Hidden(true))
	@Access(AccessType.PROPERTY)
	public String getServerMonitorsAsString() {
		if (serverMonitors.size() > 0)
			return StringUtils.join(serverMonitors.iterator(), ',');
		return null;
	}

	public void setServerMonitorsAsString(String serverMonitorsAsString) {
		serverMonitors.clear();
		if (StringUtils.isNotBlank(serverMonitorsAsString))
			serverMonitors.addAll(Arrays
					.asList(org.ironrhino.core.util.StringUtils.trimTail(
							serverMonitorsAsString, ",").split("\\s*,\\s*")));
	}

	@Override
	@NotInJson
	public boolean isNew() {
		return id == null || id == 0;
	}

}

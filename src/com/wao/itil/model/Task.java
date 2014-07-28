package com.wao.itil.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.ironrhino.core.metadata.Authorize;
import org.ironrhino.core.metadata.AutoConfig;
import org.ironrhino.core.metadata.NotInCopy;
import org.ironrhino.core.metadata.NotInJson;
import org.ironrhino.core.metadata.UiConfig;
import org.ironrhino.core.search.elasticsearch.annotations.Searchable;
import org.ironrhino.core.search.elasticsearch.annotations.SearchableId;
import org.ironrhino.core.security.role.UserRole;

import com.wao.itil.model.enums.AlarmNoticeType;
import com.wao.itil.model.enums.MonitorFrequenceType;
import com.wao.itil.model.enums.SequenceAlarmType;
import com.wao.itil.model.enums.ServerMonitorType;

/**
 * 服务器监控任务模型
 */
@Entity
@Table(name = "task")
@Searchable
@AutoConfig
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
public class Task extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = -3005854986851232552L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "task_entity_seq")
	@SequenceGenerator(name = "task_entity_seq", sequenceName = "task_entity_seq", allocationSize = 1)
	private Long id;

	/**
	 * 被监控服务器IP地址
	 */
	@Column(nullable = false)
	private String monitorIp;

	/**
	 * 任务名称
	 */
	@Column(nullable = false)
	private String taskName;

	/**
	 * 监控类型（复选）
	 */
	@Enumerated
	private ServerMonitorType monitorType;

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
	 * 创建的时间点
	 */
	@NotInCopy
	@NotInJson
	@Column(updatable = false)
	@UiConfig(hidden = true)
	private Date createTime = new Date();

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

	public String getMonitorIp() {
		return monitorIp;
	}

	public void setMonitorIp(String monitorIp) {
		this.monitorIp = monitorIp;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ServerMonitorType getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(ServerMonitorType monitorType) {
		this.monitorType = monitorType;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	@NotInJson
	public boolean isNew() {
		return id == null || id == 0;
	}

}

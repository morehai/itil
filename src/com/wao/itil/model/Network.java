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
 * 服务器网卡流量模型 <code>
 * [{"tx": 4492122, "cumulative_rx": 810046461, "rx": 4492122, "cumulative_cx": 1620092922, 
 * "time_since_update": 8086.719123840332, "cx": 8984244, "cumulative_tx": 810046461, "interface_name": "lo"}, 
 * {"tx": 543693, "cumulative_rx": 1512179542, "rx": 3449161, "cumulative_cx": 1656265981, 
 * "time_since_update": 8086.719123840332, "cx": 3992854, "cumulative_tx": 144086439, "interface_name": "eth0"}]
 * </code>
 */
@Entity
@Table(name = "itil_network")
@Searchable
@AutoConfig(namespace = "/itil")
@Authorize(ifAnyGranted = UserRole.ROLE_ADMINISTRATOR)
@Richtable(searchable = true, order = "createDate desc", celleditable = false, readonly = @Readonly(true))
public class Network extends org.ironrhino.core.model.Entity<Long> {

	private static final long serialVersionUID = 8830554327519962985L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "network_entity_seq")
	@SequenceGenerator(name = "network_entity_seq", sequenceName = "network_entity_seq")
	private Long id;
	private long rx;
	private long tx;
	private long cx;
	private long cumulativeRx;
	private long cumulativeTx;
	private long cumulativeCx;
	private String interfaceName;
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

	public Network() {
	}

	public Network(com.wao.itil.model.glances.Network networkGlances) {
		BeanUtils.copyProperties(networkGlances, this);
		cumulativeRx = networkGlances.getCumulative_rx();
		cumulativeTx = networkGlances.getCumulative_tx();
		cumulativeCx = networkGlances.getCumulative_cx();
		interfaceName = networkGlances.getInterface_name();
		timeSinceUpdate = networkGlances.getTime_since_update();
	}

	@Override
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getRx() {
		return rx;
	}

	public void setRx(long rx) {
		this.rx = rx;
	}

	public long getTx() {
		return tx;
	}

	public void setTx(long tx) {
		this.tx = tx;
	}

	public long getCx() {
		return cx;
	}

	public void setCx(long cx) {
		this.cx = cx;
	}

	public long getCumulativeRx() {
		return cumulativeRx;
	}

	public void setCumulativeRx(long cumulativeRx) {
		this.cumulativeRx = cumulativeRx;
	}

	public long getCumulativeTx() {
		return cumulativeTx;
	}

	public void setCumulativeTx(long cumulativeTx) {
		this.cumulativeTx = cumulativeTx;
	}

	public long getCumulativeCx() {
		return cumulativeCx;
	}

	public void setCumulativeCx(long cumulativeCx) {
		this.cumulativeCx = cumulativeCx;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
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

package com.wao.itil.model.glances;

import java.io.Serializable;

import org.ironrhino.core.metadata.NotInCopy;

/**
 * 服务器网卡流量模型 <code>
 * [{"tx": 4492122, "cumulative_rx": 810046461, "rx": 4492122, "cumulative_cx": 1620092922, 
 * "time_since_update": 8086.719123840332, "cx": 8984244, "cumulative_tx": 810046461, "interface_name": "lo"}, 
 * {"tx": 543693, "cumulative_rx": 1512179542, "rx": 3449161, "cumulative_cx": 1656265981, 
 * "time_since_update": 8086.719123840332, "cx": 3992854, "cumulative_tx": 144086439, "interface_name": "eth0"}]
 * </code>
 */
public class Network implements Serializable {

	private static final long serialVersionUID = -7695425448750381987L;

	private long rx;
	private long tx;
	private long cx;
	@NotInCopy
	private long cumulative_rx;
	@NotInCopy
	private long cumulative_tx;
	@NotInCopy
	private long cumulative_cx;
	@NotInCopy
	private String interface_name;
	@NotInCopy
	private float time_since_update;

	public Network() {

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

	public long getCumulative_rx() {
		return cumulative_rx;
	}

	public void setCumulative_rx(long cumulative_rx) {
		this.cumulative_rx = cumulative_rx;
	}

	public long getCumulative_tx() {
		return cumulative_tx;
	}

	public void setCumulative_tx(long cumulative_tx) {
		this.cumulative_tx = cumulative_tx;
	}

	public long getCumulative_cx() {
		return cumulative_cx;
	}

	public void setCumulative_cx(long cumulative_cx) {
		this.cumulative_cx = cumulative_cx;
	}

	public String getInterface_name() {
		return interface_name;
	}

	public void setInterface_name(String interface_name) {
		this.interface_name = interface_name;
	}

	public float getTime_since_update() {
		return time_since_update;
	}

	public void setTime_since_update(float time_since_update) {
		this.time_since_update = time_since_update;
	}

}

package com.wao.itil.model.glances;

import java.io.Serializable;

import org.ironrhino.core.metadata.NotInCopy;

/**
 * 服务器CPU物理内核和逻辑内核模型 <code>
 * {"phys": 1, "log": 2}
 * </code>
 */
public class Core implements Serializable {

	private static final long serialVersionUID = -5507812911681166515L;

	/**
	 * 物理内核数量
	 */
	private int phys;

	/**
	 * 逻辑内核数量
	 */
	@NotInCopy
	private int log;

	public Core() {

	}

	public int getPhys() {
		return phys;
	}

	public void setPhys(int phys) {
		this.phys = phys;
	}

	public int getLog() {
		return log;
	}

	public void setLog(int log) {
		this.log = log;
	}

}

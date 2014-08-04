package com.wao.itil.model.glances;

import java.io.Serializable;

/**
 * 服务器硬件传感器模型 <code>
 * {"label": "temp", "value": 40}
 * </code>
 */
public class Sensor implements Serializable {

	private static final long serialVersionUID = 1967109180506212212L;

	/**
	 * 名称
	 */
	private String label;

	/**
	 * 数值
	 */
	private int value;

	public Sensor() {

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}

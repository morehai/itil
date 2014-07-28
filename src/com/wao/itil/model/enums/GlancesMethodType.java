package com.wao.itil.model.enums;

import org.ironrhino.core.model.Displayable;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * Glances监控工具支持的所有方法枚举
 */
public enum GlancesMethodType implements Displayable {

	getCore, getCpu, getLoad, getDiskIO, getFs, getMem, getMemSwap, getNetwork, getProcessCount, getProcessList, getSystem; // getHDDTemp, getNow, getBatPercent, getSensors, getAllLimits, getAllMonitored;

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public String getDisplayName() {
		try {
			return LocalizedTextUtil.findText(getClass(), name(), ActionContext
					.getContext().getLocale(), name(), null);
		} catch (Exception e) {
			return name();
		}
	}

	public static GlancesMethodType parse(String name) {
		if (name != null)
			for (GlancesMethodType en : values())
				if (name.equals(en.name()) || name.equals(en.getDisplayName()))
					return en;
		return null;
	}

	@Override
	public String toString() {
		return getDisplayName();
	}
}

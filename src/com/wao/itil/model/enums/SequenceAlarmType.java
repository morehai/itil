package com.wao.itil.model.enums;

import org.ironrhino.core.model.Displayable;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * 服务器监控告警提醒间隔
 */
public enum SequenceAlarmType implements Displayable {

	NONE, MIN15, MIN30, MIN45, MIN70, MIN150, MIN300, MIN450;

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

	public static SequenceAlarmType parse(String name) {
		if (name != null)
			for (SequenceAlarmType en : values())
				if (name.equals(en.name()) || name.equals(en.getDisplayName()))
					return en;
		return null;
	}

	@Override
	public String toString() {
		return getDisplayName();
	}
}
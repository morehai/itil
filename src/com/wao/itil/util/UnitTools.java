package com.wao.itil.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字节单位自动匹配工具类
 */
public class UnitTools {

	/**
	 * 自动匹配容量单位
	 */
	public String autoUnit(double val) {
		double tempVal;
		Map<String, Long> units = new HashMap<String, Long>();
		List<String> orderedUnits = new ArrayList<String>();
		initializeAutoUnitMaps(units, orderedUnits);
		for (String unit : orderedUnits) {
			long unitSize = units.get(unit);
			tempVal = val / unitSize;
			if (tempVal > 1) {
				if (tempVal < 10) {
					return String.format("%.1f", tempVal) + unit;
				} else {
					return String.format("%.0f", tempVal) + unit;
				}
			}
		}
		return String.format("%.0f", val);
	}

	private void initializeAutoUnitMaps(Map<String, Long> units,
			List<String> orderedUnits) {
		units = new HashMap<String, Long>();
		units.put("E", 1152921504606846976L);
		units.put("P", 1125899906842624L);
		units.put("T", 1099511627776L);
		units.put("G", 1073741824L);
		units.put("M", 1048576L);
		units.put("K", 1024L);

		orderedUnits = new ArrayList<String>();
		orderedUnits.add("E");
		orderedUnits.add("P");
		orderedUnits.add("T");
		orderedUnits.add("G");
		orderedUnits.add("M");
		orderedUnits.add("K");
	}

}

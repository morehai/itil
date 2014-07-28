package com.wao.itil.service.support;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.ironrhino.core.util.HttpClientUtils;
import org.springframework.stereotype.Component;

/**
 * 使用Glances工具收集服务器数据
 */
@Component
public class GlancesService {

	public String getServerInfoByAgent(String hostIp, String method)
			throws IOException {

		if (StringUtils.isNotBlank(hostIp)) {
			hostIp = "http://" + hostIp + ":61209/RPC2";
		}
		if (StringUtils.isNotBlank(method)) {
			hostIp = "<methodName>" + method + "</methodName>";
		}
		
		String resp = HttpClientUtils.post(hostIp, method);
		if (resp.indexOf("<string>") > 0) {
			resp = resp.substring(resp.indexOf("<string>") + 8,
					resp.indexOf("</string>"));
		} else {
			return null;
		}
		return resp;
	}

}

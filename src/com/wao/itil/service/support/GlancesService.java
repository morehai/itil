package com.wao.itil.service.support;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.ironrhino.core.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 使用Glances工具收集服务器数据
 */
@Component
public class GlancesService {

	// TODO 这里的ip和method通过任务调度传入，分解为最细粒度的单任务执行
	@Value("${glances.server:ip}")
	private String ip;
	@Value("${glances.method:method}")
	private String method;

	public String client() throws IOException {

		if (StringUtils.isNotBlank(ip)) {
			ip = "http://" + ip + ":61209/RPC2";
		}
		String resp = HttpClientUtils.post(ip, method);
		if (resp.indexOf("<string>") > 0) {
			resp = resp.substring(resp.indexOf("<string>") + 8,
					resp.indexOf("</string>"));
		} else {
			return null;
		}
		return resp;
	}

}

package com.itil.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ironrhino.core.util.HttpClientUtils;

public class TestZabbixAPI {

	private static String ZABBIX_API_URL = "http://192.168.0.212/zabbix/api_jsonrpc.php";
	private static Map<String, String> headers = new HashMap<String, String>();
	static {
		// headers.put(
		// "User-Agent",
		// "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.13 (KHTML, like Gecko) Chrome/24.0.1284.2 Safari/537.13");
		headers.put("Content-Type", "application/json-rpc");
	}
	private static String BODY = "{\"jsonrpc\":\"2.0\",\"method\":\"user.login\",\"params\":{\"user\":\"itadmin\",\"password\":\"itadmin&!&!\"},\"auth\":null,\"id\":0}";

	public static void main(String[] args) {
		String content = null;
		try {
			content = HttpClientUtils.postResponseText(ZABBIX_API_URL, BODY,
					headers, "UTF-8");
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

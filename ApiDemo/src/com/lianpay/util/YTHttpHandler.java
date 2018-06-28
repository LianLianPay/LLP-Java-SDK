package com.lianpay.util;

import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * Http请求处理类
 * 
 * @version V1.0
 * @author dujl@lianlian.com
 * @Date 2016年9月19日 上午22:19:48
 * @since JDK 1.6
 */
public class YTHttpHandler {

	private static Logger logger = Logger.getLogger(YTHttpHandler.class);

	private static YTHttpHandler instance;

	private YTHttpHandler() {

	}

	public static YTHttpHandler getInstance() {
		if (null == instance)
			return new YTHttpHandler();
		return instance;
	}

	public String doRequestPostString(String reqStr, String uri) {
		HttpClient httpClient = HttpClientFactory.getThreadSafeHttpClient();
		HttpPost httpPost = new HttpPost(StringUtils.trim(uri));
		httpPost.setHeader(HTTP.USER_AGENT, "httpcomponents");
		HttpResponse response = null;
		HttpEntity entity = null;
		StringEntity stringEntiry = null;
		try {
			stringEntiry = new StringEntity(reqStr, "utf-8");
			httpPost.setEntity(stringEntiry);
			long start = System.currentTimeMillis();
			response = httpClient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			// 获取响应内容
			String retData = null;
			if (null != response) {
				entity = response.getEntity();
				retData = StringEscapeUtils.unescapeJava(StringUtils.trim(EntityUtils.toString(entity, "utf-8")));
			}
			if (HttpStatus.SC_OK == status) { // 请求成功
				return retData;
			}
		} catch (Exception e) { // 读取响应错误
			// TODO: handle exception
		} finally {
			try {
				if (null != stringEntiry)
					EntityUtils.consume(stringEntiry);
				httpPost.abort(); // 释放连接资源
			} catch (Exception e) {
				// TODO: handle exception		
			}
		}
		return "error";
	}

	public static void main(String[] args) {
		// 调用样例
		String requestJson = "";
		String uri = "";
		YTHttpHandler.getInstance().doRequestPostString(requestJson, uri);
	}
}

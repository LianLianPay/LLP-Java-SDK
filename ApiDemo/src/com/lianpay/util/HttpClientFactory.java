package com.lianpay.util;

import java.security.KeyStore;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.log4j.Logger;

/**
 * 创建线程安全HttpClient对象
 * 
 * @version V1.0
 * @author dujl@lianlian.com
 * @Date 2016年9月19日 上午22:19:48
 * @since JDK 1.6
 */
public class HttpClientFactory {

	private static Logger logger = Logger.getLogger(HttpClientFactory.class);

	private static DefaultHttpClient httpClient = httpClientInstance();

	private static final int MaxTotal = Integer.MAX_VALUE; // 连接池最大连接数

	private static final int MaxPerRoute = Integer.MAX_VALUE; // 每个路由默认最大连接数

	private static final int SoTimeout = 30 * 1000; // 读取响应超时

	private static final int ConnectionTimeout = 5 * 1000; // 连接超时

	public static HttpClient getThreadSafeHttpClient() {
		return httpClient;
	}

	/**
	 * 创建HttpClient对象
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static DefaultHttpClient httpClientInstance() {
		SSLSocketFactory sf = null;
		KeyStore trustStore = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new YTSSLSocketFactory(trustStore);
		} catch (Exception e) {
			logger.error("创建SSLSocket异常," + e.getMessage(), e);
			return httpClient;
		}
		// 设置访问协议
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", sf, 443));
		// 多连接的线程安全的管理器
		PoolingClientConnectionManager conMgr = new PoolingClientConnectionManager(schReg);
		conMgr.setMaxTotal(MaxTotal); // 连接池最大连接数
		conMgr.setDefaultMaxPerRoute(MaxPerRoute); // 每个路由默认最大连接数
		httpClient = new DefaultHttpClient(conMgr);
		// 设置组件参数
		HttpParams params = httpClient.getParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "utf-8");
		HttpConnectionParams.setConnectionTimeout(params, ConnectionTimeout);
		HttpConnectionParams.setSoTimeout(params, SoTimeout);
		httpClient = new DefaultHttpClient(conMgr);
		if (logger.isInfoEnabled())
			logger.info("HttpClient对象创建完成...");
		return httpClient;
	}
}

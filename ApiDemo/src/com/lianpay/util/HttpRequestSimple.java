package com.lianpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class HttpRequestSimple{
    private static Log          log          = LogFactory
                                                     .getLog(HttpRequestSimple.class);

    private final static String CONN_TIMEOUT = "ConnectTimeout";

    private final static String CONN_FAIL    = "CONN_FAIL";

    public String postSendHttp(String url, String body)
    {
        if (url == null || "".equals(url))
        {
            log.error("request url is empty.");
            return null;
        }
        HttpClient httpClient = CustomHttpClient.GetHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "text/html;charset=UTF-8");
        try
        {
            StringEntity stringEntity = new StringEntity(body, "UTF-8");
            stringEntity.setContentEncoding(new BasicHeader(
                    HTTP.CONTENT_ENCODING, "UTF-8"));
            // 设置请求主体
            post.setEntity(stringEntity);
            // 发起交易
            HttpResponse resp = httpClient.execute(post);
            // 响应分析
            HttpEntity entity = resp.getEntity();

            BufferedReader br = new BufferedReader(new InputStreamReader(entity
                    .getContent(), "UTF-8"));
            StringBuffer responseString = new StringBuffer();
            String result = br.readLine();
            while (result != null)
            {
                responseString.append(result);
                result = br.readLine();
            }

            return responseString.toString();
        } catch (ConnectTimeoutException cte)
        {
            log.error(cte.getMessage(), cte);
            return null;
        } catch (SocketTimeoutException cte)
        {
            log.error(cte.getMessage(), cte);
            return null;
        } catch (Exception e)
        {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public String getSendHttp(String url)
    {
        if (url == null || "".equals(url))
        {
            log.error("request url is empty.");
            return null;
        }
        HttpClient httpClient = CustomHttpClient.GetHttpClient();
        HttpGet get = new HttpGet(url);
        get.setHeader("Content-Type", "text/html;charset=UTF-8");
        try
        {
            // 发起交易
            HttpResponse resp = httpClient.execute(get);
            // 响应分析
            HttpEntity entity = resp.getEntity();

            BufferedReader br = new BufferedReader(new InputStreamReader(entity
                    .getContent()));
            StringBuffer responseString = new StringBuffer();
            String result = br.readLine();
            while (result != null)
            {
                responseString.append(result);
                result = br.readLine();
            }

            return responseString.toString();
        } catch (ConnectTimeoutException cte)
        {
            log.error(cte.getMessage(), cte);
            return null;
        } catch (SocketTimeoutException cte)
        {
            log.error(cte.getMessage(), cte);
            return null;
        } catch (Exception e)
        {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public String postPramaList(List<NameValuePair> params, String url)
    {
        HttpClient httpClient = CustomHttpClient.GetHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");
        BufferedReader br = null;
        try
        {
            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(params,
                    HTTP.UTF_8);
            // 设置请求参数
            post.setEntity(formEntiry);
            // 发起交易
            HttpResponse resp = httpClient.execute(post);
            int ret = resp.getStatusLine().getStatusCode();
            if (ret == HttpStatus.SC_OK)
            {
                // 响应分析
                HttpEntity entity = resp.getEntity();

                br = new BufferedReader(new InputStreamReader(entity
                        .getContent(), "GBK"));
                StringBuffer responseString = new StringBuffer();
                String result = br.readLine();
                while (result != null)
                {
                    responseString.append(result);
                    result = br.readLine();
                }
                return responseString.toString();
            } else
            {
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    // do nothing
                }
            }
        }
    }

}

package com.lianpay.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 * 
 * @创建人： 郭锐
 * @创建时间：2010-9-10 下午01:58:27
 */
public class ClientHttp{
    private Logger       logger       = Logger.getLogger(getClass());
    private String       correlationID;
    private ClientResult clientResult = null;

    public String getCorrelationID()
    {
        return correlationID;
    }

    public ClientResult getClientResult()
    {
        return clientResult;
    }

    public void setClientResult(ClientResult clientResult)
    {
        this.clientResult = clientResult;
    }

    public void setCorrelationID(String correlationID)
    {
        this.correlationID = correlationID;
    }

    /**
     * 总的发送入口
     * @param map
     * @return
     */
    public ClientResult sendHttp(ClientParams clientParams)
    {
        clientResult = null;
        // 是否传入url
        if (null == clientParams.getHttp_request_url())
        {
            logHttp(ClientGlobals.res_code_failed,
                    ClientGlobals.res_msg_failed_request_params_error);
            clientResult = new ClientResult();
            clientResult.setRes_code(ClientGlobals.res_code_failed);
            clientResult
                    .setRes_value(ClientGlobals.res_msg_failed_request_params_error);
            return clientResult;
        }
        // 检查是否传入correlationID
        if (null == clientParams.getCorrelationID())
        {
            logHttp(ClientGlobals.res_code_failed,
                    ClientGlobals.res_msg_failed_request_params_error);
            clientResult = new ClientResult();
            clientResult.setRes_code(ClientGlobals.res_code_failed);
            clientResult
                    .setRes_value(ClientGlobals.res_msg_failed_request_params_error);
            return clientResult;
        }
        setCorrelationID(clientParams.getCorrelationID());
        String method = clientParams.getHttp_method();
        if (method.equals(ClientGlobals.http_request_method_postObject_value))
        {
            return postObject(clientParams);
        } else if (method.equals(ClientGlobals.http_request_method_get_value))
        {
            return getHttp(clientParams);
        }
        return postHttp(clientParams);
    }

    /**
     * http发送日志
     * @param relationid
     * @param errorCode
     * @param errorMsg
     */
    public void logHttp(String errorCode, String errorMsg)
    {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("[sendType:http,correlationID:");
        strBuf.append(getCorrelationID());
        strBuf.append(",errorCode:");
        strBuf.append(errorCode);
        strBuf.append(",errorMsg:");
        strBuf.append(errorMsg);
        strBuf.append("]");
        logger.info(strBuf.toString());
    }

    /**
     * 读取httpclient请求响应流
     * @param inputStream
     * @return
     */
    private String readBuffer(InputStream inputStream)
    {
        BufferedReader br = null;
        StringBuffer sReturnBuf = new StringBuffer();
        try
        {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String readStr = null;
            readStr = br.readLine();
            while (readStr != null)
            {
                sReturnBuf.append(readStr);
                readStr = br.readLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                    br = null;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sReturnBuf.toString();
    }

    /**
     * 发送方法
     * @param httpClient
     * @param response
     * @param method
     * @return
     */
    private HttpResponse sendHttp(HttpClient httpClient, HttpUriRequest method)
    {
        clientResult = null;
        HttpResponse result = null;
        // http发起请求
        try
        {
            result = httpClient.execute(method);
        } catch (ClientProtocolException e)// 协议错误
        {
            if (null == clientResult)
            {
                logHttp(ClientGlobals.res_code_failed,
                        ClientGlobals.res_msg_failed_protocol_wrong);
                clientResult = new ClientResult();
                clientResult.setRes_code(ClientGlobals.res_code_failed);
                clientResult
                        .setRes_value(ClientGlobals.res_msg_failed_protocol_wrong);
            }
        } catch (HttpHostConnectException e)// 拒绝连接
        {
            if (null == clientResult)
            {
                logHttp(ClientGlobals.res_code_failed,
                        ClientGlobals.res_msg_failed_connection_refuse);
                clientResult = new ClientResult();
                clientResult.setRes_code(ClientGlobals.res_code_failed);
                clientResult
                        .setRes_value(ClientGlobals.res_msg_failed_connection_refuse);
            }
            return null;
        } catch (ConnectTimeoutException e)// 连接超时
        {
            if (null == clientResult)
            {
                logHttp(ClientGlobals.res_code_failed,
                        ClientGlobals.res_msg_failed_connection_timeout);
                clientResult = new ClientResult();
                clientResult.setRes_code(ClientGlobals.res_code_failed);
                clientResult
                        .setRes_value(ClientGlobals.res_msg_failed_connection_timeout);
            }
        } catch (IOException e)// 读取响应超时
        {
            if (null == clientResult)
            {
                logHttp(ClientGlobals.res_code_exception,
                        ClientGlobals.res_msg_exception_socket_timeout);
                clientResult = new ClientResult();
                clientResult.setRes_code(ClientGlobals.res_code_exception);
                clientResult
                        .setRes_value(ClientGlobals.res_msg_exception_socket_timeout);
            }
        }
        return result;
    }

    /**
     * 发送序列化对象
     * @param clientParams
     * @return
     */
    private ClientResult postObject(ClientParams clientParams)
    {
        HttpClient httpClient = null;
        try
        {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(
                    ClientGlobals.http_request_connection_timeout_key,
                    clientParams.getHttp_connection_timeout());
            httpClient.getParams().setParameter(
                    ClientGlobals.http_request_socket_timeout_key,
                    clientParams.getHttp_socket_timeout());
            HttpPost httpPost = new HttpPost(clientParams.getHttp_request_url());
            httpPost.setHeader("Content-Type", clientParams
                    .getHttp_content_type());
            ByteArrayOutputStream bOut = new ByteArrayOutputStream(1024);
            ByteArrayInputStream bInput = null;
            ObjectOutputStream out = null;
            Serializable returnObj = null;
            // 解析输入参数
            try
            {
                out = new ObjectOutputStream(bOut);
                out.writeObject(clientParams.getHttp_inputobject());
            } catch (IOException e)
            {
                logHttp(ClientGlobals.res_code_failed,
                        ClientGlobals.res_msg_failed_parse_inputparam);
                clientResult = new ClientResult();
                clientResult.setRes_code(ClientGlobals.res_code_failed);
                clientResult
                        .setRes_value(ClientGlobals.res_msg_failed_parse_inputparam);
            } finally
            {
                if (null != out)
                {
                    try
                    {
                        out.flush();
                        out.close();
                    } catch (IOException e)
                    {
                        if (null == clientResult)
                        {
                            logHttp(
                                    ClientGlobals.res_code_failed,
                                    ClientGlobals.res_msg_failed_parse_inputparam_close);
                            clientResult = new ClientResult();
                            clientResult
                                    .setRes_code(ClientGlobals.res_code_failed);
                            clientResult
                                    .setRes_value(ClientGlobals.res_msg_failed_parse_inputparam_close);
                        }
                    }
                }
                if (null != clientResult)
                    return clientResult;
            }
            bInput = new ByteArrayInputStream(bOut.toByteArray());
            InputStreamEntity entity = new InputStreamEntity(bInput, -1);
            httpPost.setEntity(entity);
            HttpResponse response = null;
            // http发起请求
            response = sendHttp(httpClient, httpPost);
            if (null != clientResult)
                return clientResult;
            // 解析响应
            if (null != response)
            {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200)
                {
                    logHttp(ClientGlobals.res_code_failed,
                            ClientGlobals.res_msg_failed_response_error_status);
                    clientResult = new ClientResult();
                    clientResult.setRes_code(ClientGlobals.res_code_failed);
                    clientResult
                            .setRes_msg(ClientGlobals.res_msg_failed_response_error_status);
                    return clientResult;
                }
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null)
                {
                    InputStream is = null;
                    ObjectInputStream oInput = null;
                    try
                    {
                        is = resEntity.getContent();
                        oInput = new ObjectInputStream(is);
                        returnObj = (Serializable) oInput.readObject();
                    } catch (IllegalStateException e)
                    {
                        logHttp(
                                ClientGlobals.res_code_exception,
                                ClientGlobals.res_msg_exception_parse_res_illegalstate);
                        clientResult = new ClientResult();
                        clientResult
                                .setRes_code(ClientGlobals.res_code_exception);
                        clientResult
                                .setRes_value(ClientGlobals.res_msg_exception_parse_res_illegalstate);
                    } catch (IOException e)
                    {
                        logHttp(ClientGlobals.res_code_exception,
                                ClientGlobals.res_msg_exception_parse_res_io);
                        clientResult = new ClientResult();
                        clientResult
                                .setRes_code(ClientGlobals.res_code_exception);
                        clientResult
                                .setRes_value(ClientGlobals.res_msg_exception_parse_res_io);
                    } catch (ClassNotFoundException e)
                    {
                        logHttp(
                                ClientGlobals.res_code_exception,
                                ClientGlobals.res_msg_exception_parse_res_classnotfound);
                        clientResult = new ClientResult();
                        clientResult
                                .setRes_code(ClientGlobals.res_code_exception);
                        clientResult
                                .setRes_value(ClientGlobals.res_msg_exception_parse_res_classnotfound);
                    } finally
                    {
                        if (null != clientResult)
                            return clientResult;
                    }
                } else
                {
                    logHttp(
                            ClientGlobals.res_code_exception,
                            ClientGlobals.res_msg_exception_parse_res_entitynull);
                    clientResult = new ClientResult();
                    clientResult.setRes_code(ClientGlobals.res_code_exception);
                    clientResult
                            .setRes_msg(ClientGlobals.res_msg_exception_parse_res_entitynull);
                    return clientResult;
                }
            } else
            {
                logHttp(ClientGlobals.res_code_exception,
                        ClientGlobals.res_msg_exception_parse_res_null);
                clientResult = new ClientResult();
                clientResult.setRes_code(ClientGlobals.res_code_exception);
                clientResult
                        .setRes_msg(ClientGlobals.res_msg_exception_parse_res_null);
                return clientResult;
            }
            clientResult = new ClientResult();
            clientResult.setRes_code(ClientGlobals.res_code_success);
            clientResult.setRes_msg(ClientGlobals.res_msg_success);
            clientResult.setRes_value(returnObj);
            return clientResult;
        } finally
        {
            if (null != httpClient)
                httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * post方式发送请求
     * @param clientParams
     * @return
     */
    private ClientResult postHttp(ClientParams clientParams)
    {
        DefaultHttpClient httpClient = null;
        try
        {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(
                    ClientGlobals.http_request_connection_timeout_key,
                    clientParams.getHttp_connection_timeout());
            httpClient.getParams().setParameter(
                    ClientGlobals.http_request_socket_timeout_key,
                    clientParams.getHttp_socket_timeout());
            HttpPost httpPost = new HttpPost(clientParams.getHttp_request_url());
            httpPost.setHeader("Content-Type", clientParams
                    .getHttp_content_type());
            String bodyString = clientParams.getSendBodyStr();
            String returnvalue = null;
            logger.info("http请求:correlationID:" + getCorrelationID() + "["
                    + bodyString + "]");
            // 传入Post参数
            ByteArrayInputStream in = null;
            try
            {
                in = new ByteArrayInputStream(bodyString.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e1)
            {
                if (null == clientResult)
                {
                    logHttp(ClientGlobals.res_code_failed,
                            ClientGlobals.res_msg_failed_parse_inputparam);
                    clientResult = new ClientResult();
                    clientResult.setRes_code(ClientGlobals.res_code_failed);
                    clientResult
                            .setRes_msg(ClientGlobals.res_msg_failed_parse_inputparam);
                }
            } finally
            {
                if (null != clientResult)
                    return clientResult;
            }
            InputStreamEntity entity = new InputStreamEntity(in, -1);
            httpPost.setEntity(entity);
            HttpResponse response = null;
            // http发起请求
            response = sendHttp(httpClient, httpPost);
            if (null != clientResult)
                return clientResult;
            // 解析响应
            if (null != response)
            {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200)
                {
                    logHttp(ClientGlobals.res_code_failed,
                            ClientGlobals.res_msg_failed_response_error_status);
                    clientResult = new ClientResult();
                    clientResult.setRes_code(ClientGlobals.res_code_failed);
                    clientResult
                            .setRes_msg(ClientGlobals.res_msg_failed_response_error_status);
                    return clientResult;
                }
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null)
                {
                    try
                    {
                        returnvalue = readBuffer(resEntity.getContent());
                        resEntity.consumeContent();
                    } catch (IllegalStateException e)
                    {
                        if (null == clientResult)
                        {
                            logHttp(
                                    ClientGlobals.res_code_exception,
                                    ClientGlobals.res_msg_exception_parse_res_illegalstate);
                            clientResult = new ClientResult();
                            clientResult
                                    .setRes_code(ClientGlobals.res_code_exception);
                            clientResult
                                    .setRes_msg(ClientGlobals.res_msg_exception_parse_res_illegalstate);
                        }
                    } catch (IOException e)
                    {
                        if (null == clientResult)
                        {
                            logHttp(
                                    ClientGlobals.res_code_exception,
                                    ClientGlobals.res_msg_exception_parse_res_consumecontent);
                            clientResult = new ClientResult();
                            clientResult
                                    .setRes_code(ClientGlobals.res_code_exception);
                            clientResult
                                    .setRes_msg(ClientGlobals.res_msg_exception_parse_res_consumecontent);
                        }
                    } finally
                    {
                        if (null != clientResult)
                            return clientResult;
                    }
                } else
                {
                    logHttp(
                            ClientGlobals.res_code_exception,
                            ClientGlobals.res_msg_exception_parse_res_entitynull);
                    clientResult = new ClientResult();
                    clientResult.setRes_code(ClientGlobals.res_code_exception);
                    clientResult
                            .setRes_msg(ClientGlobals.res_msg_exception_parse_res_consumecontent);
                    return clientResult;
                }
            } else
            {
                logHttp(ClientGlobals.res_code_exception,
                        ClientGlobals.res_msg_exception_parse_res_null);
                clientResult = new ClientResult();
                clientResult.setRes_code(ClientGlobals.res_code_exception);
                clientResult
                        .setRes_msg(ClientGlobals.res_msg_exception_parse_res_null);
                return clientResult;
            }
            logger.info("http响应:correlationID:" + getCorrelationID() + "["
                    + returnvalue + "]");
            clientResult = new ClientResult();
            clientResult.setRes_code(ClientGlobals.res_code_success);
            clientResult.setRes_msg(ClientGlobals.res_msg_success);
            clientResult.setRes_value(returnvalue);
            return clientResult;
        } finally
        { // httpclient资源释放
            if (null != httpClient)
                httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * get方式发送请求
     * @param clientParams
     * @return
     */
    private ClientResult getHttp(ClientParams clientParams)
    {
        DefaultHttpClient httpClient = null;
        try
        {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(
                    ClientGlobals.http_request_connection_timeout_key,
                    clientParams.getHttp_connection_timeout());
            httpClient.getParams().setParameter(
                    ClientGlobals.http_request_socket_timeout_key,
                    clientParams.getHttp_socket_timeout());
            HttpGet httpGet = new HttpGet(clientParams.getHttp_request_url());
            httpGet.setHeader("Content-Type", clientParams
                    .getHttp_content_type());
            logger.info("http请求:correlationID:" + getCorrelationID() + "["
                    + clientParams.getHttp_request_url() + "]");
            String returnvalue = null;
            HttpResponse response = null;
            // http发起请求
            response = sendHttp(httpClient, httpGet);
            if (null != clientResult)
                return clientResult;
            // 解析响应
            if (null != response)
            {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null)
                {
                    try
                    {
                        returnvalue = readBuffer(resEntity.getContent());
                        resEntity.consumeContent();
                    } catch (IllegalStateException e)
                    {
                        if (null == clientResult)
                        {
                            logHttp(
                                    ClientGlobals.res_code_exception,
                                    ClientGlobals.res_msg_exception_parse_res_illegalstate);
                            clientResult = new ClientResult();
                            clientResult
                                    .setRes_code(ClientGlobals.res_code_exception);
                            clientResult
                                    .setRes_msg(ClientGlobals.res_msg_exception_parse_res_illegalstate);
                        }
                    } catch (IOException e)
                    {
                        if (null == clientResult)
                        {
                            logHttp(
                                    ClientGlobals.res_code_exception,
                                    ClientGlobals.res_msg_exception_parse_res_consumecontent);
                            clientResult = new ClientResult();
                            clientResult
                                    .setRes_code(ClientGlobals.res_code_exception);
                            clientResult
                                    .setRes_msg(ClientGlobals.res_msg_exception_parse_res_consumecontent);
                        }
                    } finally
                    {
                        if (null != clientResult)
                            return clientResult;
                    }
                } else
                {
                    logHttp(
                            ClientGlobals.res_code_exception,
                            ClientGlobals.res_msg_exception_parse_res_entitynull);
                    clientResult = new ClientResult();
                    clientResult.setRes_code(ClientGlobals.res_code_exception);
                    clientResult
                            .setRes_msg(ClientGlobals.res_msg_exception_parse_res_entitynull);
                    return clientResult;
                }
            } else
            {
                logHttp(ClientGlobals.res_code_exception,
                        ClientGlobals.res_msg_exception_parse_res_null);
                clientResult = new ClientResult();
                clientResult.setRes_code(ClientGlobals.res_code_exception);
                clientResult
                        .setRes_msg(ClientGlobals.res_msg_exception_parse_res_null);
                return clientResult;
            }
            logger.info("http响应:correlationID:" + getCorrelationID() + "["
                    + returnvalue + "]");
            clientResult = new ClientResult();
            clientResult.setRes_code(ClientGlobals.res_code_success);
            clientResult.setRes_msg(ClientGlobals.res_msg_success);
            clientResult.setRes_value(returnvalue);
            return clientResult;
        } finally
        { // 释放httpclient资源
            if (null != httpClient)
                httpClient.getConnectionManager().shutdown();
        }
    }
}
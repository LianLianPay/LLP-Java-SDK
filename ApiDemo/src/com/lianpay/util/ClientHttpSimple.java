package com.lianpay.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

public class ClientHttpSimple{
    /**
     * @param url
     * @param queryString
     *            类似a=b&c=d 形式的参数
     * @param inputObj
     *            发送到服务器的对象。
     * @return 服务器返回到客户端的对象,发生异常时候返回 null 修改人:杨小孟 2010-5-27
     */
    public Object postObject(String url, String queryString, Object inputObj)
    {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setQueryString(queryString);
        post.setRequestHeader("Content-Type", "application/octet-stream");
        java.io.ByteArrayOutputStream bOut = new java.io.ByteArrayOutputStream(
                1024);
        java.io.ByteArrayInputStream bInput = null;
        java.io.ObjectOutputStream out = null;
        Serializable returnObj = null;
        try
        {
            out = new java.io.ObjectOutputStream(bOut);
            out.writeObject(inputObj);
            out.flush();
            out.close();
            out = null;
            bInput = new java.io.ByteArrayInputStream(bOut.toByteArray());
            RequestEntity re = new InputStreamRequestEntity(bInput);
            post.setRequestEntity(re);
            client.executeMethod(post);
            java.io.InputStream in = post.getResponseBodyAsStream();
            java.io.ObjectInputStream oInput = new java.io.ObjectInputStream(in);
            returnObj = (Serializable) oInput.readObject();
            oInput.close();
            oInput = null;
        } catch (IOException e)
        {
            e.printStackTrace();
            returnObj = null;
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            returnObj = null;
        } finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                    out = null;
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (bInput != null)
                {

                    bInput.close();
                    bInput = null;
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // 释放连接
            post.releaseConnection();
        }
        return returnObj;
    }

    public void sendObject(String url, String queryString, Object inputObj)
    {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setQueryString(queryString);
        post.setRequestHeader("Content-Type", "application/octet-stream");
        java.io.ByteArrayOutputStream bOut = new java.io.ByteArrayOutputStream(
                1024);
        java.io.ByteArrayInputStream bInput = null;
        java.io.ObjectOutputStream out = null;
        try
        {
            out = new java.io.ObjectOutputStream(bOut);
            out.writeObject(inputObj);
            out.flush();
            out.close();
            out = null;
            bInput = new java.io.ByteArrayInputStream(bOut.toByteArray());
            RequestEntity re = new InputStreamRequestEntity(bInput);
            post.setRequestEntity(re);
            client.executeMethod(post);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                    out = null;
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (bInput != null)
                {

                    bInput.close();
                    bInput = null;
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // 释放连接
            post.releaseConnection();
        }
    }

    /**
     * @param url
     * @param queryString
     *            类似a=b&c=d 形式的参数
     * @return 服务端返回的结果。
     * @throws IOException
     */
    public String getSendHttp(String url, String queryString)
            throws IOException
    {
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(url);
        get.setQueryString(queryString);
        get.setRequestHeader("Content-Type", "application/octet-stream");
        String returnStr = null;
        try
        {
            client.executeMethod(get);
            returnStr = get.getResponseBodyAsString();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            // 释放连接
            get.releaseConnection();
        }
        return returnStr;
    }

    /**
     * @param url
     * @param queryString
     *            类似a=b&c=d 形式的参数
     * @return 服务端返回的结果。
     * @throws IOException
     */
    public String postSendHttp(String url, String queryString, String bodyString)
            throws IOException
    {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        client.getParams().setContentCharset("GBK"); // 设置httpclient的传输编码
        post.setQueryString(queryString);
        post.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
        post.setRequestHeader("CharacterEncoding", "GBK");
        post.setRequestBody(bodyString);

        String returnStr = null;
        try
        {
            client.executeMethod(post);
            // returnStr = post.getResponseBodyAsString();
            // System.out.println(post.getResponseBodyAsStream());
            InputStream in = post.getResponseBodyAsStream();
            byte[] temp = new byte[1024];
            int c;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((c = in.read(temp)) != -1)
            {
                out.write(temp, 0, c);
            }
            returnStr = new String(out.toByteArray(), "UTF-8");
            // System.out.println(returnStr);
            out.flush();
            out.close();
            in.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            // 释放连接
            post.releaseConnection();
        }
        return returnStr;
    }

    /**
     * @param url
     * @param queryString
     *            类似a=b&c=d 形式的参数
     * @param inputObj
     *            发送到服务器的对象。
     * @return 服务器返回到客户端的对象,发生异常时候返回 null 修改人:杨小孟 2010-5-27
     */
    public String postObjectReturnBody(String url, String queryString,
            Object inputObj)
    {
        String returnBody = "";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setQueryString(queryString);
        post.setRequestHeader("Content-Type", "application/octet-stream");
        java.io.ByteArrayOutputStream bOut = new java.io.ByteArrayOutputStream(
                1024);
        java.io.ByteArrayInputStream bInput = null;
        java.io.ObjectOutputStream out = null;
        try
        {
            out = new java.io.ObjectOutputStream(bOut);
            out.writeObject(inputObj);
            out.flush();
            out.close();
            out = null;
            bInput = new java.io.ByteArrayInputStream(bOut.toByteArray());
            RequestEntity re = new InputStreamRequestEntity(bInput);
            post.setRequestEntity(re);
            client.executeMethod(post);
            returnBody = post.getResponseBodyAsString();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                    out = null;
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (bInput != null)
                {

                    bInput.close();
                    bInput = null;
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // 释放连接
            post.releaseConnection();
        }
        return returnBody;
    }

}

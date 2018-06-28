package com.lianpay.util;

/**
 * 
 * @创建人： 郭锐
 *
 * @创建时间：2010-9-10 下午02:30:55
 */
public class ClientParams{
    private String  sendType;                                             // 发送方式,(http,activemq);
    private String  sendBodyStr;                                          // 发送内容
    private String  correlationID;                                        // 唯一标识本次操作
    // http发送参数
    private Integer http_connection_timeout = 5000;                       // http连接超时时间
    private Integer http_socket_timeout     = 1200000;                    // 读取响应超时时间
    private String  http_request_url;                                     // http请求url
    private String  http_method             = "postObject";               // http发送方法("getHttp","postHttp","postObject")
    private Object  http_inputobject;                                     // 发送对象
    private String  http_content_type       = "application/octet-stream"; // http内容类型("application/octet-stream","text/html;charset=UTF-8")
    // activemq发送参数
    private long    amq_msg_timeout         = 1200000;                    // 消息过期时间
    private String  amq_method              = "asyn";                     // 发送方式("asyn,"syn")同步和异步
    private String  amq_request_dataRules;                                // 分库规则
    private String  destinationName;                                      // 发送目的地名称
    private int     amq_msg_readtimeout     = 600000;                     // 读取消息超时时间(默认:1分钟)
    private int     amq_msg_readsleeptime   = 100;                        // 读取消息睡眠时间(默认:100毫秒)

    public String getSendType()
    {
        return sendType;
    }

    public void setSendType(String sendType)
    {
        this.sendType = sendType;
    }

    public String getSendBodyStr()
    {
        return sendBodyStr;
    }

    public void setSendBodyStr(String sendBodyStr)
    {
        this.sendBodyStr = sendBodyStr;
    }

    public String getCorrelationID()
    {
        return correlationID;
    }

    public void setCorrelationID(String correlationID)
    {
        this.correlationID = correlationID;
    }

    public String getHttp_request_url()
    {
        return http_request_url;
    }

    public void setHttp_request_url(String http_request_url)
    {
        this.http_request_url = http_request_url;
    }

    public String getHttp_method()
    {
        return http_method;
    }

    public void setHttp_method(String http_method)
    {
        this.http_method = http_method;
    }

    public Object getHttp_inputobject()
    {
        return http_inputobject;
    }

    public void setHttp_inputobject(Object http_inputobject)
    {
        this.http_inputobject = http_inputobject;
    }

    public String getHttp_content_type()
    {
        return http_content_type;
    }

    public void setHttp_content_type(String http_content_type)
    {
        this.http_content_type = http_content_type;
    }

    public long getAmq_msg_timeout()
    {
        return amq_msg_timeout;
    }

    public void setAmq_msg_timeout(long amq_msg_timeout)
    {
        this.amq_msg_timeout = amq_msg_timeout;
    }

    public String getAmq_method()
    {
        return amq_method;
    }

    public void setAmq_method(String amq_method)
    {
        this.amq_method = amq_method;
    }

    public String getAmq_request_dataRules()
    {
        return amq_request_dataRules;
    }

    public void setAmq_request_dataRules(String amq_request_dataRules)
    {
        this.amq_request_dataRules = amq_request_dataRules;
    }

    public int getAmq_msg_readtimeout()
    {
        return amq_msg_readtimeout;
    }

    public void setAmq_msg_readtimeout(int amq_msg_readtimeout)
    {
        this.amq_msg_readtimeout = amq_msg_readtimeout;
    }

    public int getAmq_msg_readsleeptime()
    {
        return amq_msg_readsleeptime;
    }

    public void setAmq_msg_readsleeptime(int amq_msg_readsleeptime)
    {
        this.amq_msg_readsleeptime = amq_msg_readsleeptime;
    }

    public String getDestinationName()
    {
        return destinationName;
    }

    public void setDestinationName(String destinationName)
    {
        this.destinationName = destinationName;
    }

    public Integer getHttp_connection_timeout()
    {
        return http_connection_timeout;
    }

    public void setHttp_connection_timeout(Integer http_connection_timeout)
    {
        this.http_connection_timeout = http_connection_timeout;
    }

    public Integer getHttp_socket_timeout()
    {
        return http_socket_timeout;
    }

    public void setHttp_socket_timeout(Integer http_socket_timeout)
    {
        this.http_socket_timeout = http_socket_timeout;
    }
}

package com.lianpay.util;

/**
 * http连接相关参数
 * @创建人： 郭锐
 *
 * @创建时间：2010-9-9 下午02:47:00
 */
public class ClientGlobals{
    public static final String client_send_type_http                           = "http";
    public static final String client_send_type_activemq                       = "activemq";
    // request
    public static final String http_request_connection_timeout_key             = "http.connection.timeout";  // 连接超时
    public static final String http_request_socket_timeout_key                 = "http.socket.timeout";      // 读取响应超时
    public static final String http_request_correlationID_key                  = "correlationID";            // 请求url
    public static final String http_request_method_get_value                   = "getHttp";                  // get请求方法
    public static final String http_request_method_post_value                  = "postHttp";                 // get请求方法
    public static final String http_request_method_postObject_value            = "postObject";               // post请求方法
    public static final String http_request_params_content_type_stream         = "application/octet-stream"; // 类型
    public static final String http_request_params_content_type_xml            = "text/html;charset=UTF-8";  // 类型
    public static final String amq_request_method_asyn                         = "asyn";                     // 异步请求
    public static final String amq_request_method_syn                          = "syn";                      // 同步请求
    public static final String amq_request_params_dataRules_key                = "dataRules";
    public static final String amq_request_params_machineid_key                = "machineid";                // 机器名
    // 成功
    public static final String res_code_success                                = "clientsend_success";
    public static final String res_msg_success                                 = "clientsend处理成功";
    // 失败
    public static final String res_code_failed                                 = "clientsend_failed";
    public static final String res_msg_failed_parse_inputparam                 = "解析输入参数io操作失败";
    public static final String res_msg_failed_parse_inputparam_close           = "解析输入参数io关闭操作失败";
    public static final String res_msg_failed_protocol_wrong                   = "协议错误";
    public static final String res_msg_failed_connection_refuse                = "连接拒绝";
    public static final String res_msg_failed_connection_timeout               = "连接超时";
    public static final String res_msg_failed_request_params_error             = "缺少必需参数!";
    public static final String res_msg_failed_request_sendtype_invalid         = "无效的发送方式!";
    public static final String res_msg_failed_request_encoding_exception       = "发送编码异常!";
    public static final String res_msg_failed_request_msgsetproperty_exception = "设置消息属性出错!";
    public static final String res_msg_failed_response_error_status            = "http响应非法的状态!";
    // 异常
    public static final String res_code_exception                              = "clientsend_exception";
    public static final String res_msg_exception_socket_timeout                = "读取响应超时";
    public static final String res_msg_exception_parse_res_illegalstate        = "解析响应内容非法状态";
    public static final String res_msg_exception_parse_res_io                  = "解析响应内容io异常";
    public static final String res_msg_exception_parse_res_classnotfound       = "解析响应找不到对应的class";
    public static final String res_msg_exception_parse_res_inputparam_close    = "解析响应io关闭操作失败";
    public static final String res_msg_exception_parse_res_consumecontent      = "消费响应内容io异常";
    public static final String res_msg_exception_parse_res_null                = "收到response为null";
    public static final String res_msg_exception_parse_res_entitynull          = "收到resEntity为null";
    public static final String res_msg_exception_parse_res_returMapnull        = "返回returMap为null";

    public static final String res_msg_exception_amq_send                      = "发送消息至broker异常";
    public static final String res_msg_exception_sleep_exception               = "循环读取缓存中消息时线程休眠异常";
    public static final String res_msg_exception_read_timeout                  = "读取消息超时异常";
}

package com.lianpay.util;

import java.io.IOException;

import org.apache.log4j.Logger;

public class ClientConn{
    private static Logger logger = Logger.getLogger(ClientConn.class);
    private ClientHttp    clientHttp;                                  // 发送http请求

    /**
     * 通讯主入口
     * @param sRecBuf 请求报文
     * @param dataRules 数据分布规则
     * @param queneName 发送队列名称
     * @return
     * @throws IOException 
     * @throws IOException
     * @throws AMQDownException 
     */
    public ClientResult clientSend(ClientParams clientParams)
    {
        ClientResult clientResult = null;
        Object obj_sendType = clientParams.getSendType();
        if (null == obj_sendType)
        {
            clientResult = new ClientResult();
            clientResult.setRes_code(ClientGlobals.res_code_exception);
            clientResult
                    .setRes_value(ClientGlobals.res_msg_failed_request_sendtype_invalid);
            return clientResult;
        }
        String sendType = (String) obj_sendType;
        if (sendType.equals(ClientGlobals.client_send_type_activemq))
        {

        } else if (sendType.equals(ClientGlobals.client_send_type_http))
        {
            return clientSendHttp(clientParams);
        }
        clientResult = new ClientResult();
        clientResult.setRes_code(ClientGlobals.res_code_exception);
        clientResult
                .setRes_value(ClientGlobals.res_msg_failed_request_sendtype_invalid);
        return clientResult;
    }

    private ClientResult clientSendHttp(ClientParams ClientParams)
    {
        ClientResult clientResult = clientHttp.sendHttp(ClientParams);
        if (null == clientResult)
        {
            clientHttp.logHttp(ClientGlobals.res_code_exception,
                    ClientGlobals.res_msg_exception_parse_res_returMapnull);
            clientResult = new ClientResult();
            clientResult.setRes_code(ClientGlobals.res_code_exception);
            clientResult
                    .setRes_value(ClientGlobals.res_msg_exception_parse_res_returMapnull);
        }
        return clientResult;
    }

    /**
     * 获取文件服务
     * @param comStr 执行命令语句
     * @throws IOException
     */
    public synchronized void fileClient(String comStr) throws IOException
    {
        try
        {
            Process process = Runtime.getRuntime().exec(comStr);
            process.waitFor();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ClientHttp getClientHttp()
    {
        return clientHttp;
    }

    public void setClientHttp(ClientHttp clientHttp)
    {
        this.clientHttp = clientHttp;
    }
}

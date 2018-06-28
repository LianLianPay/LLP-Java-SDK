package com.lianpay.util;

/**
 * 
 * @创建人： 郭锐
 *
 * @创建时间：2010-9-10 下午02:39:18
 */
public class ClientResult{
    private String res_code;  // 结果码
    private String res_msg;   // 结果消息
    private Object res_value; // 结果值

    public String getRes_code()
    {
        return res_code;
    }

    public void setRes_code(String res_code)
    {
        this.res_code = res_code;
    }

    public String getRes_msg()
    {
        return res_msg;
    }

    public void setRes_msg(String res_msg)
    {
        this.res_msg = res_msg;
    }

    public Object getRes_value()
    {
        return res_value;
    }

    public void setRes_value(Object res_value)
    {
        this.res_value = res_value;
    }
}

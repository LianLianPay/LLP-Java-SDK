package com.lianpay.pay.demo;


/**
 * 银行卡支付类
 * @author duzl
 *
 */
public class BankCardPayBean extends TranBaseBean{

    private static final long serialVersionUID = 1L;
    private String            oid_partner;          // 商户编号
    private String            busi_partner;         // 商户业务类型
    private String            no_order;             // 商户唯一订单号
    private String            dt_order;             // 商户订单时间
    private String            name_goods;           // 商品名称
    private String            info_order;           // 订单描述
    private String            money_order;          // 交易金额 单位元
    private String            notify_url;           // 服务器异步通知地址
    private String            pay_type;             // 支付方式
    private String            card_no;              // 银行卡号
    private String            bank_code;            // 银行编号
    private String            acct_name;            // 银行账号姓名
    private String            bind_mob;             // 绑定手机号
    private String            vali_date;            // 信用卡有效期
    private String            cvv2;                 // 信用卡CVV2
    private String            id_type;              // 证件类型
    private String            id_no;                // 证件号码
    private String            verify_code;          // 短信验证码
    private String            req_ip;               // 请求IP
    private String            oid_paybill;          // 支付单号
    private String            result_pay;           // 支付结果
    private String            settle_date;          // 清算日期
    private String            oid_userno;           // 用户编号
    private String            valid_order;          // 订单有效期
    private String            token;                // 授权码
    private String            times_errmsg;         // 验证码输错次数
    private String            agreementno;          // 协议编号

    // 一键支付参数
    private String            item_product;         // 商品条目
    private String            user_id;              // 商户唯一ID
    private String            no_agree;             // 银通协议编号

    // 360 API 改造参数
    private String            risk_item;            // 风控参数
    private String            api_version;          // 版本标识
    private String            sms_flag;             // 0 不发送短信 1发送短信

    private String            platform;             // 平台来源标示
    private String            user_login;
    private String            flag_card_bind;       // 银行卡绑定标识
    private String            flag_smssend;         //是否需要发送短信验证码 标识
    private String            flag_sign;            //签约标识
    
    private String            flag_sms_verify;     //短信验证标识

    public String getOid_partner()
    {
        return oid_partner;
    }

    public void setOid_partner(String oid_partner)
    {
        this.oid_partner = oid_partner;
    }

    public String getBusi_partner()
    {
        return busi_partner;
    }

    public void setBusi_partner(String busi_partner)
    {
        this.busi_partner = busi_partner;
    }

    public String getNo_order()
    {
        return no_order;
    }

    public void setNo_order(String no_order)
    {
        this.no_order = no_order;
    }

    public String getDt_order()
    {
        return dt_order;
    }

    public void setDt_order(String dt_order)
    {
        this.dt_order = dt_order;
    }

    public String getName_goods()
    {
        return name_goods;
    }

    public void setName_goods(String name_goods)
    {
        this.name_goods = name_goods;
    }

    public String getInfo_order()
    {
        return info_order;
    }

    public void setInfo_order(String info_order)
    {
        this.info_order = info_order;
    }

    public String getMoney_order()
    {
        return money_order;
    }

    public void setMoney_order(String money_order)
    {
        this.money_order = money_order;
    }

    public String getNotify_url()
    {
        return notify_url;
    }

    public void setNotify_url(String notify_url)
    {
        this.notify_url = notify_url;
    }

    public String getPay_type()
    {
        return pay_type;
    }

    public void setPay_type(String pay_type)
    {
        this.pay_type = pay_type;
    }

    public String getCard_no()
    {
        return card_no;
    }

    public void setCard_no(String card_no)
    {
        this.card_no = card_no;
    }

    public String getBank_code()
    {
        return bank_code;
    }

    public void setBank_code(String bank_code)
    {
        this.bank_code = bank_code;
    }

    public String getAcct_name()
    {
        return acct_name;
    }

    public void setAcct_name(String acct_name)
    {
        this.acct_name = acct_name;
    }

    public String getBind_mob()
    {
        return bind_mob;
    }

    public void setBind_mob(String bind_mob)
    {
        this.bind_mob = bind_mob;
    }

    public String getVali_date()
    {
        return vali_date;
    }

    public void setVali_date(String vali_date)
    {
        this.vali_date = vali_date;
    }

    public String getCvv2()
    {
        return cvv2;
    }

    public void setCvv2(String cvv2)
    {
        this.cvv2 = cvv2;
    }

    public String getId_type()
    {
        return id_type;
    }

    public void setId_type(String id_type)
    {
        this.id_type = id_type;
    }

    public String getId_no()
    {
        return id_no;
    }

    public void setId_no(String id_no)
    {
        this.id_no = id_no;
    }

    public String getVerify_code()
    {
        return verify_code;
    }

    public void setVerify_code(String verify_code)
    {
        this.verify_code = verify_code;
    }

    public String getReq_ip()
    {
        return req_ip;
    }

    public void setReq_ip(String req_ip)
    {
        this.req_ip = req_ip;
    }

    public String getOid_paybill()
    {
        return oid_paybill;
    }

    public void setOid_paybill(String oid_paybill)
    {
        this.oid_paybill = oid_paybill;
    }

    public String getResult_pay()
    {
        return result_pay;
    }

    public void setResult_pay(String result_pay)
    {
        this.result_pay = result_pay;
    }

    public String getSettle_date()
    {
        return settle_date;
    }

    public void setSettle_date(String settle_date)
    {
        this.settle_date = settle_date;
    }

    public String getOid_userno()
    {
        return oid_userno;
    }

    public void setOid_userno(String oid_userno)
    {
        this.oid_userno = oid_userno;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getTimes_errmsg()
    {
        return times_errmsg;
    }

    public void setTimes_errmsg(String times_errmsg)
    {
        this.times_errmsg = times_errmsg;
    }

    public String getValid_order()
    {
        return valid_order;
    }

    public void setValid_order(String valid_order)
    {
        this.valid_order = valid_order;
    }

    public String getAgreementno()
    {
        return agreementno;
    }

    public void setAgreementno(String agreementno)
    {
        this.agreementno = agreementno;
    }

    public String getItem_product()
    {
        return item_product;
    }

    public void setItem_product(String item_product)
    {
        this.item_product = item_product;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getNo_agree()
    {
        return no_agree;
    }

    public void setNo_agree(String no_agree)
    {
        this.no_agree = no_agree;
    }

    public String getRisk_item()
    {
        return risk_item;
    }

    public void setRisk_item(String risk_item)
    {
        this.risk_item = risk_item;
    }

    public String getApi_version()
    {
        return api_version;
    }

    public void setApi_version(String api_version)
    {
        this.api_version = api_version;
    }

    public String getSms_flag()
    {
        return sms_flag;
    }

    public void setSms_flag(String sms_flag)
    {
        this.sms_flag = sms_flag;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getUser_login()
    {
        return user_login;
    }

    public void setUser_login(String user_login)
    {
        this.user_login = user_login;
    }

    public String getFlag_card_bind()
    {
        return flag_card_bind;
    }

    public void setFlag_card_bind(String flag_card_bind)
    {
        this.flag_card_bind = flag_card_bind;
    }

    public String getFlag_smssend()
    {
        return flag_smssend;
    }

    public void setFlag_smssend(String flag_smssend)
    {
        this.flag_smssend = flag_smssend;
    }

    public String getFlag_sign()
    {
        return flag_sign;
    }

    public void setFlag_sign(String flag_sign)
    {
        this.flag_sign = flag_sign;
    }

    public String getFlag_sms_verify()
    {
        return flag_sms_verify;
    }

    public void setFlag_sms_verify(String flag_sms_verify)
    {
        this.flag_sms_verify = flag_sms_verify;
    }
    
    
    

}

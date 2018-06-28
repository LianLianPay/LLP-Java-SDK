package com.lianpay.pay.demo;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianpay.api.util.ApiUtils;
import com.lianpay.api.util.TraderRSAUtil;
import com.lianpay.util.HttpRequestSimple;
import com.lianpay.util.Md5Algorithm;
import com.lianpay.util.YTHttpHandler;

/**
 * SDK SERVER testcase
 * 
 * @author guoyx
 * @date:May 13, 2013 5:09:31 PM
 * @version :1.0
 * 
 */
public class ApiQueryTest{            
    private static String       TRADER_MD5_KEY = "201408071000001543test_20140812";
    private final static String SERVER         = "https://yintong.com.cn/queryapi/orderquery.htm";
    private final static String prikeyvalue       = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";


    public static void main(String args[])
    {

        testQuery();
    }
    
    public static void testQuery()
    {
        OrderQuery bankCardPayBean = new OrderQuery();
        bankCardPayBean.setOid_partner("201408071000001543");
        bankCardPayBean.setSign_type("MD5");
        bankCardPayBean.setNo_order("20140501094312");
        bankCardPayBean.setQuery_version("1.1");
        bankCardPayBean.setSign(genSign(JSON.parseObject(JSON
                .toJSONString(bankCardPayBean))));
        String reqJson = JSON.toJSONString(bankCardPayBean);
        // System.out.println("请求报文为:" + reqJson);
        // HttpSender.setUrl(SERVER+"bankcardunbind");
        HttpRequestSimple httpclent = new HttpRequestSimple();
        String resJson = YTHttpHandler.getInstance().doRequestPostString(reqJson, SERVER);
        // String resJson = HttpSender.send(reqJson);
        // BankCardPayBean res = JSON.parseObject(resJson, BankCardPayBean.class);
        System.out.println("结果报文为:" + resJson);

    }
    
   
   




    private static String genSign(JSONObject reqObj)
    {
        String sign = reqObj.getString("sign");
        String sign_type=reqObj.getString("sign_type");
        // // 生成待签名串
        String sign_src = genSignData(reqObj);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]待签名原串"
                + sign_src);
        System.out.println("商户[" + reqObj.getString("oid_partner") + "]签名串"
                + sign);

        if("MD5".equals(sign_type)){
            sign_src += "&key=" + TRADER_MD5_KEY;
            return signMD5(sign_src);
        }
        if("RSA".equals(sign_type)){
            return getSignRSA(sign_src);
        }
        return null;
    }

    private static String signMD5(String signSrc)
    {

        try
        {
            return Md5Algorithm.getInstance().md5Digest(
                    signSrc.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * RSA签名验证
     * 
     * @param reqObj
     * @return
     */
    public static String getSignRSA(String sign_src)
    {
        return TraderRSAUtil.sign(prikeyvalue, sign_src);

    }


    /**
     * 生成待签名串
     * 
     * @param paramMap
     * @return
     */
    public static String genSignData(JSONObject jsonObject)
    {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String) keys.get(i);
            // sign 和ip_client 不参与签名
            if ("sign".equals(key))
            {
                continue;
            }
            String value = (String) jsonObject.getString(key);
            // 空串不参与签名
            if (null==value)
            {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&"))
        {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }

}

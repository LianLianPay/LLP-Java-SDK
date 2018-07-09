# LLP-Java-SDK

欢迎来到连连， 本仓库中包含使用```Java```请求连连的服务器端API时的示例工程及使用说明。

其中，SDK目录下的```yintong_api_util.jar```即为连连Java的服务器端SDK。

## 主要内容

* [前置要求](#前置要求)

* [使用说明](#使用说明)

* [签名说明](#签名说明)

* [延伸阅读](#延伸阅读)


## 前置要求

Java SDK的版本在1.6或1.6以上

## 使用说明

您的服务器可通过HTTP协议直接请求连连的服务器端API， 要求如下:

* HTTP请求的媒体类型应设置为:

```text
Content-type: application/json;charset=utf-8
```

* 请求方法应为```POST```。

* 需使用[HTTPS](https://baike.baidu.com/item/https/285356?fr=aladdin)协议。

* 在您与连连的交互中， 所有的信息须进行签名处理。

## 签名说明

首先生成签名原串， 如示例工程中的```genSignData()```方法:

```java
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
```


加签时，使用SDK的```sign```方法进行加签, 其中```prikeyvalue```即为您的私钥：

```java
    /**
     * RSA加签
     * 
     * @param reqObj
     * @return
     */
    public static String getSignRSA(String sign_src)
    {
        return TraderRSAUtil.sign(prikeyvalue, sign_src);
    }
```

验签时， 使用SDK的```checkSign()```方法，其中```rsa_public```为连连向您提供的公钥：

```java
    /**
     * RSA签名验证
     *
     * @param reqObj
     * @return
     */
    private boolean checkSignRSA(JSONObject reqObj, String rsa_public)
    {

        logger.log(Priority.INFO, "进入商户[" + reqObj.getString("oid_partner")
                + "]RSA签名验证: ");
        if (reqObj == null)
        {
            return false;
        }
        String sign = reqObj.getString("sign");
        // 生成待签名串
        String sign_src = getInstance().generateSignSrc(reqObj);
        logger.log(Priority.INFO,"商户[" + reqObj.getString("oid_partner") + "]待签名原串: "
                + sign_src);
        logger.log(Priority.INFO,"商户[" + reqObj.getString("oid_partner") + "]签名串: "
                + sign);
        try
        {
            if (TraderRSAUtil.checksign(rsa_public, sign_src, sign))
            {
                logger.log(Priority.INFO,"商户[" + reqObj.getString("oid_partner")
                        + "]RSA签名验证通过.");
                return true;
            } else
            {
                logger.log(Priority.INFO,"商户[" + reqObj.getString("oid_partner")
                        + "]RSA签名验证未通过.");
                return false;
            }
        } catch (Exception e)
        {
            logger.log(Priority.INFO,"商户[" + reqObj.getString("oid_partner")
                    + "]RSA签名验证异常, " + e.getMessage());
            return false;
        }
    }

```

## 延伸阅读

[连连开放平台 - API文档 - 新手指南](https://open.lianlianpay-inc.com/apis/get-started)

[连连开放平台 - 签名机制](https://open.lianlianpay-inc.com/docs/development/signature-overview)
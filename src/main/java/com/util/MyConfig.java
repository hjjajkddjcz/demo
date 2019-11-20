package com.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author : Jixiaohu
 * @Date : 2019-04-08.
 * @Time : 13:19.
 * @Description :
 */
public class MyConfig extends WXPayConfig {

    private byte[] certData;

    private String appid;

    private String mchId;

    private String subMchId;

    private String subAppid;

    private String key;

    private String certPath;

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public void setSubAppid(String subAppid) {
        this.subAppid = subAppid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MyConfig(byte[] certData) {
        this.certData = certData;
    }

    public MyConfig() {
    }

    /**
     * 获取 App ID
     *
     * @return App ID
     */
    @Override
    String getAppID() {
        return this.appid;
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    @Override
    String getMchID() {
        return this.mchId;
    }

    @Override
    String getSubAppID() {
        return this.subAppid;
    }

    @Override
    String getSubMchID() {
        return this.subMchId;
    }

    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    @Override
    String getKey() {
        return this.key;
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    @Override
    IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}

package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: demo
 * @description:
 * @author: wu han
 * @create: 2019-10-12 10:14
 */
@ApiModel
public class WxFaceAuthInfo {
    @ApiModelProperty(value = "门店编号")
    private String store_id;

    @ApiModelProperty(value = "门店名称")
    private String store_name;

    @ApiModelProperty(value = "终端设备编号")
    private String device_id;

    @ApiModelProperty(value = "附加字段")
    private String attach;

    @ApiModelProperty(value = "初始化数据")
    private String rawdata;

    @ApiModelProperty(value = "商户绑定的公众号/小程序的appid")
    private String appid;

    @ApiModelProperty(value = "商户号")
    private String mch_id;

    @ApiModelProperty(value = "子商户绑定的公众号/小程序appid(服务商模式)")
    private String sub_appid;

    @ApiModelProperty(value = "子商户号(服务商模式)")
    private String sub_mch_id;

    @ApiModelProperty(value = "当前时间10位unix时间戳")
    private String now;

    @ApiModelProperty(value = "版本号")
    private String version = "1";

    @ApiModelProperty(value = "签名类型")
    private String sign_type = "MD5";

    @ApiModelProperty(value = "随机字符串")
    private String nonce_str;


    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getRawdata() {
        return rawdata;
    }

    public void setRawdata(String rawdata) {
        this.rawdata = rawdata;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }
}

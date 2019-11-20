package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: demo
 * @description:
 * @author: wu han
 * @create: 2019-10-12 11:36
 */
@ApiModel
public class WxFaceQueryOrder {

    @ApiModelProperty(value = "公众账号id")
    private String appid;

    @ApiModelProperty(value = "商户号")
    private String mch_id;

    @ApiModelProperty(value = "服务商模式下必填，微信支付分配的特约商户号")
    private String sub_mch_id;

    @ApiModelProperty(value = "微信订单号")
    private String transaction_id;

    @ApiModelProperty(value = "商户订单号")
    private String out_trade_no;

    @ApiModelProperty(value = "随机字符串")
    private String nonce_str;

    @ApiModelProperty(value = "签名类型")
    private String sign_type = "MD5";


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

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public WxFaceQueryOrder(WxFaceSqlModel sqlModel,String transaction_id) {
        this.appid = sqlModel.getAppid();
        this.mch_id = sqlModel.getMch_id();
        this.sub_mch_id = sqlModel.getSub_mch_id();
        this.transaction_id = transaction_id;
        this.out_trade_no = sqlModel.getOut_trade_no();
        this.nonce_str = sqlModel.getNonce_str();

    }

    public WxFaceQueryOrder() {
    }
}

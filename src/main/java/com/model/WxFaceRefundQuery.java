package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: demo
 * @description:
 * @author: wu han
 * @create: 2019-10-14 17:05
 */
@ApiModel
public class WxFaceRefundQuery {

    @ApiModelProperty(value = "公众账号ID")
    private String appid;

    @ApiModelProperty(value = "商户号")
    private String mch_id;

    @ApiModelProperty(value = "特约商户号")
    private String sub_mch_id;

    @ApiModelProperty(value = "随机字符串")
    private String nonce_str;

    @ApiModelProperty(value = "微信订单号")
    private String transaction_id;

    @ApiModelProperty(value = "商户订单号")
    private String out_trade_no;

    @ApiModelProperty(value = "商户退款单号")
    private String out_refund_no;

    @ApiModelProperty(value = "微信退款单号")
    private String refund_id;

    @ApiModelProperty(value = "偏移量")
    private Integer offset;

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

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
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

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}

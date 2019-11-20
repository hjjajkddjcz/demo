package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: demo
 * @description:
 * @author: wu han
 * @create: 2019-10-15 09:28
 */
@ApiModel
public class WxFaceDownLoadBill {

    @ApiModelProperty(value = "微信分配的公众账号ID")
    private String appid;

    @ApiModelProperty(value = "商户号")
    private String mch_id;

    @ApiModelProperty(value = "特约商户号，服务商模式下必填")
    private String sub_mch_id;

    @ApiModelProperty(value = "随机字符串")
    private String nonce_str;

    @ApiModelProperty(value = "对账单日期,格式yyyymmss")
    private String bill_date;

    @ApiModelProperty(value = "账单类型,ALL，返回当日所有订单信息，默认值\n" +
            "SUCCESS，返回当日成功支付的订单\n" +
            "REFUND，返回当日退款订单\n" +
            "RECHARGE_REFUND，返回当日充值退款订单（相比其他对账单多一栏“返还手续费”）")
    private String bill_type;

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

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }
}

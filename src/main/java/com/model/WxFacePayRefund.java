package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: demo
 * @description: 申请退款用
 * @author: wu han
 * @create: 2019-10-11 14:18
 */
@ApiModel
public class WxFacePayRefund extends WxFacePayModel{
    @ApiModelProperty(value = "微信订单号")
    private String transaction_id;

    @ApiModelProperty(value = "商户退款单号")
    private String out_refund_no;

    @ApiModelProperty(value = "退款金额")
    private String refund_fee;

    @ApiModelProperty(value = "退款原因")
    private String refund_desc;

    @ApiModelProperty(value = "退款资金来源")
    private String refund_account;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public void setRefund_desc(String refund_desc) {
        this.refund_desc = refund_desc;
    }

    public String getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(String refund_account) {
        this.refund_account = refund_account;
    }
}

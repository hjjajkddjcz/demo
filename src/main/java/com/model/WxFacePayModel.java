package com.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: demo
 * @description:
 * @author: wu han
 * @create: 2019-10-11 10:23
 */
@ApiModel
public class WxFacePayModel {
    @ApiModelProperty(value = "公众账号ID")
    private String appid;

    @ApiModelProperty(value = "特约商户公众账号id")
    private String sub_appid;

    @ApiModelProperty(value = "商户号")
    private String mch_id;

    @ApiModelProperty(value = "服务商模式下必填，微信支付分配的特约商户号")
    private String sub_mch_id;

    @ApiModelProperty(value = "终端设备号")
    private String device_info;

    @ApiModelProperty(value = "随机字符串")
    private String nonce_str;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "商品描述")
    private String body;

    @ApiModelProperty(value = "商品详情")
    private String detail;

    @ApiModelProperty(value = "附加数据")
    private String attach;

    @ApiModelProperty(value = "商品订单号")
    private String out_trade_no;

    @ApiModelProperty(value = "总金额")
    private String total_fee;

    @ApiModelProperty(value = "货币类型")
    private String fee_type;

    @ApiModelProperty(value = "终端IP")
    private String spbill_create_ip;

    @ApiModelProperty(value = "商品标记")
    private String goods_tag;

    @ApiModelProperty(value = "用户标识")
    private String openid;

    @ApiModelProperty(value = "人脸凭证")
    private String face_code;

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

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getFace_code() {
        return face_code;
    }

    public void setFace_code(String face_code) {
        this.face_code = face_code;
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

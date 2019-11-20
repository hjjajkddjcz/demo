package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.model.*;
import com.service.WxFacePayService;
import com.util.HttpClientUtil;
import com.util.WXPayConstants;
import com.util.WXPayUtil;
import com.util.WXPayXmlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: demo
 * @description:
 * @author: wu han
 * @create: 2019-10-11 10:11
 */
@RestController
@Api(tags = "微信刷脸付")
@RequestMapping("/home")
public class WxFacePayController {
    private String WX_FACE_PAY_URL = "https://api.mch.weixin.qq.com/pay/facepay";
    private String WX_FACE_PAY_AUTHINFO_URL = "https://payapp.weixin.qq.com/face/get_wxpayface_authinfo";
    private String FACE_PAY_QUERY_URL = "https://api.mch.weixin.qq.com/pay/facepayquery";
    private String FACE_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    private String FACE_QUERY_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    private String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    private String FACE_PAY_REVERSE_URL = "https://api.mch.weixin.qq.com/secapi/pay/facepayreverse";
    private String FACE_SUCCESS  = "SUCCESS";
    private String TRADE_PAYING = "TRADE_PAYING";
    private String SYSTEM_ERROR = "SYSTEMERROR";
    private String ERROR_CODE = "err_code";
    private String TRADE_STATE = "trade_state";
    private String RESULT_CODE = "result_code";
    private String MERCHANT_ID = "mch_id";
    private String TRANSACTION_ID = "transaction_id";
    private String SQLMODEL_SUCCESS = "1";
    private String SQLMODEL_FAIL = "2";
    private String SQLMODEL_PAYING = "3";
    private String TRADE_STATUS_PAY = "1";
    private static Logger log = LoggerFactory.getLogger(WxFacePayController.class);

    @Autowired
    WxFacePayService wxFacePayService;

    @ApiOperation(value = "获取调用凭证", notes = "get_wxpayface_authinfo")
    @PostMapping("/getauthinfo")
    public JSONObject getWxFaceAuthInfo(WxFaceAuthInfo info,String apikey){
        log.info("进入微信刷脸付获取调用凭证方法");
        String now = String.valueOf(WXPayUtil.getCurrentTimestamp());
        info.setNow(now);
        Map<String,String> map = BeanMap.create(info);
        Map<String,String> aftermap = new HashMap<>(16);
        map.forEach((key,value) -> {
            if (!"".equals(value)&& value != null ) {
                aftermap.put(key, value);
            }
        });
        try {
            String signedXml = WXPayUtil.generateSignedXml(aftermap ,apikey, WXPayConstants.SignType.MD5);
            log.info("转xml结束，结果：" + signedXml);
            String result = HttpClientUtil.requestOnce(WX_FACE_PAY_AUTHINFO_URL,signedXml,aftermap.get(MERCHANT_ID));
            log.info("Post结束，结果：" + result);
            Map<String,String> resultmap = WXPayUtil.xmlToMap(result);
            log.info("转map结束");
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(resultmap);
            return jsonObject;
        } catch (Exception e) {
            log.info("获取调用凭证出现异常:"+e.getStackTrace());
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "微信刷脸支付", notes = "支付方法")
    @PostMapping("/wxfacepayunfiledorder")
    public JSONObject wxFacePayDemo(WxFacePayModel paymodel, String apikey) {
        log.info("进入微信刷脸付支付下单方法");
        WxFaceSqlModel sqlModel = new WxFaceSqlModel(paymodel,TRADE_STATUS_PAY);
        sqlModel.setStatus(SQLMODEL_PAYING);
        Map<String, String> map = BeanMap.create(paymodel);
        Map<String, String> aftermap = new HashMap<>(16);
        map.forEach((key, value) -> {
            if (!"".equals(value)&& value != null) {
                aftermap.put(key, value);
            }
        });
        try {
                //开始下单操作
                String signedXml = WXPayUtil.generateSignedXml(aftermap, apikey, WXPayConstants.SignType.MD5);
                log.info("转xml结束，结果：" + signedXml);
                String result = HttpClientUtil.requestOnce(WX_FACE_PAY_URL,signedXml,aftermap.get(MERCHANT_ID));
                log.info("Post结束，结果：" + result);
                map = WXPayUtil.xmlToMap(result);
                log.info("转map结束");
                if (FACE_SUCCESS.equals(map.get(RESULT_CODE))) {
                    sqlModel.setStatus(SQLMODEL_SUCCESS);
                }else {
                    sqlModel.setStatus(SQLMODEL_FAIL);
                    if (SYSTEM_ERROR.equals(map.get(ERROR_CODE))){
                        WxFaceQueryOrder queryOrder = new WxFaceQueryOrder(sqlModel,map.get(TRANSACTION_ID));
                        Map querymap = new HashMap(16);
                        //查询订单状态，若为支付中或异常则继续查询，限时20s
                        Long starttime = System.currentTimeMillis();
                        Long endtime = 0L;
                        Long usedtime = 0L;
                        do {
                            JSONObject jsonObject = facePayQuery(queryOrder,apikey);
                            querymap = (Map) JSONObject.parse(jsonObject.toJSONString());
                            endtime = System.currentTimeMillis();
                            usedtime = (endtime-starttime)/1000;
                        }while(TRADE_PAYING.equals(map.get(TRADE_STATE)) && usedtime <= 20L);
                        if (usedtime > 20L){
                            log.info("轮询超时！");
                        }
                        if (!"".equals(querymap.get(TRADE_STATE)) && FACE_SUCCESS.equals(querymap.get(TRADE_STATE))&&querymap.get(TRADE_STATE)!=null){
                            //交易成功
                            sqlModel.setStatus(SQLMODEL_SUCCESS);
                        }else {
                            sqlModel.setStatus(SQLMODEL_FAIL);
                            //交易失败或异常，开始撤销订单操作
                            Map reversemap = new HashMap(16);
                            do {
                                reversemap = facereverse(paymodel,apikey);
                            }while (!FACE_SUCCESS.equals(reversemap.get(RESULT_CODE)));

                        }
                    }
                }
            //存入数据库
            wxFacePayService.insertWxFacePayDemo(sqlModel);
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(map);
            return jsonObject;
        } catch (Exception e) {
            log.info("请求支付出现异常:"+e.getStackTrace());
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "微信刷脸申请退款", notes = "wxfacerefund")
    @PostMapping("/facerefund")
    public JSONObject facerefund(WxFacePayRefund payRefund, String apikey){
        log.info("进入微信刷脸付申请退款方法");
        Map<String,String> map = BeanMap.create(payRefund);
        Map<String,String> aftermap = new HashMap<>(16);
        map.forEach((key,value) -> {
            if (!"".equals(value)&& value != null) {
                aftermap.put(key, value);
            }
        });
        try {
            String signedXml = WXPayUtil.generateSignedXml(aftermap,apikey, WXPayConstants.SignType.MD5);
            log.info("转xml结束，结果：" + signedXml);
            String result = HttpClientUtil.requestOnce(FACE_REFUND_URL,signedXml,payRefund.getMch_id(),1);
            log.info("Post结束，结果：" + result);
            map = WXPayUtil.xmlToMap(result);
            log.info("转map结束");
            if(FACE_SUCCESS.equals(map.get(RESULT_CODE))){
                wxFacePayService.updateWxFacePayDemoRefundStatus(payRefund);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(map);
            return jsonObject;
        } catch (Exception e) {
            log.info("退款申请出现异常:"+e.getStackTrace());
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "微信刷脸订单查询", notes = "queryorder")
    @PostMapping("/facepayquery")
    public JSONObject facePayQuery(WxFaceQueryOrder queryOrder,String apikey){
        log.info("进入微信刷脸付订单查询方法");
        Map<String,String> map = BeanMap.create(queryOrder);
        Map<String,String> aftermap = new HashMap<>(16);
        map.forEach((key,value) -> {
            if (!"".equals(value)&& value != null) {
                aftermap.put(key, value);
            }
        });
        try {
            String signedXml = WXPayUtil.generateSignedXml(aftermap,apikey, WXPayConstants.SignType.MD5);
            log.info("转xml结束，结果：" + signedXml);
            String result = HttpClientUtil.requestOnce(FACE_PAY_QUERY_URL,signedXml,aftermap.get(MERCHANT_ID));
            log.info("Post结束，结果：" + result);
            map = WXPayUtil.xmlToMap(result);
            log.info("转map结束");
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(map);
            return jsonObject;
        } catch (Exception e) {
            log.info("查询订单出现异常:"+e.getStackTrace());
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "微信退款订单查询", notes = "refundQuery")
    @PostMapping("/facerefundQuery")
    public JSONObject faceRefundQuery(WxFaceRefundQuery refundQuery,String apikey){
        log.info("进入微信刷脸付退款订单查询方法");
        Map<String,String> map = BeanMap.create(refundQuery);
        Map<String,String> aftermap = new HashMap<>(16);
        map.forEach((key,value) -> {
            if (!"".equals(value)&& value != null) {
                aftermap.put(key, value);
            }
        });
        try {
            String signedXml = WXPayUtil.generateSignedXml(aftermap,apikey, WXPayConstants.SignType.MD5);
            log.info("转xml结束，结果：" + signedXml);
            String result = HttpClientUtil.requestOnce(FACE_QUERY_REFUND_URL,signedXml,aftermap.get(MERCHANT_ID));
            log.info("Post结束，结果：" + result);
            map = WXPayUtil.xmlToMap(result);
            log.info("转map结束");
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(map);
            return jsonObject;
        } catch (Exception e) {
            log.info("查询订单出现异常:"+e.getStackTrace());
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "刷脸付对账单下载", notes = "downloadbill")
    @PostMapping("/facedownloadbill")
    public String faceDownloadBill(WxFaceDownLoadBill downLoadBill,String apikey){
        log.info("进入微信刷脸付对账单下载方法");
        Map<String,String> map = BeanMap.create(downLoadBill);
        Map<String,String> aftermap = new HashMap<>(16);
        map.forEach((key,value) -> {
            if (!"".equals(value)&& value != null) {
                aftermap.put(key, value);
            }
        });
        try {
            String signedXml = WXPayUtil.generateSignedXml(aftermap,apikey, WXPayConstants.SignType.MD5);
            log.info("转xml结束，结果：" + signedXml);
            String result = HttpClientUtil.requestOnce(DOWNLOAD_BILL_URL,signedXml,aftermap.get(MERCHANT_ID));
            log.info("Post结束，结果：" + result);
            return result;
        } catch (Exception e) {
            log.info("订单下载出现异常:"+e.getStackTrace());
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value = "微信刷脸撤销订单", notes = "wxfacereverse")
    @PostMapping("/facereverse")
    public JSONObject facereverse(WxFacePayModel payModel, String apikey){
        log.info("进入微信刷脸付撤销订单方法");
        Map<String,String> map = BeanMap.create(payModel);
        Map<String,String> aftermap = new HashMap<>(16);
        map.forEach((key,value) -> {
            if (!"".equals(value)&& value != null) {
                aftermap.put(key, value);
            }
        });
        try {
            while (!FACE_SUCCESS.equals(map.get(RESULT_CODE))) {
                String signedXml = WXPayUtil.generateSignedXml(aftermap,apikey, WXPayConstants.SignType.MD5);
                log.info("转xml结束，结果：" + signedXml);
                String result = HttpClientUtil.requestOnce(FACE_PAY_REVERSE_URL,signedXml,payModel.getMch_id(),1);
                log.info("Post结束，结果：" + result);
                map = WXPayUtil.xmlToMap(result);
                log.info("转map结束");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(map);
            return jsonObject;
        } catch (Exception e) {
            log.info("撤销订单出现异常:"+e.getStackTrace());
            e.printStackTrace();
            return null;
        }
    }
}


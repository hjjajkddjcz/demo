package com.service;

import com.model.WxFacePayRefund;
import com.model.WxFaceSqlModel;

public interface WxFacePayService {

    int insertWxFacePayDemo(WxFaceSqlModel sqlModel);

    int updateWxFacePayDemoRefundStatus(WxFacePayRefund payRefund);
}

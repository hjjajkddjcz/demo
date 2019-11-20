package com.service.impl;

import com.mapper.WxFacePayDemoMapper;
import com.model.WxFacePayRefund;
import com.model.WxFaceSqlModel;
import com.service.WxFacePayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: demo
 * @description:
 * @author: wu han
 * @create: 2019-10-14 15:05
 */
@Service("WxFacePayService")
public class WxFacePayServiceImpl implements WxFacePayService {

    @Resource
    private WxFacePayDemoMapper wxFacePayDemoMapper;


    @Override
    public int insertWxFacePayDemo(WxFaceSqlModel sqlModel) {
        return wxFacePayDemoMapper.insertWxFacePayDemo(sqlModel);
    }

    @Override
    public int updateWxFacePayDemoRefundStatus(WxFacePayRefund payRefund) {
        return wxFacePayDemoMapper.updateWxFacePayDemoRefundStatus(payRefund);
    }
}

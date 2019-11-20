package com.mapper;

import com.mapper.sqlprovider.WxFacePayDemoSqlProvider;
import com.model.WxFacePayRefund;
import com.model.WxFaceSqlModel;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface WxFacePayDemoMapper {

    @InsertProvider(type = WxFacePayDemoSqlProvider.class,method = "insertWxFacePayDemo" )
    int insertWxFacePayDemo(WxFaceSqlModel sqlModel);

    @UpdateProvider(type = WxFacePayDemoSqlProvider.class,method = "updateWxFacePayDemoRefundStatus" )
    int updateWxFacePayDemoRefundStatus(WxFacePayRefund payRefundmodel);
}

package com.mapper.sqlprovider;

import com.model.WxFacePayModel;
import com.model.WxFacePayRefund;
import com.model.WxFaceSqlModel;
import com.mysql.cj.util.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @program: demo
 * @description:
 * @author: wu han
 * @create: 2019-10-14 14:07
 */
public class WxFacePayDemoSqlProvider {

    private static String TABLE_NAME = "wx_pay_face_demo";

    public String insertWxFacePayDemo(WxFaceSqlModel sqlModel){
        SQL sql = new SQL();
        sql.INSERT_INTO(TABLE_NAME);

        if (sqlModel.getAppid() != null) {
            sql.VALUES("appid", "#{appid,jdbcType=VARCHAR}");
        }
        if (sqlModel.getSub_appid() != null) {
            sql.VALUES("sub_appid", "#{sub_appid,jdbcType=VARCHAR}");
        }
        if (sqlModel.getMch_id() != null) {
            sql.VALUES("merchant_id", "#{mch_id,jdbcType=VARCHAR}");
        }
        if (sqlModel.getSub_mch_id() != null) {
            sql.VALUES("sub_mch_id", "#{sub_mch_id,jdbcType=VARCHAR}");
        }
        if (sqlModel.getDevice_info() != null) {
            sql.VALUES("device_info", "#{device_info,jdbcType=VARCHAR}");
        }
        if (sqlModel.getOut_trade_no() != null) {
            sql.VALUES("out_trade_no", "#{out_trade_no,jdbcType=VARCHAR}");
        }
        if (sqlModel.getTotal_fee() != null) {
            sql.VALUES("total_fee", "#{total_fee,jdbcType=BIGINT}");
        }
        if (sqlModel.getSpbill_create_ip() != null) {
            sql.VALUES("spbill_create_ip", "#{spbill_create_ip,jdbcType=VARCHAR}");
        }
        if (sqlModel.getOpenid() != null) {
            sql.VALUES("open_id", "#{openid,jdbcType=VARCHAR}");
        }
        if (sqlModel.getFace_code() != null) {
            sql.VALUES("face_code", "#{face_code,jdbcType=VARCHAR}");
        }
        if (sqlModel.getTrade_status() != null) {
            sql.VALUES("trade_status", "#{trade_status,jdbcType=BIGINT}");
        }
        if (sqlModel.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=BIGINT}");
        }
        if (sqlModel.getDetail() != null) {
            sql.VALUES("detail", "#{detail,jdbcType=VARCHAR}");
        }
        if (sqlModel.getCreate_time() != null) {
            sql.VALUES("create_time", "#{create_time,jdbcType=TIMESTAMP}");
        }
        return sql.toString();

    }

    public String updateWxFacePayDemoRefundStatus(WxFacePayRefund payRefundmModel){
        SQL sql = new SQL();
        sql.UPDATE(TABLE_NAME);

        sql.SET("status = 4");

        sql.WHERE("out_trade_no = #{out_trade_no,jdbcType = VARCHAR} and status = 1");

        return sql.toString();
    }
}

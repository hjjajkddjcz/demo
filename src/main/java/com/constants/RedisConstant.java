package com.constants;

public class RedisConstant {

    //用户登入
    public static final String PAY_PC_USER = "pay:pc:user:";

    //扩展字段集合
    public static final String PAY_PC_EX_RULE = "pay:pc:ex_rule";

    //支付方式字典，支付方式code：支付方式对象
    public static final String PAY_PC_PT = "pay:pc:pt";

    //商户渠道配置 支付方式code + 渠道账号 ：渠道信息
    public static final String PAY_PC_MCTC = "pay:pc:mctc";

    // 商户数据 商户编号：商户对象
    public static final String PAY_PC_MER = "pay:pc:mer";

    // 商户渠道ID:渠道信息对象
    public static final String PAY_PC_MCL = "pay:pc:mcl";

    // 商户渠道配置 渠道账号 ：渠道信息
    public static final String PAY_PC_CTC = "pay:pc:ctc";

    // 商户编号：业务类型
    public static final String PAY_PC_MNB = "pay:pc:mnb";

    // 商户编号：商户签约对象
    public static final String PAY_PC_MCT = "pay:pc:mct";

    //风控Redisson分布式锁
    public static final String PAY_PC_RISKR = "pay:pc:riskr:";

    //微信分账Redisson分布式锁
    public static final String PAY_PC_WXSUB = "pay:pc:wxsub:";

    //微信分账过期订单通知Redisson分布式锁
    public static final String PAY_PC_WXSUB_EXPIRE = "pay:pc:wxe:";

    //账单下载锁
    public static final String PAY_PC_REC_DW = "pay:pc:rec_dw:";

    //对账锁
    public static final String PAY_PC_REC_CK = "pay:pc:rec_ck:";

    //微信容灾当前域名
    public static final String PAY_PC_WX_DOMAIN = "pay:pc:wx_domain";

    //微信容灾请求统计
    public static final String PAY_PC_WX_STAT = "pay:pc:wx_stat";

    //收银台信息
    public static final String PAY_PC_CASHIER_INFO = "pay:pc:ci:";

    //pc收银台返回信息
    public static final String GATEWAY_QRCODE_RESPONSE = "pay:pc:pccode:";

    //订单列表
    public static final String GATEWAY_ORDER_LIST = "pay:pc:ol:";

    //订单详情
    public static final String GATEWAY_UNIDEFIED = "pay:pc:order:";

    //订单状态
    public static final String GATEWAY_TRADE_STATUS = "pay:pc:stat:";

    //生成订单号
    public static final String PAY_PC_OR = "pay:pc:or:";

    //智康下单结果
    public static final String ZHI_CALL_PAY = "pay:pc:zhicall:";

}

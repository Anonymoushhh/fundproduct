package com.sdu.fund.core.constants;

/**
 * @program: fundproduct
 * @description: 基金数值操作中用到的key
 * @author: anonymous
 * @create: 2020/1/24 18:29
 **/
public class FundDataKey {

    /* 基金类型*/
    public static final String FUND_TYPE = "fundType";

    /* 申购状态*/
    public static final String PURCHASE_STATUS = "purchaseStatus";

    /* 赎回状态*/
    public static final String REDEEM_STATUS = "redeemStatus";

    /* 最小申购份额*/
    public static final String MIN_PURCHASE_AMOUNT = "minPurchaseAmount";

    /* 日累计申购限额*/
    public static final String PURCHASE_LIMIT_ONT_DAY = "purchaseLimitOneDay";

    /* 首次购买最小金额*/
    public static final String FIRST_BUY_AMOUNT = "firstBuyAmount";

    /* 追加购买最小份额*/
    public static final String SUPPLEMENT_AMOUNT = "supplementAmount";

    /* 持仓限额*/
    public static final String POSITIONS_LIMIT = "positionsLimit";

    /* 最小赎回份额*/
    public static final String MIN_REDEEM_SHARE = "minRedeemShare";

    /* 部分赎回最低保留份额*/
    public static final String MIN_REMAINING_SHARE = "minRemainingShare";

    /* 买入确认日*/
    public static final String BUY_COMFIRM_DAY = "buyConfirmDay";

    /* 卖出确认日*/
    public static final String REDEEM_COMFIRM_DAY = "redeemConfirmDay";

    /* 管理费*/
    public static final String MANAGER_RATE = "managerRate";

    /* 托管费*/
    public static final String TRUSTEE_RATE = "trusteeRate";

    /* 销服费*/
    public static final String SERVICE_RATE = "serviceRate";

    /*
      解析购买信息html中的key
    */
    /* 交易状态*/
    public static final String TRADE_STATUS = "tradeStatus";

    /* 申购与赎回金额*/
    public static final String PURCHASE_AND_REDEEM_AMOUNT = "purchaseAndRedeemAmount";

    /* 交易确认日*/
    public static final String TRADE_CONFIRM_DAY = "tradeConfirmDay";

    /* 运作费用*/
    public static final String OPERATION_COST = "operationCost";

    /* 认购费率（前端）*/
    public static final String SUBSCRIBE_RATE = "subscribeRate";

    /* 申购费率（前端）*/
    public static final String PURCHASE_RATE = "purchaseRate";

    /* 赎回费率*/
    public static final String REDEEM_RATE = "redeemRate";

    /*
      传递数据的key
    */
    /* 基金数值列表数据*/
    public static final String FUND_DATA_LIST_DATA = "fundDataListData";

    /* 基金数值html数据*/
    public static final String FUND_DATA_HTML_DATA = "fundDataHtmlData";

}

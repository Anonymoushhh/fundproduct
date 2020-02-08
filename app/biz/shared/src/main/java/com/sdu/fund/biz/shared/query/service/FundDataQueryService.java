package com.sdu.fund.biz.shared.query.service;


import com.sdu.fund.biz.shared.query.vo.*;


/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/5 20:57
 **/
public interface FundDataQueryService {

    public NetValuesVO queryNetValues(String fundCode);

    public TradeStatusVO queryTradeStatus(String fundCode);

    public TradeLimitVO queryTradeLimit(String fundCode);

    public PurchaseStepsVO queryPurchaseSteps(String fundCode);

    public RedeemStepsVO queryRedeemSteps(String fundCode);

    public RatesVO queryRates(String fundCode);


}

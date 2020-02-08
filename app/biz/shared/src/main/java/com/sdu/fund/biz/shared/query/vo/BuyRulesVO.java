package com.sdu.fund.biz.shared.query.vo;


/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 16:32
 **/
public class BuyRulesVO {

    private TradeStatusVO tradeStatusVO;

    private TradeLimitVO tradeLimitVO;

    private PurchaseStepsVO purchaseStepsVO;

    private RedeemStepsVO redeemStepsVO;

    private RatesVO ratesVO;

    public TradeStatusVO getTradeStatusVO() {
        return tradeStatusVO;
    }

    public void setTradeStatusVO(TradeStatusVO tradeStatusVO) {
        this.tradeStatusVO = tradeStatusVO;
    }

    public TradeLimitVO getTradeLimitVO() {
        return tradeLimitVO;
    }

    public void setTradeLimitVO(TradeLimitVO tradeLimitVO) {
        this.tradeLimitVO = tradeLimitVO;
    }

    public PurchaseStepsVO getPurchaseStepsVO() {
        return purchaseStepsVO;
    }

    public void setPurchaseStepsVO(PurchaseStepsVO purchaseStepsVO) {
        this.purchaseStepsVO = purchaseStepsVO;
    }

    public RedeemStepsVO getRedeemStepsVO() {
        return redeemStepsVO;
    }

    public void setRedeemStepsVO(RedeemStepsVO redeemStepsVO) {
        this.redeemStepsVO = redeemStepsVO;
    }

    public RatesVO getRatesVO() {
        return ratesVO;
    }

    public void setRatesVO(RatesVO ratesVO) {
        this.ratesVO = ratesVO;
    }
}

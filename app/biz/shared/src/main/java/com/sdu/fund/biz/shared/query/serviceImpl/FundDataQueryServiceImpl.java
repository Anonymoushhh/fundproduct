package com.sdu.fund.biz.shared.query.serviceImpl;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.biz.shared.query.service.FundDataQueryService;
import com.sdu.fund.biz.shared.query.vo.*;
import com.sdu.fund.core.model.bo.FundData;
import com.sdu.fund.core.repository.FundDataRepository;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 19:05
 **/
public class FundDataQueryServiceImpl implements FundDataQueryService {

    @SofaReference
    private FundDataRepository fundDataRepository;

    @Override
    public NetValuesVO queryNetValues(String fundCode) {
        FundData fundData = fundDataRepository.get(fundCode);
        return new NetValuesVO().convert(fundData);
    }

    @Override
    public TradeStatusVO queryTradeStatus(String fundCode) {
        FundData fundData = fundDataRepository.get(fundCode);
        return new TradeStatusVO().convert(fundData);
    }

    @Override
    public TradeLimitVO queryTradeLimit(String fundCode) {
        FundData fundData = fundDataRepository.get(fundCode);
        return new TradeLimitVO().convert(fundData);
    }

    @Override
    public PurchaseStepsVO queryPurchaseSteps(String fundCode) {
        FundData fundData = fundDataRepository.get(fundCode);
        return new PurchaseStepsVO().convert(fundData);
    }

    @Override
    public RedeemStepsVO queryRedeemSteps(String fundCode) {
        FundData fundData = fundDataRepository.get(fundCode);
        return new RedeemStepsVO().convert(fundData);
    }

    @Override
    public RatesVO queryRates(String fundCode) {
        FundData fundData = fundDataRepository.get(fundCode);
        return new RatesVO().convert(fundData);
    }
}

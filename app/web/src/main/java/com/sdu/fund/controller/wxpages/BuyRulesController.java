package com.sdu.fund.controller.wxpages;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.biz.shared.query.service.FundCompanyQueryService;
import com.sdu.fund.biz.shared.query.service.FundDataQueryService;
import com.sdu.fund.biz.shared.query.vo.*;
import com.sdu.fund.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 12:20
 **/
@RestController
@RequestMapping("/fundProduct/buyRules")
public class BuyRulesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyRulesController.class);

    @SofaReference
    private FundDataQueryService fundDataQueryService;

    @RequestMapping(value = "/buyRules", method = RequestMethod.POST)
    public Response<BuyRulesVO> queryFundCompany(@RequestParam(value = "fundCode") String fundCode) {
        BuyRulesVO buyRulesVO = new BuyRulesVO();
        try {
            TradeStatusVO tradeStatusVO = fundDataQueryService.queryTradeStatus(fundCode);
            TradeLimitVO tradeLimitVO = fundDataQueryService.queryTradeLimit(fundCode);
            PurchaseStepsVO purchaseStepsVO = fundDataQueryService.queryPurchaseSteps(fundCode);
            RedeemStepsVO redeemStepsVO = fundDataQueryService.queryRedeemSteps(fundCode);
            RatesVO ratesVO = fundDataQueryService.queryRates(fundCode);

            buyRulesVO.setTradeStatusVO(tradeStatusVO);
            buyRulesVO.setTradeLimitVO(tradeLimitVO);
            buyRulesVO.setPurchaseStepsVO(purchaseStepsVO);
            buyRulesVO.setRedeemStepsVO(redeemStepsVO);
            buyRulesVO.setRatesVO(ratesVO);
        } catch (Exception e) {
            LOGGER.error("基金公司查询失败，fundCompanyId={},msg={}", fundCode,
                    e.getMessage());
            return Response.buildErrorResponse();
        }

        return Response.buildSuccessResponse(buyRulesVO);
}
}

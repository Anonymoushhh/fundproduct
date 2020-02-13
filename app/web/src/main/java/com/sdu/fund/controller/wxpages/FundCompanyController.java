package com.sdu.fund.controller.wxpages;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.query.service.FundCommonQueryService;
import com.sdu.fund.biz.shared.query.service.FundCompanyQueryService;
import com.sdu.fund.biz.shared.query.vo.FundCompanyVO;
import com.sdu.fund.biz.shared.query.vo.FundManagerVO;
import com.sdu.fund.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 12:20
 **/
@RestController
@RequestMapping("/fundProduct/fundCompany")
public class FundCompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundCompanyController.class);

    @SofaReference
    private FundCompanyQueryService fundCompanyQueryService;

    @RequestMapping(value = "/fundCompanyInfoList", method = RequestMethod.GET)
    public Response<FundCompanyVO> queryFundCompany(@RequestParam(value = "fundCompanyId") String fundCompanyId) {
        FundCompanyVO fundCompanyVO = new FundCompanyVO();
        try {
            fundCompanyVO = fundCompanyQueryService.queryFundCompanyInfoList(fundCompanyId);
        } catch (Exception e) {
            LOGGER.error("基金公司查询失败，fundCompanyId={},msg={}", fundCompanyId,
                    e.getMessage());
            return Response.buildErrorResponse();
        }

        return Response.buildSuccessResponse(fundCompanyVO);
}
}

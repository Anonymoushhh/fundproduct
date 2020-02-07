package com.sdu.fund.controller.wxpages;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.query.service.FundCommonQueryService;
import com.sdu.fund.biz.shared.query.vo.FundManagerVO;
import com.sdu.fund.common.code.ResultCode;
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
@RequestMapping("/fundProduct/fundManager")
public class FundManagerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundManagerController.class);

    @SofaReference
    private FundCommonQueryService fundCommonQueryService;

    @RequestMapping(value = "/managers", method = RequestMethod.POST)
    public Response<List<FundManagerVO>> queryFundManagers(@RequestBody List<String> managerIds) {
        List<FundManagerVO> fundManagerVOS = Lists.newArrayList();
        for (String managerId : managerIds) {
            FundManagerVO fundManagerVO = new FundManagerVO();
            try {
                fundManagerVO = fundCommonQueryService.queryFundManager(managerId);
                fundManagerVOS.add(fundManagerVO);
            } catch (Exception e) {
                LOGGER.error("基金经理查询失败，managerId={},msg={}", managerId,
                        e.getMessage());
            }
        }

        return Response.buildSuccessResponse(fundManagerVOS);
    }
}

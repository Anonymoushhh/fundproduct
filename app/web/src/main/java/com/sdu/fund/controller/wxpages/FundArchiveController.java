package com.sdu.fund.controller.wxpages;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.query.service.FundArchiveQueryService;
import com.sdu.fund.biz.shared.query.service.FundCommonQueryService;
import com.sdu.fund.biz.shared.query.vo.FundArchiveVO;
import com.sdu.fund.biz.shared.query.vo.FundManagerVO;
import com.sdu.fund.core.model.bo.FundArchive;
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
@RequestMapping("/fundProduct/fundArchive")
public class FundArchiveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundArchiveController.class);

    @SofaReference
    private FundArchiveQueryService fundArchiveQueryService;

    @RequestMapping(value = "/archiveInfoList", method = RequestMethod.GET)
    public Response<FundArchiveVO> queryFundArchive(@RequestParam(value = "fundCode") String fundCode) {
        FundArchiveVO fundArchiveVO = new FundArchiveVO();
        try {
            fundArchiveVO = fundArchiveQueryService.queryFundArchiveInfoList(fundCode);
        } catch (Exception e) {
            LOGGER.error("基金档案查询失败，fundCode={},msg={}", fundCode, e.getMessage());
            return Response.buildErrorResponse();
        }

        return Response.buildSuccessResponse(fundArchiveVO);
    }
}

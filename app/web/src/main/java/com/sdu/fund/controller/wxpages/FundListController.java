package com.sdu.fund.controller.wxpages;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.query.service.FundCommonQueryService;
import com.sdu.fund.biz.shared.query.vo.RankVO;
import com.sdu.fund.common.code.ResultCode;
import com.sdu.fund.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: fundproduct
 * @description: 基金列表页控制器
 * @author: anonymous
 * @create: 2020/2/5 16:28
 **/
@RestController
@RequestMapping("/fundProduct/fundList")
public class FundListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundListController.class);

    @SofaReference
    private FundCommonQueryService fundCommonQueryService;

    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Response<List<RankVO>> queryRank(@RequestParam(value = "sortType") Integer sortType, @RequestParam(value =
            "fundType") Integer fundType, @RequestParam(value = "gainType") Integer gainType, @RequestParam(value =
            "curPage") Integer curPage, @RequestParam(value = "pageSize") Integer pageSize) {
        List<RankVO> rankVOS = Lists.newArrayList();
        try {
            rankVOS = fundCommonQueryService.queryFundList(sortType, fundType, gainType, curPage, pageSize);
        }catch(Exception e){
            LOGGER.error("基金列表查询失败，errCode={},msg={}", ResultCode.SERVER_EXCEPTION,
                    e.getMessage());
        }
        return Response.buildSuccessResponse(rankVOS);
    }
}

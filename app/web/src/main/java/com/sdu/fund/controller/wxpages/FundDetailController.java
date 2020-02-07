package com.sdu.fund.controller.wxpages;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.biz.shared.query.service.FundDataQueryService;
import com.sdu.fund.biz.shared.query.service.wxpages.FundDetailPageService;
import com.sdu.fund.biz.shared.query.vo.FooterMessageVO;
import com.sdu.fund.biz.shared.query.vo.NetValuesVO;
import com.sdu.fund.biz.shared.query.vo.TopMessageVO;
import com.sdu.fund.common.code.ResultCode;
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
@RequestMapping("/fundProduct/fundDetail")
public class FundDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundDetailController.class);

    @SofaReference
    private FundDetailPageService fundDetailPageService;

    @SofaReference
    private FundDataQueryService fundDataQueryService;

    @RequestMapping(value = "/topMessage", method = RequestMethod.GET)
    public Response<TopMessageVO> queryTopMessage(@RequestParam(value = "fundCode") String fundCode) {
        TopMessageVO topMessageVO = new TopMessageVO();
        try {
            topMessageVO = fundDetailPageService.queryTopMessage(fundCode);
        }catch(Exception e){
            LOGGER.error("基金详情topMessage查询失败，errCode={},msg={}", ResultCode.SERVER_EXCEPTION,
                    e.getMessage());
        }
        return Response.buildSuccessResponse(topMessageVO);
    }

    @RequestMapping(value = "/netValues", method = RequestMethod.GET)
    public Response<NetValuesVO> queryNetValues(@RequestParam(value = "fundCode") String fundCode) {
        NetValuesVO netValuesVO = new NetValuesVO();
        try {
            netValuesVO = fundDataQueryService.queryNetValues(fundCode);
        }catch(Exception e){
            LOGGER.error("基金详情净值查询失败，errCode={},msg={}", ResultCode.SERVER_EXCEPTION,
                    e.getMessage());
        }
        return Response.buildSuccessResponse(netValuesVO);
    }

    @RequestMapping(value = "/estimatedNet", method = RequestMethod.GET)
    public Response<NetValuesVO> estimatedNetValue(@RequestParam(value = "fundCode") String fundCode) {
        NetValuesVO netValuesVO = new NetValuesVO();
        try {
            // TODO 估算净值，实时接口获取
        }catch(Exception e){
            LOGGER.error("基金估算净值查询失败，errCode={},msg={}", ResultCode.SERVER_EXCEPTION,
                    e.getMessage());
        }
        return Response.buildSuccessResponse(netValuesVO);
    }

    @RequestMapping(value = "/performanceInformation", method = RequestMethod.GET)
    public Response<NetValuesVO> queryPerformanceInformation(@RequestParam(value = "fundCode") String fundCode) {
        NetValuesVO netValuesVO = new NetValuesVO();
        try {
            // TODO 业绩信息，数据爬取还没写。。。
        }catch(Exception e){
            LOGGER.error("基金业绩信息查询失败，errCode={},msg={}", ResultCode.SERVER_EXCEPTION,
                    e.getMessage());
        }
        return Response.buildSuccessResponse(netValuesVO);
    }

    @RequestMapping(value = "/footerMessage", method = RequestMethod.GET)
    public Response<FooterMessageVO> queryFooterMessage(@RequestParam(value = "fundCode") String fundCode) {
        FooterMessageVO footerMessageVO = new FooterMessageVO();
        try {
            footerMessageVO = fundDetailPageService.queryFooterMessage(fundCode);
        }catch(Exception e){
            LOGGER.error("基金footerMessage查询失败，errCode={},msg={}", ResultCode.SERVER_EXCEPTION,
                    e.getMessage());
        }
        return Response.buildSuccessResponse(footerMessageVO);
    }
}

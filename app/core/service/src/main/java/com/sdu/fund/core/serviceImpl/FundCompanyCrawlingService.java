package com.sdu.fund.core.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sdu.fund.common.code.ResultCode;
import com.sdu.fund.common.result.Result;
import com.sdu.fund.common.util.DateUtil;
import com.sdu.fund.common.util.ResultUtil;
import com.sdu.fund.core.repository.FundCompanyRepository;
import com.sdu.fund.core.request.CrawingRequest;
import com.sdu.fund.core.service.DataCrawlingService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.sdu.fund.core.model.bo.FundCompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @program: fundproduct
 * @description: 基金公司数据爬取服务实现
 * @author: anonymous
 * @create: 2019-11-23 22:03
 **/
public class FundCompanyCrawlingService implements DataCrawlingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundCompanyCrawlingService.class);

    @Autowired
    private FundCompanyRepository fundCompanyRepository;

    public List<FundCompany> deal(String sourceData) {
        String json = StringUtils.substringAfter(sourceData, "=");
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray data = jsonObject.getJSONArray("datas");
        List<FundCompany> fundCompanies = Lists.newArrayList();
        for (int i = 0; i < data.size(); i++) {
            JSONArray item = data.getJSONArray(i);
            FundCompany fundCompany = new FundCompany();
            fundCompany.setFundCompanyCode(item.getString(0));
            fundCompany.setFundCompanyName(item.getString(1));
            fundCompany.setEstablishDate(StringUtils.isNotBlank(item.getString(2)) ?
                DateUtil.strToDate(item.getString(2), DateUtil.FMT_YMD1) : null);
            fundCompany.setFundAmount(StringUtils.isNotBlank(item.getString(3)) ?
                Integer.parseInt(item.getString(3)) : 0);
            fundCompany.setManager(item.getString(4));
            fundCompany.setFundCompanyNameAcronym(item.getString(5));
            fundCompany.setManagementScale(item.getString(7));
            fundCompany.setGrade(item.getString(8).length());
            fundCompany.setFundCompanyNameAbbr(item.getString(9));
            fundCompany.setUpdateTime(StringUtils.isNotBlank(item.getString(11)) ?
                DateUtil.strToDate(StringUtils.substringBefore(item.getString(11), " "),
                    DateUtil.FMT_YMD2) : new Date());

            fundCompanies.add(fundCompany);
        }

        return fundCompanies;
    }

    @Override
    public Result save(Object data) {
        List<String> success = Lists.newArrayList();
        List<String> failure = Lists.newArrayList();
        List<FundCompany> fundCompanies = (List<FundCompany>) data;
        if (CollectionUtils.isEmpty(fundCompanies)) {
            LOGGER.error("#爬取数据存储或更新失败#，fundCompanies为空,errCode={}",
                ResultCode.PARAMETER_ILLEGAL);
            return ResultUtil.buildFailedResult(ResultCode.PARAMETER_ILLEGAL);
        }
        for (FundCompany fundCompany : fundCompanies) {
            Result result;
            FundCompany fundCompanyExist = fundCompanyRepository.get(fundCompany.getFundCompanyCode());
            if (fundCompanyExist == null) {
                result = fundCompanyRepository.add(fundCompany);
                // 天天基金数据更新时间与数据库不一致时更新
            } else if (!DateUtil.sameDateTime(fundCompany.getUpdateTime(), fundCompanyExist.getUpdateTime())) {
                result = fundCompanyRepository.update(fundCompany);
            } else {
                result = ResultUtil.buildSuccessResult();
            }
            if (result != null && result.isSuccess()) {
                success.add(fundCompany.getFundCompanyCode());
            } else {
                failure.add(fundCompany.getFundCompanyCode());
            }
        }
        LOGGER.info("#爬取数据存储或更新完成#，总数量为{},成功数量为{},失败数量为{},失败记录为{}", fundCompanies.size(), success.size(),
            failure.size(), failure.toString());
        if (CollectionUtils.isNotEmpty(success) && CollectionUtils.isNotEmpty(failure)) {
            LOGGER.error("#爬取数据存储或更新失败#，失败数量为{},失败记录为{},errCode={}", failure.size(), failure.toString(),
                ResultCode.PARTIAL_FAILURE);
            return ResultUtil.buildFailedResult(ResultCode.PARTIAL_FAILURE, failure);
        }

        return ResultUtil.buildSuccessResult();
    }

    @Override
    public Result execute() {
        // 1.爬取
        CrawingRequest crawingRequest = new CrawingRequest();
        crawingRequest.setUrl("http://fund.eastmoney.com/Data/FundRankScale.aspx?_=1574132995835");
        String sourceData = crawing(crawingRequest);
        // 2.处理
        List<FundCompany> fundCompanies = null;
        if (sourceData != null) {
            fundCompanies = deal(sourceData);
        } else {
            return ResultUtil.buildFailedResult(ResultCode.URL_INVAILD);
        }
        // 3.保存
        Result result = save(fundCompanies);

        return result;
    }


}

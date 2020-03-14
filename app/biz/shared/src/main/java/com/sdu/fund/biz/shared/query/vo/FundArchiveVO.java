package com.sdu.fund.biz.shared.query.vo;

import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.constants.FundArchiveVOKey;
import com.sdu.fund.biz.shared.constants.FundManagerVOKey;
import com.sdu.fund.common.model.BaseEntry;
import com.sdu.fund.common.util.DateUtil;
import com.sdu.fund.core.model.bo.FundArchive;
import com.sdu.fund.core.model.bo.FundManager;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 19:24
 **/
public class FundArchiveVO {

    private List<BaseEntry> listData;

    public FundArchiveVO convert(FundArchive fundArchive) {
        if(fundArchive==null){
            return this;
        }
        List<BaseEntry> listData = Lists.newArrayList();
        listData.add(new BaseEntry(FundArchiveVOKey.FUND_NAME, fundArchive.getFundName()));
        listData.add(new BaseEntry(FundArchiveVOKey.FUND_NAME_ABBR, fundArchive.getFundNameAbbr()));
        listData.add(new BaseEntry(FundArchiveVOKey.FUND_CODE, fundArchive.getFundCode()));
        listData.add(new BaseEntry(FundArchiveVOKey.FUND_TYPE, fundArchive.getFundType()));
        listData.add(new BaseEntry(FundArchiveVOKey.ESTABLISH_DATE, DateUtil.dateToStr(fundArchive.getEstablishDate()
                ,DateUtil.FMT_YMD1)));
        listData.add(new BaseEntry(FundArchiveVOKey.ISSUE_DATE,DateUtil.dateToStr(fundArchive.getIssueDate(),
                DateUtil.FMT_YMD1)));
        listData.add(new BaseEntry(FundArchiveVOKey.COMPANY_NAME,fundArchive.getCompanyName()));
        listData.add(new BaseEntry(FundArchiveVOKey.CUSTODIAN,fundArchive.getCustodian()));
        listData.add(new BaseEntry(FundArchiveVOKey.PERFORMANCE_BASELINE,fundArchive.getPerformanceBaseline()));
        listData.add(new BaseEntry(FundArchiveVOKey.TRACKING_TARGET,fundArchive.getTrackingTarget()));
        listData.add(new BaseEntry(FundArchiveVOKey.INVESTMENT_TARGET,fundArchive.getInvestmentTarget()));
        listData.add(new BaseEntry(FundArchiveVOKey.INVESTMENT_CONCEPT,fundArchive.getInvestmentConcept()));
        listData.add(new BaseEntry(FundArchiveVOKey.INVESTMENT_SCOPE,fundArchive.getInvestmentScope()));
        listData.add(new BaseEntry(FundArchiveVOKey.INVESTMENT_STRATEGY,fundArchive.getInvestmentStrategy()));
        listData.add(new BaseEntry(FundArchiveVOKey.DIVIDEND_POLICY,fundArchive.getDividendPolicy()));
        listData.add(new BaseEntry(FundArchiveVOKey.RISK_RETURN_CHARACTERISTICS,fundArchive.getRiskReturnCharacteristics()));

        this.listData = listData;
        return this;
    }

    public List<BaseEntry> getListData() {
        return listData;
    }

    public void setListData(List<BaseEntry> listData) {
        this.listData = listData;
    }
}

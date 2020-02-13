package com.sdu.fund.biz.shared.query.vo;

import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.constants.FundCompanyVOKey;
import com.sdu.fund.biz.shared.constants.FundManagerVOKey;
import com.sdu.fund.common.model.BaseEntry;
import com.sdu.fund.common.util.DateUtil;
import com.sdu.fund.core.model.bo.FundCompany;
import com.sdu.fund.core.model.bo.FundManager;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 16:32
 **/
public class FundCompanyVO {

    private String fundCompanyName;
    private List<BaseEntry> listData;

    public FundCompanyVO convert(FundCompany fundCompany) {
        if (fundCompany == null) {
            return new FundCompanyVO();
        }
        this.fundCompanyName = fundCompany.getFundCompanyName();
        List<BaseEntry> listData = Lists.newArrayList();
        listData.add(new BaseEntry(FundCompanyVOKey.FUND_COMPANY_NAME_ABBR, fundCompany.getFundCompanyNameAbbr()));
        listData.add(new BaseEntry(FundCompanyVOKey.GRADE, fundCompany.getGrade() != null ? (fundCompany.getGrade() +
                "星评级") : null));
        listData.add(new BaseEntry(FundCompanyVOKey.MANAGEMENT_SCALE, fundCompany.getManagementScale()));
        listData.add(new BaseEntry(FundCompanyVOKey.FUND_AMOUNT,
                fundCompany.getFundAmount() != null ? (fundCompany.getFundAmount() + "只") : null));
        listData.add(new BaseEntry(FundCompanyVOKey.ESTABLISH_DATE, DateUtil.dateToStr(fundCompany.getEstablishDate()
                , DateUtil.FMT_YMD1)));
        this.listData = listData;
        return this;
    }

    public String getFundCompanyName() {
        return fundCompanyName;
    }

    public void setFundCompanyName(String fundCompanyName) {
        this.fundCompanyName = fundCompanyName;
    }

    public List<BaseEntry> getListData() {
        return listData;
    }

    public void setListData(List<BaseEntry> listData) {
        this.listData = listData;
    }
}

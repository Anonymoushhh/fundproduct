package com.sdu.fund.biz.shared.query.vo;

import com.sdu.fund.core.model.bo.FundArchive;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 14:53
 **/
public class FooterMessageVO {

    private List<String> managerIds;
    private List<String> managerNames;
    private String fundCompanyId;
    private String fundCompanyName;

    public FooterMessageVO convert(FundArchive fundArchive){
        if(fundArchive==null){
            return this;
        }
        this.managerIds = fundArchive.getManagerIds();
        this.managerNames = fundArchive.getManagerNames();
        this.fundCompanyId = fundArchive.getCompanyCode();
        this.fundCompanyName = fundArchive.getCompanyName();
        return this;
    }
    public List<String> getManagerIds() {
        return managerIds;
    }

    public void setManagerIds(List<String> managerIds) {
        this.managerIds = managerIds;
    }

    public List<String> getManagerNames() {
        return managerNames;
    }

    public void setManagerNames(List<String> managerNames) {
        this.managerNames = managerNames;
    }

    public String getFundCompanyId() {
        return fundCompanyId;
    }

    public void setFundCompanyId(String fundCompanyId) {
        this.fundCompanyId = fundCompanyId;
    }

    public String getFundCompanyName() {
        return fundCompanyName;
    }

    public void setFundCompanyName(String fundCompanyName) {
        this.fundCompanyName = fundCompanyName;
    }
}

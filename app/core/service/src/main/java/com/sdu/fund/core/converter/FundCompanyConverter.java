package com.sdu.fund.core.converter;

import com.sdu.fund.core.model.bo.FundCompany;
import com.sdu.fund.common.dal.entity.FundCompanyDo;

/**
 * @program: fundproduct
 * @description: 基金公司对象转换类
 * @author: anonymous
 * @create: 2019-11-29 10:51
 **/
public class FundCompanyConverter{

    public static FundCompany FundCompanyDoconvert2FundCompany(FundCompanyDo fundCompanyDo){
        if(fundCompanyDo == null){
            return null;
        }
        FundCompany fundCompany = new FundCompany();
        fundCompany.setFundCompanyCode(fundCompanyDo.getFundCompanyCode());
        fundCompany.setFundCompanyName(fundCompanyDo.getFundCompanyName());
        fundCompany.setEstablishDate(fundCompanyDo.getEstablishDate());
        fundCompany.setFundAmount(fundCompanyDo.getFundAmount());
        fundCompany.setManager(fundCompanyDo.getManager());
        fundCompany.setFundCompanyNameAcronym(fundCompanyDo.getFundCompanyNameAcronym());
        fundCompany.setManagementScale(fundCompanyDo.getManagementScale());
        fundCompany.setGrade(fundCompanyDo.getGrade());
        fundCompany.setFundCompanyNameAbbr(fundCompanyDo.getFundCompanyNameAbbr());
        fundCompany.setUpdateTime(fundCompanyDo.getUpdateTime());

        return fundCompany;
    }

    public static FundCompanyDo FundCompanyconvert2FundCompanyDo(FundCompany fundCompany){
        if(fundCompany == null){
            return null;
        }
        FundCompanyDo fundCompanyDo = new FundCompanyDo();
        fundCompanyDo.setFundCompanyCode(fundCompany.getFundCompanyCode());
        fundCompanyDo.setFundCompanyName(fundCompany.getFundCompanyName());
        fundCompanyDo.setEstablishDate(fundCompany.getEstablishDate());
        fundCompanyDo.setFundAmount(fundCompany.getFundAmount());
        fundCompanyDo.setManager(fundCompany.getManager());
        fundCompanyDo.setFundCompanyNameAcronym(fundCompany.getFundCompanyNameAcronym());
        fundCompanyDo.setManagementScale(fundCompany.getManagementScale());
        fundCompanyDo.setGrade(fundCompany.getGrade());
        fundCompanyDo.setFundCompanyNameAbbr(fundCompany.getFundCompanyNameAbbr());
        fundCompanyDo.setUpdateTime(fundCompany.getUpdateTime());

        return fundCompanyDo;
    }

}

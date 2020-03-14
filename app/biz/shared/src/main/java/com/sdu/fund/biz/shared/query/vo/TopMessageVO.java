package com.sdu.fund.biz.shared.query.vo;

import com.sdu.fund.core.model.enums.FundTypeEnum;
import com.sdu.fund.common.validator.Validator;
import com.sdu.fund.core.model.bo.FundArchive;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 12:30
 **/
public class TopMessageVO {

    private String fundName;
    private String type;
    private String risk;
    private String star;

    public TopMessageVO convert(FundArchive fundArchive){
        if(fundArchive==null){
            return this;
        }
        this.fundName = fundArchive.getFundNameAbbr();
        FundTypeEnum fundTypeEnum = FundTypeEnum.getEnumByMsg(fundArchive.getFundType());
        Validator.notNull(fundTypeEnum);
        this.type = fundTypeEnum.getMsg();
        this.risk = "中高风险";
        this.star = "5星评级";
        return this;
    }
    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}

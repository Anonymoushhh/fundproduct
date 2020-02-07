package com.sdu.fund.biz.shared.query.vo;

import com.sdu.fund.biz.shared.enums.FundTypeEnum;
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
    private Integer type;
    private String risk;
    private String star;

    public TopMessageVO convert(FundArchive fundArchive){
        this.fundName = fundArchive.getFundName();
        FundTypeEnum fundTypeEnum = FundTypeEnum.getEnumByMsg(fundArchive.getFundType());
        Validator.notNull(fundTypeEnum);
        this.type = fundTypeEnum.getCode();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

package com.sdu.fund.biz.shared.query.vo;


import com.sdu.fund.core.model.bo.FundData;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/5 17:43
 **/
public class RankVO {

    private String fundCode;
    private String name;
    private Double netValue;
    private Double gain;

    public RankVO convert(FundData fundData){
        this.fundCode = fundData.getFundCode();
        this.name = fundData.getFundName();
        this.netValue = fundData.getUnitNet();
        if(fundData.getGrowthRate()!=null){
            this.gain = fundData.getGrowthRate();
        }else if(fundData.getEarningRate1w()!=null){
            this.gain = fundData.getEarningRate1w();
        }else if(fundData.getEarningRate1m()!=null){
            this.gain = fundData.getEarningRate1m();
        }else if(fundData.getEarningRate3m()!=null){
            this.gain = fundData.getEarningRate3m();
        }else if(fundData.getEarningRate6m()!=null){
            this.gain = fundData.getEarningRate6m();
        }else if(fundData.getEarningRate1y()!=null){
            this.gain = fundData.getEarningRate1y();
        }else if(fundData.getEarningRate2y()!=null){
            this.gain = fundData.getEarningRate2y();
        }else if(fundData.getEarningRate3y()!=null){
            this.gain = fundData.getEarningRate3y();
        }else if(fundData.getEarningRateThisYear()!=null){
            this.gain = fundData.getEarningRateThisYear();
        }else if(fundData.getEarningRateFromEstablish()!=null){
            this.gain = fundData.getEarningRateFromEstablish();
        }else{
            gain = null;
        }
        return this;
    }
    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNetValue() {
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }
}

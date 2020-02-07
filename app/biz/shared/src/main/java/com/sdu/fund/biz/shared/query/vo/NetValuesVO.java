package com.sdu.fund.biz.shared.query.vo;

import com.sdu.fund.core.model.bo.FundData;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 13:52
 **/
public class NetValuesVO {
    private Double unitNet;
    private Double accumulatedNet;
    private Boolean gainLastDay;
    // 带%单位
    private String gainRangeLastDay;
    private Double estimatedNet;
    private Boolean gainToday;
    private String gainRangeToday;

    public NetValuesVO convert(FundData fundData) {
        this.unitNet = fundData.getUnitNet();
        this.accumulatedNet = fundData.getAccumulatedNet();
        if (fundData.getGrowthRate() != null) {
            this.gainLastDay = fundData.getGrowthRate() >= 0;
        }
        this.gainRangeLastDay = fundData.getGrowthRate() + "%";
        this.estimatedNet = fundData.getEstimatedNet();
        if (fundData.getGainRangeToday() != null) {
            this.gainLastDay = fundData.getGainRangeToday() >= 0;
        }
        this.gainRangeToday = fundData.getGainRangeToday() + "%";
        return this;
    }

    public Double getUnitNet() {
        return unitNet;
    }

    public void setUnitNet(Double unitNet) {
        this.unitNet = unitNet;
    }

    public Double getAccumulatedNet() {
        return accumulatedNet;
    }

    public void setAccumulatedNet(Double accumulatedNet) {
        this.accumulatedNet = accumulatedNet;
    }

    public Boolean getGainLastDay() {
        return gainLastDay;
    }

    public void setGainLastDay(Boolean gainLastDay) {
        this.gainLastDay = gainLastDay;
    }

    public String getGainRangeLastDay() {
        return gainRangeLastDay;
    }

    public void setGainRangeLastDay(String gainRangeLastDay) {
        this.gainRangeLastDay = gainRangeLastDay;
    }

    public String getGainRangeToday() {
        return gainRangeToday;
    }

    public void setGainRangeToday(String gainRangeToday) {
        this.gainRangeToday = gainRangeToday;
    }

    public Double getEstimatedNet() {
        return estimatedNet;
    }

    public void setEstimatedNet(Double estimatedNet) {
        this.estimatedNet = estimatedNet;
    }

    public Boolean getGainToday() {
        return gainToday;
    }

    public void setGainToday(Boolean gainToday) {
        this.gainToday = gainToday;
    }

}

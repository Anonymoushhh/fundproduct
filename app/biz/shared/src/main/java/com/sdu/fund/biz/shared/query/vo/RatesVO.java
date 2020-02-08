package com.sdu.fund.biz.shared.query.vo;

import com.sdu.fund.core.model.bo.FundData;
import com.sdu.fund.core.model.bo.Rate;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 16:32
 **/
public class RatesVO {

    private String managerRate;
    private String trusteeRate;
    private String serviceRate;
    private List<Rate> purchaseRate;
    private List<Rate> redeemRate;

    public RatesVO convert(FundData fundData) {
        this.managerRate = fundData.getManagerRate() + "%";
        this.trusteeRate = fundData.getTrusteeRate() + "%";
        this.serviceRate = fundData.getServiceRate() + "%";
        this.purchaseRate = fundData.getPurchaseRate();
        this.redeemRate = fundData.getRedeemRate();
        return this;
    }

    public String getManagerRate() {
        return managerRate;
    }

    public void setManagerRate(String managerRate) {
        this.managerRate = managerRate;
    }

    public String getTrusteeRate() {
        return trusteeRate;
    }

    public void setTrusteeRate(String trusteeRate) {
        this.trusteeRate = trusteeRate;
    }

    public String getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(String serviceRate) {
        this.serviceRate = serviceRate;
    }

    public List<Rate> getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(List<Rate> purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public List<Rate> getRedeemRate() {
        return redeemRate;
    }

    public void setRedeemRate(List<Rate> redeemRate) {
        this.redeemRate = redeemRate;
    }
}

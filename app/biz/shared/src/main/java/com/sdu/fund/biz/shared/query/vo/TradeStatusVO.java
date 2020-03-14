package com.sdu.fund.biz.shared.query.vo;

import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.constants.FundManagerVOKey;
import com.sdu.fund.biz.shared.constants.TradeStatusVOKey;
import com.sdu.fund.common.model.BaseEntry;
import com.sdu.fund.common.util.DateUtil;
import com.sdu.fund.core.model.bo.FundData;
import com.sdu.fund.core.model.bo.FundManager;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 16:32
 **/
public class TradeStatusVO {

    private List<BaseEntry> listData;

    public TradeStatusVO convert(FundData fundData) {
        if(fundData==null){
            return this;
        }
        List<BaseEntry> listData = Lists.newArrayList();
        listData.add(new BaseEntry(TradeStatusVOKey.PURCHASE_STATUS, fundData.getPurchaseStatus().getMsg()));
        listData.add(new BaseEntry(TradeStatusVOKey.REDEEM_STATUS, fundData.getRedeemStatus().getMsg()));
        listData.add(new BaseEntry(TradeStatusVOKey.TIMING_INVESTMENT_STATUS, "暂不支持"));
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

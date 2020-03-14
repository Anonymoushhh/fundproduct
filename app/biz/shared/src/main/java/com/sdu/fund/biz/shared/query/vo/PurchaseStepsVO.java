package com.sdu.fund.biz.shared.query.vo;

import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.constants.FundManagerVOKey;
import com.sdu.fund.biz.shared.constants.PurchaseStepsVOKey;
import com.sdu.fund.common.model.BaseEntry;
import com.sdu.fund.common.model.StepsEntry;
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
public class PurchaseStepsVO {

    private List<StepsEntry> listData;

    public PurchaseStepsVO convert(FundData fundData) {
        if(fundData==null){
            return this;
        }
        List<StepsEntry> listData = Lists.newArrayList();
        listData.add(new StepsEntry(PurchaseStepsVOKey.BUY, "T日"));
        listData.add(new StepsEntry(PurchaseStepsVOKey.SHARE_CONFIRM, fundData.getBuyConfirmDay().getMsg()));
        listData.add(new StepsEntry(PurchaseStepsVOKey.SEE_PROFIT, fundData.getBuyConfirmDay().getMsg() + "收益更新后"));
        this.listData = listData;
        return this;
    }

    public List<StepsEntry> getListData() {
        return listData;
    }

    public void setListData(List<StepsEntry> listData) {
        this.listData = listData;
    }
}
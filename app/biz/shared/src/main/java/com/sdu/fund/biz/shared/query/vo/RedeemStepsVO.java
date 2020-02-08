package com.sdu.fund.biz.shared.query.vo;

import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.constants.FundManagerVOKey;
import com.sdu.fund.biz.shared.constants.PurchaseStepsVOKey;
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
public class RedeemStepsVO {

    private List<BaseEntry> listData;

    public RedeemStepsVO convert(FundData fundData) {
        listData.add(new BaseEntry(PurchaseStepsVOKey.BUY, "T日"));
        listData.add(new BaseEntry(PurchaseStepsVOKey.SHARE_CONFIRM, fundData.getRedeemComfirmDay()));
        listData.add(new BaseEntry(PurchaseStepsVOKey.SEE_PROFIT, fundData.getRedeemComfirmDay() + "清算结束后"));
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

package com.sdu.fund.biz.shared.query.vo;

import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.constants.FundManagerVOKey;
import com.sdu.fund.biz.shared.constants.TradeLimitVOKey;
import com.sdu.fund.common.model.BaseEntry;
import com.sdu.fund.common.util.DateUtil;
import com.sdu.fund.common.util.NumberUtil;
import com.sdu.fund.core.model.bo.FundData;
import com.sdu.fund.core.model.bo.FundManager;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 16:32
 **/
public class TradeLimitVO {

    private List<BaseEntry> listData;

    public TradeLimitVO convert(FundData fundData) {
        if (fundData == null) {
            return this;
        }
        List<BaseEntry> listData = Lists.newArrayList();
        listData.add(new BaseEntry(TradeLimitVOKey.MIN_PURCHASE_AMOUNT,
                NumberUtil.getDouble_to2_no4no5(fundData.getMinPurchaseAmount()) +
                        "元"));
        listData.add(new BaseEntry(TradeLimitVOKey.FIRST_BUY_AMOUNT, fundData.getFirstBuyAmount() != null ? (
                fundData.getFirstBuyAmount() + "元") : "无限制"));
        listData.add(new BaseEntry(TradeLimitVOKey.SUPPLEMENT_AMOUNT, fundData.getSupplementAmount() != null ?
                (fundData.getSupplementAmount() + "元") : "无限制"));
        listData.add(new BaseEntry(TradeLimitVOKey.PURCHASE_LIMIT_ONE_DAY, fundData.getPurchaseLimitOneDay() != null ?
                (fundData.getPurchaseLimitOneDay() + "元") : "无限制"));
        listData.add(new BaseEntry(TradeLimitVOKey.POSITIONS_LIMIT, fundData.getPositionsLimit() != null ?
                (fundData.getPositionsLimit() + "元") : "无限制"));
        listData.add(new BaseEntry(TradeLimitVOKey.MIN_REDEEM_SHARE, fundData.getMinRedeemShare() != null ?
                (fundData.getMinRedeemShare() + "份") : "无限制"));
        listData.add(new BaseEntry(TradeLimitVOKey.MIN_REMAINING_SHARE, fundData.getMinRemainingShare() != null ?
                (fundData.getMinRemainingShare() + "份") : "无限制"));
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

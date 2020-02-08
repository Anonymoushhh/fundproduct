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
public class PurchaseStepsVO {

    private List<BaseEntry> listData;

    public PurchaseStepsVO convert(FundData fundData) {
        List<BaseEntry> listData = Lists.newArrayList();
        listData.add(new BaseEntry(PurchaseStepsVOKey.BUY, "T日"));
        listData.add(new BaseEntry(PurchaseStepsVOKey.SHARE_CONFIRM, fundData.getBuyComfirmDay()));
        listData.add(new BaseEntry(PurchaseStepsVOKey.SEE_PROFIT, fundData.getBuyComfirmDay() + "收益更新后"));
        this.listData = listData;
        return this;
    }

    public List<BaseEntry> getListData() {
        return listData;
    }

    public void setListData(List<BaseEntry> listData) {
        this.listData = listData;
    }

    class StepsEntry extends BaseEntry {

        private String text;

        public StepsEntry(String text, String value) {
            super(null, value);
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
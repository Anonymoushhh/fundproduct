package com.sdu.fund.biz.shared.query.vo;

import com.google.common.collect.Lists;
import com.sdu.fund.biz.shared.constants.FundManagerVOKey;
import com.sdu.fund.common.model.BaseEntry;
import com.sdu.fund.common.util.DateUtil;
import com.sdu.fund.core.model.bo.FundManager;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 16:32
 **/
public class FundManagerVO {

    private String desc;
    private List<BaseEntry> listData;

    public FundManagerVO convert(FundManager fundManager) {
        this.desc = fundManager.getManagerIntroduction();
        List<BaseEntry> listData = Lists.newArrayList();
        listData.add(new BaseEntry(FundManagerVOKey.COMPANY_NAME, fundManager.getCompanyName()));
        listData.add(new BaseEntry(FundManagerVOKey.SERVICE_DATE, fundManager.getServiceDate() + "天"));
        listData.add(new BaseEntry(FundManagerVOKey.ASSETS_SCALE, fundManager.getAssetsScale()));
        listData.add(new BaseEntry(FundManagerVOKey.BEST_RETURN_HISTORY, fundManager.getBestReturnHistory()));
        listData.add(new BaseEntry(FundManagerVOKey.BEST_RETURN_NOW, fundManager.getBestReturnNameNow()));
        listData.add(new BaseEntry(FundManagerVOKey.START_WORKING_TIME,
                DateUtil.dateToStr(fundManager.getStartWorkingTime(), DateUtil.FMT_YMD1)));
        this.listData = listData;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<BaseEntry> getListData() {
        return listData;
    }

    public void setListData(List<BaseEntry> listData) {
        this.listData = listData;
    }
}

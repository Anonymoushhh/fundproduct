package com.sdu.fund.core.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sdu.fund.common.code.ResultCode;
import com.sdu.fund.common.exception.CommonException;
import com.sdu.fund.common.exception.HttpException;
import com.sdu.fund.common.result.Result;
import com.sdu.fund.common.util.DateUtil;
import com.sdu.fund.common.util.ResultUtil;
import com.sdu.fund.common.util.TypeConvertUtil;
import com.sdu.fund.common.validator.Validator;
import com.sdu.fund.core.constants.FundDataKey;
import com.sdu.fund.core.constants.Url;
import com.sdu.fund.core.converter.FundTypeConverter;
import com.sdu.fund.core.converter.RateAndDayFormatConverter;
import com.sdu.fund.core.model.bo.FundData;
import com.sdu.fund.core.model.bo.Rate;
import com.sdu.fund.core.model.enums.ConfirmDayEnum;
import com.sdu.fund.core.model.enums.PurchaseStatusEnum;
import com.sdu.fund.core.model.enums.RedeemStatusEnum;
import com.sdu.fund.core.repository.FundDataRepository;
import com.sdu.fund.core.request.CrawingRequest;
import com.sdu.fund.core.service.DataCrawlingService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.*;

public class FundDataCrawlingService implements DataCrawlingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundDataCrawlingService.class);

    private static Map<String, String> dealMethods = Maps.newLinkedHashMap();

    private static final Set<String> ignore = Sets.newHashSet();

    static {
        dealMethods.put(Url.fundDataList, "dealDataForList");
        dealMethods.put(Url.fundDataHtml, "dealDataForHtml");
        ignore.addAll(Lists.newArrayList("860005", "001298", "000545", "000577", "001117", "001891", "166020",
                "000619"));
    }

    @Autowired
    private FundDataRepository fundDataRepository;

    private String crawingFundDataListData(CrawingRequest crawingRequest) throws Exception {
        String fundDataListData = StringUtils.substringBetween(crawing(crawingRequest), "datas:", ",allRecords");
        return fundDataListData;
    }

    /*
     * @description 基金数值数据爬取的组装
     * @param [crawingRequest]
     * @return java.lang.String
     * @author anonymous
     * @date 2020/1/26
     */
    private String crawingFundDataData(CrawingRequest crawingRequest) throws Exception {
        Map<String, String> dataMap = Maps.newLinkedHashMap();
        List<CrawingRequest> urls = crawingRequest.getCrawingRequests();
        CrawingRequest fundDataHtmlCrawingRequest = urls.get(0);
        String fundCode = null;
        // 1.获取基金简要信息列表
        String fundDataListData = (String) crawingRequest.getExtInfo().get(FundDataKey.FUND_DATA_LIST_DATA);
        JSONArray fundDataListArray = JSONArray.parseArray(fundDataListData);
        fundCode = fundDataListArray.getString(0);
        // 2.爬取html信息
        fundDataHtmlCrawingRequest.setUrl(StringUtils.replace(Url.fundDataHtml, "fundcode",
                fundCode));
        String fundDataHtmlData = crawing(fundDataHtmlCrawingRequest);
        // 3.Jsoup解析html
        Document document = Jsoup.parse(fundDataHtmlData);
        Elements elements = document.select("div[class=boxitem w790]");

        JSONObject fundDataHtml = new JSONObject();
        String fundType = document.select("div[class=bs_gl]").get(0).select("span").get(1).text();
        fundDataHtml.put(FundDataKey.FUND_TYPE, fundType);
        for (int i = 0; i < elements.size(); i++) {
            Element table = elements.get(i);
            final List<String> keys = Lists.newArrayList("applicableAmount", "applicableTime", "rate");

            if (table != null) {
                String title = table.select("label.left").text();
                Elements trs;
                Elements rows;
                switch (title) {
                    case "交易状态":
                        trs = table.select("tr");
                        JSONObject tradeStatus = new JSONObject();
                        // 说明是可以申购的基金
                        rows = trs.get(0).select("td.w135");
                        tradeStatus.put(FundDataKey.PURCHASE_STATUS, rows.get(0).text());
                        tradeStatus.put(FundDataKey.REDEEM_STATUS, rows.get(1).text());
                        fundDataHtml.put(FundDataKey.TRADE_STATUS, JSON.toJSONString(tradeStatus));

                        break;
                    case "申购与赎回金额":
                        trs = table.select("tr");
                        JSONObject purchaseAndRedeemAmount = new JSONObject();
                        // 能走到这说明是可以申购的基金
                        rows = trs.get(0).select("td.w135");
                        purchaseAndRedeemAmount.put(FundDataKey.MIN_PURCHASE_AMOUNT,
                                StringUtils.replace(rows.get(0).text(), "元", ""));
                        purchaseAndRedeemAmount.put(FundDataKey.PURCHASE_LIMIT_ONT_DAY,
                                StringUtils.replace(rows.get(2).text(), "元", ""));
                        rows = trs.get(1).select("td.w135");
                        purchaseAndRedeemAmount.put(FundDataKey.FIRST_BUY_AMOUNT,
                                StringUtils.replace(rows.get(0).text(), "元", ""));
                        purchaseAndRedeemAmount.put(FundDataKey.SUPPLEMENT_AMOUNT,
                                StringUtils.replace(rows.get(1).text(), "元", ""));
                        purchaseAndRedeemAmount.put(FundDataKey.POSITIONS_LIMIT,
                                StringUtils.replace(rows.get(2).text(), "元", ""));
                        rows = trs.get(2).select("td.w135");
                        purchaseAndRedeemAmount.put(FundDataKey.MIN_REDEEM_SHARE,
                                StringUtils.replace(rows.get(0).text(), "份", ""));
                        purchaseAndRedeemAmount.put(FundDataKey.MIN_REMAINING_SHARE,
                                StringUtils.replace(rows.get(1).text(), "份", ""));
                        fundDataHtml.put(FundDataKey.PURCHASE_AND_REDEEM_AMOUNT,
                                JSON.toJSONString(purchaseAndRedeemAmount));

                        break;
                    case "交易确认日":
                        trs = table.select("tr");
                        JSONObject tradeConfirm = new JSONObject();
                        rows = trs.get(0).select("td");
                        tradeConfirm.put(FundDataKey.BUY_COMFIRM_DAY, rows.get(1).text());
                        tradeConfirm.put(FundDataKey.REDEEM_COMFIRM_DAY, rows.get(3).text());
                        fundDataHtml.put(FundDataKey.TRADE_CONFIRM_DAY,
                                JSON.toJSONString(tradeConfirm));

                        break;
                    case "运作费用":
                        trs = table.select("tr");
                        JSONObject operationCost = new JSONObject();
                        rows = trs.get(0).select("td.w135");
                        operationCost.put(FundDataKey.MANAGER_RATE,
                                RateAndDayFormatConverter.convert2Format(rows.get(0).text()));
                        operationCost.put(FundDataKey.TRUSTEE_RATE,
                                RateAndDayFormatConverter.convert2Format(rows.get(1).text()));
                        operationCost.put(FundDataKey.SERVICE_RATE,
                                RateAndDayFormatConverter.convert2Format(rows.get(2).text()));
                        fundDataHtml.put(FundDataKey.OPERATION_COST,
                                JSON.toJSONString(operationCost));

                        break;
                    case "认购费率（前端）":
                        trs = table.select("tbody").select("tr");
                        JSONArray subscribeRate = new JSONArray();

                        for (Element tr : trs) {
                            rows = tr.select("td");
                            Validator.sizeNotEquals(rows.size(), 3);
                            JSONObject rate = new JSONObject();

                            for (int j = 0; j < rows.size(); j++) {
                                rate.put(keys.get(j), RateAndDayFormatConverter.convert2Format(rows.get(j).text()));
                            }
                            subscribeRate.add(rate);
                        }

                        fundDataHtml.put(FundDataKey.SUBSCRIBE_RATE,
                                JSON.toJSONString(subscribeRate));

                        break;
                    case "申购费率（前端）":
                        trs = table.select("tbody").select("tr");
                        JSONArray purchaseRate = new JSONArray();

                        for (Element tr : trs) {
                            rows = tr.select("td");
                            Validator.sizeNotEquals(rows.size(), 3);
                            JSONObject rate = new JSONObject();

                            for (int j = 0; j < rows.size(); j++) {
                                rate.put(keys.get(j), RateAndDayFormatConverter.convert2Format(rows.get(j).text()));
                            }
                            purchaseRate.add(rate);
                        }

                        fundDataHtml.put(FundDataKey.PURCHASE_RATE,
                                JSON.toJSONString(purchaseRate));

                        break;
                    case "赎回费率":
                        trs = table.select("tbody").select("tr");
                        JSONArray redeemRate = new JSONArray();

                        for (Element tr : trs) {
                            rows = tr.select("td");
                            Validator.sizeNotEquals(rows.size(), 3);
                            JSONObject rate = new JSONObject();

                            for (int j = 0; j < rows.size(); j++) {
                                rate.put(keys.get(j), RateAndDayFormatConverter.convert2Format(rows.get(j).text()));
                            }
                            redeemRate.add(rate);
                        }

                        fundDataHtml.put(FundDataKey.REDEEM_RATE,
                                JSON.toJSONString(redeemRate));

                        break;

                }
            }
        }
        dataMap.put(Url.fundDataList, fundDataListData);
        dataMap.put(Url.fundDataHtml, JSON.toJSONString(fundDataHtml));

        return JSON.toJSONString(dataMap);
    }

    @Override
    public FundData deal(String sourceData) throws Exception {
        Map<String, String> sourceDatas = JSON.parseObject(sourceData, Map.class);
        FundData fundData = new FundData();
        Method method = null;
        for (Map.Entry<String, String> entry : sourceDatas.entrySet()) {
            method = this.getClass().getDeclaredMethod(dealMethods.get(entry.getKey()), FundData.class,
                    Class.forName("java.lang.String"));
            fundData = (FundData) method.invoke(this, fundData, entry.getValue());
        }

        return fundData;
    }

    private FundData dealDataForList(FundData fundData, String sourceData) throws Exception {
        Validator.notNull(fundData, "fundData为空");
        Validator.notNull(sourceData, "sourceData为空");
        JSONArray fundSourceData = JSON.parseArray(sourceData);
        Validator.sizeNotEquals(fundSourceData.size(), 25);
        fundData.setFundCode(fundSourceData.getString(0));
        fundData.setFundName(fundSourceData.getString(1));
        fundData.setDate(StringUtils.isNotBlank(fundSourceData.getString(3)) ?
                DateUtil.strToDate(fundSourceData.getString(3),
                DateUtil.FMT_YMD1) : null);
        fundData.setUnitNet(StringUtils.isNotBlank(fundSourceData.getString(4)) ?
                Double.valueOf(fundSourceData.getString(4)) :
                null);
        fundData.setAccumulatedNet(StringUtils.isNotBlank(fundSourceData.getString(5)) ?
                Double.valueOf(fundSourceData.getString(5)) : null);
        fundData.setGrowthRate(StringUtils.isNotBlank(fundSourceData.getString(6)) ?
                Double.valueOf(fundSourceData.getString(6))
                : null);
        fundData.setEarningRate1w(StringUtils.isNotBlank(fundSourceData.getString(7)) ?
                Double.valueOf(fundSourceData.getString(7)) : null);
        fundData.setEarningRate1m(StringUtils.isNotBlank(fundSourceData.getString(8)) ?
                Double.valueOf(fundSourceData.getString(8)) : null);
        fundData.setEarningRate3m(StringUtils.isNotBlank(fundSourceData.getString(9)) ?
                Double.valueOf(fundSourceData.getString(9)) : null);
        fundData.setEarningRate6m(StringUtils.isNotBlank(fundSourceData.getString(10)) ?
                Double.valueOf(fundSourceData.getString(10)) : null);
        fundData.setEarningRate1y(StringUtils.isNotBlank(fundSourceData.getString(11)) ?
                Double.valueOf(fundSourceData.getString(11)) : null);
        fundData.setEarningRate2y(StringUtils.isNotBlank(fundSourceData.getString(12)) ?
                Double.valueOf(fundSourceData.getString(12)) : null);
        fundData.setEarningRate3y(StringUtils.isNotBlank(fundSourceData.getString(13)) ?
                Double.valueOf(fundSourceData.getString(13)) : null);
        fundData.setEarningRateThisYear(StringUtils.isNotBlank(fundSourceData.getString(14)) ?
                Double.valueOf(fundSourceData.getString(14)) : null);
        fundData.setEarningRateFromEstablish(StringUtils.isNotBlank(fundSourceData.getString(15)) ?
                Double.valueOf(fundSourceData.getString(15)) : null);

        return fundData;
    }

    private FundData dealDataForHtml(FundData fundData, String sourceData) throws Exception {
        Validator.notNull(fundData, "fundData为空");
        Validator.notNull(sourceData, "sourceData为空");

        JSONObject fundDataHtml = JSON.parseObject(sourceData);

        fundData.setFundType(FundTypeConverter.convert2Model(fundDataHtml.getString(FundDataKey.FUND_TYPE)));

        List<Rate> purchaseRate = JSONObject.parseArray(fundDataHtml.getString(FundDataKey.PURCHASE_RATE), Rate.class);
        fundData.setPurchaseRate(purchaseRate);
        List<Rate> subscribeRate = JSONObject.parseArray(fundDataHtml.getString(FundDataKey.SUBSCRIBE_RATE),
                Rate.class);
        fundData.setSubscribeRate(subscribeRate);
        List<Rate> redeemRate = JSONObject.parseArray(fundDataHtml.getString(FundDataKey.REDEEM_RATE), Rate.class);
        fundData.setRedeemRate(redeemRate);

        JSONObject operationCost = fundDataHtml.getJSONObject(FundDataKey.OPERATION_COST);
        fundData.setManagerRate(TypeConvertUtil.convert2Double(operationCost.getString(FundDataKey.MANAGER_RATE)));
        fundData.setTrusteeRate(TypeConvertUtil.convert2Double(operationCost.getString(FundDataKey.TRUSTEE_RATE)));
        fundData.setServiceRate(TypeConvertUtil.convert2Double(operationCost.getString(FundDataKey.SERVICE_RATE)));

        JSONObject purchaseAndRedeemAmount = fundDataHtml.getJSONObject(FundDataKey.PURCHASE_AND_REDEEM_AMOUNT);
        fundData.setFirstBuyAmount(TypeConvertUtil.convert2Double(purchaseAndRedeemAmount.getString(FundDataKey.FIRST_BUY_AMOUNT)));
        fundData.setPurchaseLimitOneDay(TypeConvertUtil.convert2Double(purchaseAndRedeemAmount.getString(FundDataKey.PURCHASE_LIMIT_ONT_DAY)));
        fundData.setSupplementAmount(TypeConvertUtil.convert2Double(purchaseAndRedeemAmount.getString(FundDataKey.SUPPLEMENT_AMOUNT)));
        fundData.setPositionsLimit(TypeConvertUtil.convert2Double(purchaseAndRedeemAmount.getString(FundDataKey.POSITIONS_LIMIT)));
        fundData.setMinRemainingShare(TypeConvertUtil.convert2Double(purchaseAndRedeemAmount.getString(FundDataKey.MIN_REMAINING_SHARE)));
        fundData.setMinPurchaseAmount(TypeConvertUtil.convert2Double(purchaseAndRedeemAmount.getString(FundDataKey.MIN_PURCHASE_AMOUNT)));
        fundData.setMinRedeemShare(TypeConvertUtil.convert2Double(purchaseAndRedeemAmount.getString(FundDataKey.MIN_REDEEM_SHARE)));

        JSONObject tradeConfirmDay = fundDataHtml.getJSONObject(FundDataKey.TRADE_CONFIRM_DAY);
        fundData.setBuyConfirmDay(ConfirmDayEnum.getEnumByMsg(tradeConfirmDay.getString(FundDataKey.BUY_COMFIRM_DAY)));
        fundData.setRedeemConfirmDay(ConfirmDayEnum.getEnumByMsg(tradeConfirmDay.getString(FundDataKey.REDEEM_COMFIRM_DAY)));

        JSONObject tradeStatus = fundDataHtml.getJSONObject(FundDataKey.TRADE_STATUS);
        fundData.setPurchaseStatus(PurchaseStatusEnum.getEnumByMsg(tradeStatus.getString(FundDataKey.PURCHASE_STATUS)));
        fundData.setRedeemStatus(RedeemStatusEnum.getEnumByMsg(tradeStatus.getString(FundDataKey.REDEEM_STATUS)));

        return fundData;
    }

    @Override
    public Result save(Object data) {
        FundData fundData = (FundData) data;
        if (fundData == null) {
            LOGGER.error("#爬取数据存储或更新失败#，fundData为空,errCode={}", ResultCode.PARAMETER_ILLEGAL);
            return ResultUtil.buildFailedResult(ResultCode.PARAMETER_ILLEGAL);
        }
        Result result = null;
        FundData fundDataExist = fundDataRepository.get(fundData.getFundCode());
        if (fundDataExist == null) {
            result = fundDataRepository.add(fundData);
        } else {
            result = fundDataRepository.update(fundData);
        }
        if (result != null && result.isSuccess()) {
            LOGGER.info("#基金数据爬取#存储或更新完成#，fundcode={}", fundData.getFundCode());
        } else {
            LOGGER.info("#基金数据爬取#存储或更新失败#，fundcode={}", fundData.getFundCode());
            return ResultUtil.buildFailedResult(ResultCode.FAILURE);
        }

        return ResultUtil.buildSuccessResult();
    }

    @Override
    public Result execute() {
        List<String> success = Lists.newArrayList();
        List<String> failure = Lists.newArrayList();
        String fundCode = null;
        JSONArray fundDataListArray = null;
        try {
            // 1.爬取
            CrawingRequest fundDataListCrawingRequest = new CrawingRequest();
            // 默认查过去一年的数据
            String url = StringUtils.replace(Url.fundDataList, "startDate",
                    DateUtil.getTimeDayByString(DateUtil.getStringBeforeYears(new Date(), 1), DateUtil.FMT_YMD1));
            url = StringUtils.replace(url, "endDate", DateUtil.getDay());
            fundDataListCrawingRequest.setUrl(url);
            String fundDataListData = crawingFundDataListData(fundDataListCrawingRequest);
            if (fundDataListData == null) {
                // 爬取全部失败
                return ResultUtil.buildFailedResult(ResultCode.FAILURE);
            }
            // 可能会抛异常导致fundCodeListArray或fundCodeListItem为null，下面会catch住
            fundDataListArray = (JSONArray) JSON.parse(fundDataListData);
            for (int i = 0; i < fundDataListArray.size(); i++) {
                try {
                    String str = (String) fundDataListArray.get(i);
                    // 得到的数据是这样的...
                    // [519674,银河创新成长混合,YHCXCZHH,2020-01-23,5.0136,5.0136,-2.0762,8.5054,25.7771,
                    // 48.8245,84.2084,137.2516,74.9154,133.8760,24.4440,401.36,2010-12-29,1,137.5663,
                    // 1.50%,0.15%,1,0.15%,1,187.3288]
                    // 没有引号，不是标准json，做下转换。。。
                    String[] strArray = str.split(",", 25);
                    for (int j = 0; j < strArray.length; j++) {
                        strArray[j] = "\"" + strArray[j] + "\"";
                    }
                    String s = Arrays.toString(strArray);
                    JSONArray fundDataListItem = JSONArray.parseArray(Arrays.toString(strArray));
                    fundCode = fundDataListItem.getString(0);
                    if (ignore.contains(fundCode)) {
                        success.add(fundCode);
                        continue;
                    }
                    CrawingRequest crawingRequest = new CrawingRequest();
                    CrawingRequest fundDataHtmlCrawingRequest = new CrawingRequest();
                    fundDataHtmlCrawingRequest.setUrl(Url.fundDataHtml);
                    crawingRequest.setCrawingRequests(Lists.newArrayList(fundDataHtmlCrawingRequest));
                    // 扩展字段塞基金基本信息，主要用来传递fundcode
                    Map<String, Object> extInfo = Maps.newHashMap();
                    extInfo.put(FundDataKey.FUND_DATA_LIST_DATA, JSON.toJSONString(fundDataListItem));
                    crawingRequest.setExtInfo(extInfo);
                    FundData fundData = null;
                    // 获取该基金所有档案数据的组装
                    String sourceData = crawingFundDataData(crawingRequest);
                    if (sourceData != null) {
                        fundData = deal(sourceData);
                    } else {
                        failure.add(fundCode);
                        LOGGER.info("#基金数值数据爬取#单条完成，fundcode={},已完成：{}，成功：{}，失败：{}，未完成：{}，总数据：{}", fundCode,
                                success.size() + failure.size(), success.size(), failure.size(),
                                fundDataListArray.size() - success.size() - failure.size(), fundDataListArray.size());
                        continue;
                    }
                    // 3.保存
                    Result result = save(fundData);
                    if (result != null && result.isSuccess()) {
                        success.add(fundCode);
                    } else {
                        failure.add(fundCode);
                    }
                    LOGGER.info("#基金数据爬取#单条完成，fundcode={},已完成：{}，成功：{}，失败：{}，未完成：{}，总数据：{}", fundCode,
                            success.size() + failure.size(), success.size(), failure.size(),
                            fundDataListArray.size() - success.size() - failure.size(), fundDataListArray.size());
                } catch (HttpException e) {
                    LOGGER.error("#基金数据爬取#网络异常，fundcode={},msg={}", fundCode, e.getMessage());
                    failure.add(fundCode);
                } catch (CommonException e) {
                    LOGGER.error("#基金数据爬取#通用异常，fundcode={},msg={}", fundCode, e.getMessage());
                    failure.add(fundCode);
                } catch (Exception e) {
                    // fundDataListItem为null的时候会走这里，按道理其他情况不会为null
                    LOGGER.error("#基金数据爬取#未知异常，fundcode={},msg={}", fundCode, e.getMessage());
                    failure.add(fundCode);
                }
            }
        } catch (HttpException e) {
            LOGGER.error("#基金数据爬取#网络异常，fundcode={},msg={}", fundCode, e.getMessage());
            return ResultUtil.buildFailedResult(ResultCode.FAILURE, failure);
        } catch (Exception e) {
            // fundCodeListItem为null的时候会走这里，按道理其他情况不会为null
            LOGGER.error("#基金数据爬取#未知异常，fundcode={},msg={}", fundCode, e.getMessage());
            return ResultUtil.buildFailedResult(ResultCode.FAILURE, failure);
        }
        LOGGER.info("#基金数据爬取#全部完成，成功：{}，失败：{}，总数据：{}", success.size(), failure.size(), fundDataListArray.size());
        if (CollectionUtils.isEmpty(failure)) {
            return ResultUtil.buildSuccessResult();
        } else {
            LOGGER.error("##基金数据爬取#部分失败，失败数量为{},失败记录为{},errCode={}", failure.size(), failure.toString(),
                    ResultCode.PARTIAL_FAILURE);
            return ResultUtil.buildFailedResult(ResultCode.PARTIAL_FAILURE, failure);
        }
    }
}

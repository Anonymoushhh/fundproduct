package com.sdu.fund.core.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sdu.fund.common.code.ResultCode;
import com.sdu.fund.common.exception.CommonException;
import com.sdu.fund.common.exception.HttpException;
import com.sdu.fund.common.result.Result;
import com.sdu.fund.common.util.DateUtil;
import com.sdu.fund.common.util.ResultUtil;
import com.sdu.fund.common.validator.Validator;
import com.sdu.fund.core.constants.FundManagerKey;
import com.sdu.fund.core.constants.Url;
import com.sdu.fund.core.model.bo.FundManager;
import com.sdu.fund.core.repository.FundManagerRepository;
import com.sdu.fund.core.request.CrawingRequest;
import com.sdu.fund.core.service.DataCrawlingService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: fundproduct
 * @description: 基金经理数据爬取服务
 * @author: anonymous
 * @create: 2019-12-16 22:32
 **/
public class FundManagerCrawlingService implements DataCrawlingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundManagerCrawlingService.class);

    private static Map<String, String> dealMethods = Maps.newLinkedHashMap();

    @Autowired
    private FundManagerRepository fundManagerRepository;

    static {
        dealMethods.put(Url.fundManagerList, "dealDataForList");
        dealMethods.put(Url.fundManagerHtml, "dealDataForHtml");
    }

    private String crawingFundManagerData(CrawingRequest crawingRequest) {
        Map<String, String> dataMap = Maps.newLinkedHashMap();
        String managerId = null;
        try {
            String fundManagerListData =
                (String) crawingRequest.getExtInfo().get(FundManagerKey.FUND_MANAGER_LIST_DATA);
            JSONArray fundManagerListArray = JSONArray.parseArray(fundManagerListData);
            managerId = fundManagerListArray.getString(0);

            // 爬取该基金经理对应html页面
            CrawingRequest fundManagerHtmlCrawingRequest = new CrawingRequest();
            fundManagerHtmlCrawingRequest.setUrl(StringUtils.replace(Url.fundManagerHtml, FundManagerKey.MANAGER_ID,
                managerId));
            String fundManagerHtmlCrawingData = DataCrawlingService.super.crawing(fundManagerHtmlCrawingRequest);
            //Jsoup解析html
            Document document = Jsoup.parse(fundManagerHtmlCrawingData);
            Elements introductionElements = document.getElementsByClass("right ms").get(0).select("p");
            Elements startWorkTimeElements = document.getElementsByClass("right jd ");
            String introduction = StringUtils.substringAfter(introductionElements.text(), "基金经理简介：");
            String startWorkTime = StringUtils.substringBetween(startWorkTimeElements.text(), "任职起始日期：", " 现任基金公司");
            // 设置成JSONObject对象，传递给deal方法处理
            JSONObject fundManagerHtmlJson = new JSONObject();
            fundManagerHtmlJson.put(FundManagerKey.MANAGER_INTRODUCTION, introduction);
            fundManagerHtmlJson.put(FundManagerKey.START_WORKING_TIME, startWorkTime);

            dataMap.put(Url.fundManagerList, fundManagerListData);
            dataMap.put(Url.fundManagerHtml, JSON.toJSONString(fundManagerHtmlJson));
        } catch (HttpException e) {
            // 捕获到http异常，等待重试
            LOGGER.error("数据爬取网络异常，msg={},managerId={}", e.getMessage(), managerId);
            return null;
        } catch (CommonException e) {
            LOGGER.error("数据爬取网页异常，msg={},managerId={}", e.getMessage(), managerId);
            return null;
        } catch (Exception e) {
            LOGGER.error("数据爬取未知异常，msg={},managerId={}", e.getMessage(), managerId);
            return null;
        }
        return JSON.toJSONString(dataMap);
    }

    @Override
    public FundManager deal(String sourceData) throws Exception {
        Map<String, String> sourceDatas = JSON.parseObject(sourceData, Map.class);
        FundManager fundManager = new FundManager();
        Method method = null;
        for (Map.Entry<String, String> entry : sourceDatas.entrySet()) {
            method = this.getClass().getDeclaredMethod(dealMethods.get(entry.getKey()), FundManager.class,
                Class.forName("java.lang.String"));
            fundManager = (FundManager) method.invoke(this, fundManager, entry.getValue());
        }

        return fundManager;
    }

    /*
     * @description 处理基金经理列表信息["30568687","周咏梅","80523667","华泰保兴","005645,006188,007385,007540,007767","华泰保兴尊信定开债,
     * 华泰保兴尊颐定开,华泰保兴安盈定开混合,华泰保兴安悦债券,华泰保兴尊享定开","678","9.85%","005645","华泰保兴尊信定开债","21.53亿元","9.85%"]
     * @param [fundManager, sourceData]
     * @return com.sdu.fund.core.model.bo.FundManager
     * @author anonymous
     * @date 2019/12/17
     */
    private FundManager dealDataForList(FundManager fundManager, String sourceData) throws Exception {
        Validator.notNull(fundManager, "fundManager为空");
        Validator.notNull(sourceData, "sourceData为空");

        JSONArray data = JSON.parseArray(sourceData);
        fundManager.setManagerId(data.getString(0));
        fundManager.setManagerName(data.getString(1));
        fundManager.setCompanyCode(data.getString(2));
        fundManager.setCompanyName(data.getString(3));
        /* 恶心的数据，"005645,006188,007385,007540,007767"，基金代码没有引号，JSON
            .parseArray会把005645前面的0去掉*/
        List<String> fundCodes = Arrays.asList(StringUtils.split(data.getString(4), ","));
        // 基金代码位数不为6的前面补0
        for (int t = 0; t < fundCodes.size(); t++) {
            String str = fundCodes.get(t);
            while (str.length() < 6) {
                fundCodes.set(t, "0" + str);
            }
        }
        fundManager.setFundCodes(fundCodes);
        List<String> fundNames = Arrays.asList(StringUtils.split(data.getString(5), ","));

        fundManager.setFundNames(fundNames);
        fundManager.setServiceDate(Integer.parseInt(data.getString(6)));
        fundManager.setBestReturnNow(data.getString(7));
        fundManager.setBestReturnCodeNow(data.getString(8));
        fundManager.setBestReturnNameNow(data.getString(9));
        fundManager.setAssetsScale(data.getString(10));
        fundManager.setBestReturnHistory(data.getString(11));

        return fundManager;
    }

    private FundManager dealDataForHtml(FundManager fundManager, String sourceData) throws Exception {
        Validator.notNull(fundManager, "fundManager为空");
        Validator.notNull(sourceData, "sourceData为空");

        JSONObject fundManagerData = JSON.parseObject(sourceData);
        fundManager.setStartWorkingTime(DateUtil.strToDate(fundManagerData.getString(FundManagerKey.START_WORKING_TIME), DateUtil.FMT_YMD1));
        fundManager.setManagerIntroduction(fundManagerData.getString(FundManagerKey.MANAGER_INTRODUCTION));

        return fundManager;
    }

    @Override
    public Result save(Object data) {
        FundManager fundManager = (FundManager) data;
        if (fundManager == null) {
            LOGGER.error("#爬取数据存储或更新失败#，fundManager为空,errCode={}",
                ResultCode.PARAMETER_ILLEGAL);
            return ResultUtil.buildFailedResult(ResultCode.PARAMETER_ILLEGAL);
        }
        Result result = null;
        FundManager fundManagerExist = fundManagerRepository.get(fundManager.getManagerId());
        if (fundManagerExist == null) {
            result = fundManagerRepository.add(fundManager);
        } else {
            result = fundManagerRepository.update(fundManager);
        }
        if (result != null && result.isSuccess()) {
            LOGGER.info("#基金经理数据爬取#存储或更新完成#，ManagerId={}", fundManager.getManagerId());
        } else {
            LOGGER.info("#基金经理数据爬取#存储或更新失败#，ManagerId={}", fundManager.getManagerId());
            return ResultUtil.buildFailedResult(ResultCode.FAILURE);
        }

        return ResultUtil.buildSuccessResult();
    }

    @Override
    public Result execute() {
        List<String> success = Lists.newArrayList();
        List<String> failurePages = Lists.newArrayList();

        CrawingRequest fundManagerListCrawingRequest = new CrawingRequest();
        fundManagerListCrawingRequest.setUrl(Url.fundManagerList);
        fundManagerListCrawingRequest.setPaging(true);

        int pages = 1;
        for (int i = 1; i <= pages; i++) {
            try {
                // 设置当前页数
                fundManagerListCrawingRequest.setCurPage(i);
                // 爬取单页数据
                String fundManagerListCrawingData = crawing(fundManagerListCrawingRequest);
                if (fundManagerListCrawingData == null) {
                    // 爬取该页失败
                    failurePages.add(i+"");
                }
                String fundManagerListData = StringUtils.substringAfter(fundManagerListCrawingData, "= ");
                JSONObject fundManagerListJson = JSON.parseObject(fundManagerListData);
                pages = Integer.parseInt(fundManagerListJson.getString("pages"));
                JSONArray fundManagerListDataArray = JSON.parseArray(fundManagerListJson.getString("data"));
                for (int j = 0; j < fundManagerListDataArray.size(); j++) {
                    // 1.根据每页的每个基金经理数据爬取相应详情页，组装每个基金经理全部数据
                    try {
                        JSONArray item = (JSONArray) fundManagerListDataArray.get(j);
                        CrawingRequest crawingRequest = new CrawingRequest();
                        crawingRequest.setUrl(Url.fundManagerHtml);
                        // 扩展字段塞基金基本信息，主要用来传递fundManagerList中的数据
                        Map<String, Object> extInfo = Maps.newHashMap();
                        extInfo.put(FundManagerKey.FUND_MANAGER_LIST_DATA, JSON.toJSONString(item));
                        crawingRequest.setExtInfo(extInfo);
                        String sourceData = crawingFundManagerData(crawingRequest);
                        // 2.处理
                        FundManager fundManager = deal(sourceData);
                        // 3.保存
                        Result result = save(fundManager);
                    } catch (Exception e) {
                        LOGGER.error("#基金经理数据爬取#服务异常，msg={},page={},item={}", e.getMessage(), pages,
                            JSON.toJSONString(fundManagerListDataArray.get(i)));
                        return ResultUtil.buildFailedResult(ResultCode.SERVER_EXCEPTION);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("#基金经理数据爬取#服务异常，msg={},page={}", e.getMessage(), pages);
                return ResultUtil.buildFailedResult(ResultCode.SERVER_EXCEPTION);
            }
        }
        return ResultUtil.buildSuccessResult();
    }

    public static void main(String[] args) {
        new FundManagerCrawlingService().execute();
    }
}

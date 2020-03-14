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
import com.sdu.fund.common.util.StringUtil;
import com.sdu.fund.common.validator.Validator;
import com.sdu.fund.core.constants.FundArchiveKey;
import com.sdu.fund.core.constants.Url;
import com.sdu.fund.core.model.bo.FundArchive;
import com.sdu.fund.core.repository.FundArchiveRepository;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: fundproduct
 * @description: 基金档案数据爬取服务
 * @author: anonymous
 * @create: 2019-12-05 21:40
 **/
public class FundArchiveCrawlingService implements DataCrawlingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundArchiveCrawlingService.class);

    private static Map<String, String> dealMethods = Maps.newLinkedHashMap();
    private static final Set<String> ignore = Sets.newHashSet();

    static {
        dealMethods.put(Url.fundCodeList, "dealDataForList");
        dealMethods.put(Url.fundDetail, "dealDataForDetail");
        dealMethods.put(Url.fundDetailHtml, "dealDataForHtml");
    }

    @Autowired
    private FundArchiveRepository fundArchiveRepository;

    private String crawingFundArchiveData(CrawingRequest crawingRequest) throws Exception{
        List<CrawingRequest> urls = crawingRequest.getCrawingRequests();
        CrawingRequest fundDetailCrawingRequest = urls.get(0);
        CrawingRequest fundDetailHtmlCrawingRequest = urls.get(1);
        String fundCode = null;
        // 1.爬取
        Map<String, String> dataMap = Maps.newLinkedHashMap();
            // 1.1爬取基金简要信息列表
            String fundCodeListData = (String) crawingRequest.getExtInfo().get(FundArchiveKey.FUND_CODE_LIST_DATA);
            JSONArray fundCodeListArray = JSONArray.parseArray(fundCodeListData);
            fundCode = fundCodeListArray.getString(0);
            // 1.2获取所有基金代码

            // 1.3遍历所有基金代码获取每只基金详细数据
            // 1.3.1爬取基金详情数据
            fundDetailCrawingRequest.setUrl(StringUtils.replace(Url.fundDetail, "fundcode",
                    fundCode));
            String fundDetailCrawingData = crawing(fundDetailCrawingRequest);
            // 删除爬取数据中无用部分
            String fundDetailData =
                    StringUtils.substringBefore(fundDetailCrawingData, "收益率") + StringUtils.substringAfter(fundDetailCrawingData, "现任基金经理");

            // 1.3.2爬取基金详情数据（html）
            fundDetailHtmlCrawingRequest.setUrl(StringUtils.replace(Url.fundDetailHtml, "fundcode",
                    fundCode));
            String fundDetailHtmlCrawingData = crawing(fundDetailHtmlCrawingRequest);

            //Jsoup解析html
            Document document = Jsoup.parse(fundDetailHtmlCrawingData);
            Elements rows = document.select("table[class=info w790]").get(0).select("tr");
            JSONArray fundArchive = new JSONArray();

            if (rows.size() == 0) {
                throw new CommonException("档案表格数据为空");
            } else {
                for (int j = 0; j < 10; j++) {
                    // 只获取表格前十行
                    Element row = rows.get(j);
                    fundArchive.add(row.select("td").get(0).text());
                    fundArchive.add(row.select("td").get(1).text());
                    if (j == 4) {
                        // 获取基金公司id
                        fundArchive.add(StringUtils.substring(row.select("a").attr("href"), 29, 37));
                    }
                }
            }

            rows = document.getElementsByClass("boxitem w790");
            if (rows.size() == 0) {
                throw new CommonException("网页下方数据为空");
            } else {
                for (int k = 0; k < rows.size(); k++) {
                    Element row = rows.get(k);
                    fundArchive.add(row.select("p").text());
                }
            }
            String fundDetailHtmlData = JSON.toJSONString(fundArchive);

            // 1.4组装数据
            dataMap.put(Url.fundCodeList, fundCodeListData);
            dataMap.put(Url.fundDetail, fundDetailData);
            dataMap.put(Url.fundDetailHtml, fundDetailHtmlData);

        return JSON.toJSONString(dataMap);
    }

    private String crawingFundCodeListData(CrawingRequest crawingRequest) throws Exception {
        String fundCodeListData =
                StringUtils.substringBetween(crawing(crawingRequest), "=", ";");
//        // mock用
//        String fundCodeListData = "[[\"180052\",\"YHBBZZHH\",\"银华保本增值混合\",\"保本型\",\"YINHUABAOBENZENGZHIHUNHE\"],
//        [\"040028\",\"HAYYXDQLCZQA\",\"华安月月鑫短期理财债券A\",\"理财型\"," +
//            "\"HUAANYUEYUEXINDUANQILICAIZHAIQUANA\"],[\"183001\",\"YHQQYX\",\"银华全球优选\",\"QDII\",
//            \"YINHUAQUANQIUYOUXUAN\"]]";
        JSONArray fundCodeListDataArray = JSON.parseArray(fundCodeListData);
        Iterator<Object> it = fundCodeListDataArray.iterator();
        // 过滤
        while (it.hasNext()) {
            JSONArray data = (JSONArray) it.next();
            if (StringUtils.contains(data.getString(2), "后端") || ignore.contains(data.getString(0))) {
                it.remove();
                continue;
            }
        }
        return JSON.toJSONString(fundCodeListDataArray);
    }

    /*
     * @description 处理多个数据源
     * @param [sourceData]
     * @return java.lang.Object
     * @author anonymous
     * @date 2019/12/5
     */
    @Override
    public FundArchive deal(String sourceData) throws Exception {
        Map<String, String> sourceDatas = JSON.parseObject(sourceData, Map.class);
        FundArchive fundArchive = new FundArchive();
        Method method = null;
        for (Map.Entry<String, String> entry : sourceDatas.entrySet()) {
            method = this.getClass().getDeclaredMethod(dealMethods.get(entry.getKey()), FundArchive.class,
                Class.forName("java.lang.String"));
            fundArchive = (FundArchive) method.invoke(this, fundArchive, entry.getValue());
        }

        return fundArchive;
    }

    /*
     * @description  第一步处理基金列表数据
     * ["000001","HXCZHH","华夏成长混合","混合型","HUAXIACHENGZHANGHUNHE"]
     * @param [fundArchive, sourceData]
     * @return java.util.List<com.sdu.fund.core.model.bo.FundArchive>
     * @author anonymous
     * @date 2019/12/6
     */
    private FundArchive dealDataForList(FundArchive fundArchive, String sourceData) throws Exception {
        Validator.notNull(fundArchive, "fundArchive为空");
        Validator.notNull(sourceData, "sourceData为空");

        JSONArray data = JSON.parseArray(sourceData);
        fundArchive.setFundCode(data.getString(0));
        fundArchive.setFundNamePinyin(data.getString(1));
        fundArchive.setFundNameAbbr(data.getString(2));
        fundArchive.setFundType(data.getString(3));

        return fundArchive;
    }

    /*
     * @description  第二步处理基金详细信息
     * @param [fundArchive, sourceData]
     * @return com.sdu.fund.core.model.bo.FundArchive
     * @author anonymous
     * @date 2019/12/6
     */
    private FundArchive dealDataForDetail(FundArchive fundArchive, String sourceData) throws Exception {
        Validator.notNull(fundArchive, "fundArchive为空");
        Validator.notNull(sourceData, "sourceData为空");

        List<String> stockCodes =
            JSON.parseArray(StringUtil.findValueBetweenFirstSquareBrackets(sourceData,
                FundArchiveKey.STOCK_CODES) != null ?
                StringUtil.findValueBetweenFirstSquareBrackets(sourceData,
                    FundArchiveKey.STOCK_CODES) : "[]", String.class);
        fundArchive.setStockCodes(stockCodes);

                /* 恶心的数据，var zqCodes = "0180061,0101071,1086042,0180081"，债券代码没有引号，JSON
                 .parseArray会把0101071前面的0去掉，而stockCodes源数据格式为var stockCodes=["6013181","6005191","6000361",
                 "6011661","6002761","6000301","6008871","6013281","6000161","6000001"]，因此不需要特殊处理*/
        List<String> zqCodes =
            JSON.parseArray("[" + (StringUtil.findValueBetweenFirstQuotes(sourceData,
                FundArchiveKey.ZQ_CODES) != null ?
                StringUtil.findValueBetweenFirstQuotes(sourceData,
                    FundArchiveKey.ZQ_CODES) : "") + "]", String.class);
        // 债券代码位数不为7的前面补0
        for (int t = 0; t < zqCodes.size(); t++) {
            String str = zqCodes.get(t);
            while (zqCodes.get(t).length() < 7) {
                zqCodes.set(t, "0" + str);
            }
        }
        fundArchive.setZqCodes(zqCodes);

        // 设置基金经理ids
        List<String> managerIds = Lists.newArrayList();

        JSONArray managers = JSON.parseArray(StringUtils.substringBetween(sourceData,
            FundArchiveKey.MANAGERS, " ;"));
        for (int j = 0; j < managers.size(); j++) {
            JSONObject item = managers.getJSONObject(j);
            managerIds.add(item.getString("id"));
        }
        fundArchive.setManagerIds(managerIds);

        return fundArchive;

    }

    private FundArchive dealDataForHtml(FundArchive fundArchive, String sourceData) throws CommonException, Exception {
        Validator.notNull(fundArchive, "fundArchive为空");
        Validator.notNull(sourceData, "sourceData为空");

        JSONArray fundArchiveData = JSON.parseArray(sourceData);
        fundArchive.setFundName(fundArchiveData.getString(0));
        fundArchive.setFundType(fundArchiveData.getString(3));
        fundArchive.setIssueDate(DateUtil.strToDate(fundArchiveData.getString(4), DateUtil.FMT_YMD3));
        fundArchive.setEstablishDate(DateUtil.strToDate(StringUtils.substring(fundArchiveData.getString(5), 0,
            12), DateUtil.FMT_YMD3));
        fundArchive.setCompanyName(fundArchiveData.getString(8));
        fundArchive.setCustodian(fundArchiveData.getString(9));
        fundArchive.setCompanyCode(fundArchiveData.getString(10));
        fundArchive.setPerformanceBaseline(fundArchiveData.getString(19));
        fundArchive.setTrackingTarget(fundArchiveData.getString(20));
        fundArchive.setInvestmentTarget(fundArchiveData.getString(21));
        fundArchive.setInvestmentConcept(fundArchiveData.getString(22));
        fundArchive.setInvestmentScope(fundArchiveData.getString(23));
        fundArchive.setInvestmentStrategy(fundArchiveData.getString(24));
        fundArchive.setDividendPolicy(fundArchiveData.getString(25));
        fundArchive.setRiskReturnCharacteristics(fundArchiveData.getString(27));

        return fundArchive;
    }

    @Override
    public Result save(Object data) {
        FundArchive fundArchive = (FundArchive) data;
        if (fundArchive == null) {
            LOGGER.error("#爬取数据存储或更新失败#，fundArchive为空,errCode={}",
                ResultCode.PARAMETER_ILLEGAL);
            return ResultUtil.buildFailedResult(ResultCode.PARAMETER_ILLEGAL);
        }
        Result result = null;
        FundArchive fundArchiveExist = fundArchiveRepository.get(fundArchive.getFundCode());
        if (fundArchiveExist == null) {
            result = fundArchiveRepository.add(fundArchive);
        } else {
            result = fundArchiveRepository.update(fundArchive);
        }
        if (result != null && result.isSuccess()) {
            LOGGER.info("#基金档案爬取#存储或更新完成#，fundcode={}", fundArchive.getFundCode());
        } else {
            LOGGER.info("#基金数据爬取#存储或更新失败#，fundcode={}", fundArchive.getFundCode());
            return ResultUtil.buildFailedResult(ResultCode.FAILURE);
        }

        return ResultUtil.buildSuccessResult();
    }

    @Override
    public Result execute() {
        List<String> success = Lists.newArrayList();
        List<String> failure = Lists.newArrayList();
        String fundCode = null;
        JSONArray fundCodeListArray = null;
        try {
            // 1.爬取
            CrawingRequest fundCodeListCrawingRequest = new CrawingRequest();
            fundCodeListCrawingRequest.setUrl(Url.fundCodeList);
            String fundCodeListData = crawingFundCodeListData(fundCodeListCrawingRequest);
            if (fundCodeListData == null) {
                // 爬取全部失败
                return ResultUtil.buildFailedResult(ResultCode.FAILURE);
            }
            // 可能会抛异常导致fundCodeListArray或fundCodeListItem为null，下面会catch住
            fundCodeListArray = JSONArray.parseArray(fundCodeListData);
            for (int i = 0; i < fundCodeListArray.size(); i++) {
                try {
                    JSONArray fundCodeListItem = (JSONArray) fundCodeListArray.get(i);
                    fundCode = fundCodeListItem.getString(0);
                    CrawingRequest crawingRequest = new CrawingRequest();
                    CrawingRequest fundDetailCrawingRequest = new CrawingRequest();
                    fundDetailCrawingRequest.setUrl(Url.fundDetail);
                    CrawingRequest fundDetailHtmlCrawingRequest = new CrawingRequest();
                    fundDetailHtmlCrawingRequest.setUrl(Url.fundDetailHtml);
                    crawingRequest.setCrawingRequests(Lists.newArrayList(fundDetailCrawingRequest,
                        fundDetailHtmlCrawingRequest));
                    // 扩展字段塞基金基本信息，主要用来传递fundcode
                    Map<String, Object> extInfo = Maps.newHashMap();
                    extInfo.put(FundArchiveKey.FUND_CODE_LIST_DATA, JSON.toJSONString(fundCodeListItem));
                    crawingRequest.setExtInfo(extInfo);

                    // 获取该基金所有档案数据的组装
                    String sourceData = crawingFundArchiveData(crawingRequest);
                    FundArchive fundArchive = null;
                    if (sourceData != null) {
                        fundArchive = deal(sourceData);
                    } else {
                        failure.add(fundCode);
                        LOGGER.info("#基金数据爬取#单条完成，fundcode={},已完成：{}，成功：{}，失败：{}，未完成：{}，总数据：{}", fundCode,
                            success.size() + failure.size(), success.size(), failure.size(),
                            fundCodeListArray.size() - success.size() - failure.size(), fundCodeListArray.size());
                        continue;
                    }
                    // 3.保存
                    Result result = save(fundArchive);
                    if (result != null && result.isSuccess()) {
                        success.add(fundCode);
                    } else {
                        failure.add(fundCode);
                    }
                    LOGGER.info("#基金数据爬取#单条完成，fundcode={},已完成：{}，成功：{}，失败：{}，未完成：{}，总数据：{}", fundCode,
                        success.size() + failure.size(), success.size(), failure.size(),
                        fundCodeListArray.size() - success.size() - failure.size(), fundCodeListArray.size());
                } catch (HttpException e) {
                    LOGGER.error("#基金数据爬取#网络异常，fundcode={},msg={}", fundCode, e.getMessage());
                    failure.add(fundCode);
                } catch (CommonException e) {
                    LOGGER.error("#基金数据爬取#通用异常，fundcode={},msg={}", fundCode, e.getMessage());
                    failure.add(fundCode);
                } catch (Exception e) {
                    // fundCodeListItem为null的时候会走这里，按道理其他情况不会为null
                    LOGGER.error("#基金数据爬取#未知异常，fundcode={},msg={}", fundCode, e.getMessage());
                    failure.add(fundCode);
                }
            }
        } catch (HttpException e) {
            LOGGER.error("#基金数据爬取#网络异常，fundcode={},msg={}", fundCode, e.getMessage());
            return ResultUtil.buildFailedResult(ResultCode.FAILURE, failure);
        } catch (Exception e) {
            LOGGER.error("#基金数据爬取#未知异常，fundcode={},msg={}", fundCode, e.getMessage());
            return ResultUtil.buildFailedResult(ResultCode.FAILURE, failure);
        }
        LOGGER.info("#基金数据爬取#全部完成，成功：{}，失败：{}，总数据：{}", success.size(), failure.size(), fundCodeListArray.size());
        if (CollectionUtils.isEmpty(failure)) {
            return ResultUtil.buildSuccessResult();
        }else{
            LOGGER.error("##基金数据爬取#部分失败，失败数量为{},失败记录为{},errCode={}", failure.size(), failure.toString(),
                    ResultCode.PARTIAL_FAILURE);
            return ResultUtil.buildFailedResult(ResultCode.PARTIAL_FAILURE, failure);
        }
    }

    public static void main(String[] args) {
        new FundArchiveCrawlingService().execute();
    }
}

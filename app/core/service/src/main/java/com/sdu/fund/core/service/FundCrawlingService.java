package com.sdu.fund.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sdu.fund.common.exception.CommonException;
import com.sdu.fund.common.exception.HttpException;
import com.sdu.fund.core.constants.FundArchiveKey;
import com.sdu.fund.core.constants.Url;
import com.sdu.fund.core.request.CrawingRequest;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @program: fundproduct
 * @description: 基金数据爬取服务
 * @author: anonymous
 * @create: 2019-12-09 18:18
 **/
public abstract class FundCrawlingService implements DataCrawlingService {

    private static final Logger      LOGGER = LoggerFactory.getLogger(FundCrawlingService.class);
    private static final Set<String> ignore = Sets.newHashSet();

    protected String crawingFundArchiveData(CrawingRequest crawingRequest) {
        List<CrawingRequest> urls = crawingRequest.getCrawingRequests();
        CrawingRequest fundDetailCrawingRequest = urls.get(0);
        CrawingRequest fundDetailHtmlCrawingRequest = urls.get(1);
        String fundCode = null;
        // 1.爬取
        Map<String, String> dataMap = Maps.newLinkedHashMap();
        try {
            // 1.1爬取基金简要信息列表
            String fundCodeListData = (String) crawingRequest.getExtInfo().get(FundArchiveKey.FUND_CODE_LIST_DATA);
            JSONArray fundCodeListArray = JSONArray.parseArray(fundCodeListData);
            fundCode = fundCodeListArray.getString(0);
            // 1.2获取所有基金代码

            // 1.3遍历所有基金代码获取每只基金详细数据
            // 1.3.1爬取基金详情数据
            fundDetailCrawingRequest.setUrl(StringUtils.replace(Url.fundDetail, "fundcode",
                fundCode));
            String fundDetailCrawingData = DataCrawlingService.super.crawing(fundDetailCrawingRequest);
            // 删除爬取数据中无用部分
            String fundDetailData =
                StringUtils.substringBefore(fundDetailCrawingData, "收益率") + StringUtils.substringAfter(fundDetailCrawingData, "现任基金经理");

            // 1.3.2爬取基金详情数据（html）
            fundDetailHtmlCrawingRequest.setUrl(StringUtils.replace(Url.fundDetailHtml, "fundcode",
                fundCode));
            String fundDetailHtmlCrawingData = DataCrawlingService.super.crawing(fundDetailHtmlCrawingRequest);

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
                        fundArchive.add(StringUtils.substring(row.select("a").attr("href"), 34, 42));
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
        } catch (HttpException e) {
            // 捕获到http异常，等待重试
            LOGGER.error("数据爬取网络异常，msg={},fundcode={}", e.getMessage(), fundCode);
            return null;
        } catch (CommonException e) {
            LOGGER.error("数据爬取网页异常，msg={},fundcode={}", e.getMessage(), fundCode);
            return null;
        } catch (Exception e) {
            LOGGER.error("数据爬取未知异常，msg={},fundcode={}", e.getMessage(), fundCode);
            return null;
        }

        return JSON.toJSONString(dataMap);
    }

    protected String crawingFundCodeListData() throws HttpException, Exception {
        CrawingRequest fundCodeListCrawingRequest = new CrawingRequest();
        fundCodeListCrawingRequest.setUrl(Url.fundCodeList);
        String fundCodeListData =
            StringUtils.substringBetween(DataCrawlingService.super.crawing(fundCodeListCrawingRequest), "=", ";");
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
}

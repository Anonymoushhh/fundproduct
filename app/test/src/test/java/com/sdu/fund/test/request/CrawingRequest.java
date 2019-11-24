package com.sdu.fund.test.request;

import java.util.Map;

/**
 * @program: fundproduct
 * @description: 数据爬取请求
 * @author: anonymous
 * @create: 2019-11-23 20:36
 **/
public class CrawingRequest {

    /* 爬取地址*/
    private String url;

    /* 是否分页*/
    private Boolean paging;

    /* 扩展字段*/
    private Map<String,Object> extInfo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getPaging() {
        return paging;
    }

    public void setPaging(Boolean paging) {
        this.paging = paging;
    }

    public int getCurPage() {
        return (Integer)extInfo.get("curPage");
    }

    public void setCurPage(int curPage) {
        extInfo.put("curPage",curPage);
    }

}

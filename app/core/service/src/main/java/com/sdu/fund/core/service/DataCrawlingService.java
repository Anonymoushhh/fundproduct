package com.sdu.fund.core.service;

import com.sdu.fund.common.enums.RequestMethodEnum;
import com.sdu.fund.common.result.Result;
import com.sdu.fund.common.util.HttpUtil;
import com.sdu.fund.core.request.CrawingRequest;

/**
 * @program: fundproduct
 * @description: 数据爬取公共服务
 * @author: anonymous
 * @create: 2019-11-23 20:22
 **/
public interface DataCrawlingService {

    /*
     * @description 数据爬取 
     * @param [crawingRequest] 
     * @return java.lang.String 
     * @author anonymous 
     * @date 2019/11/27 
     */
    default String crawing(CrawingRequest crawingRequest){
        String url = crawingRequest.getUrl();
        String response = HttpUtil.send(url, RequestMethodEnum.GET);
        return response;
    };

    /*
     * @description 数据处理 
     * @param [data] 
     * @return java.lang.Object 
     * @author anonymous 
     * @date 2019/11/27 
     */
    public Object deal(String sourceData);

    /*
     * @description 数据保存 
     * @param [data] 
     * @return com.sdu.fund.common.result.Result 
     * @author anonymous 
     * @date 2019/11/28 
     */
    public Result save(Object data);
}

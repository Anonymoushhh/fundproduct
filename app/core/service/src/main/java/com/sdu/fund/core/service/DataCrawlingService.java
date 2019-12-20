package com.sdu.fund.core.service;

import com.sdu.fund.common.enums.RequestMethodEnum;
import com.sdu.fund.common.exception.CommonException;
import com.sdu.fund.common.exception.HttpException;
import com.sdu.fund.common.result.Result;
import com.sdu.fund.common.util.HttpUtil;
import com.sdu.fund.common.validator.Validator;
import com.sdu.fund.core.request.CrawingRequest;
import org.apache.commons.lang3.StringUtils;

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
    default String crawing(CrawingRequest crawingRequest) throws HttpException, CommonException {
        Validator.notNull(crawingRequest,"crawingRequest为null");
        String url = crawingRequest.getUrl();
        // 分页的话url中页符用page占位
        if(crawingRequest.getPaging()&&crawingRequest.getCurPage()>0){
            url = StringUtils.replace(url, "page", (crawingRequest.getCurPage()+""));
        }
        String response = HttpUtil.send(url, crawingRequest.getRequestMethodEnum() != null ?
            crawingRequest.getRequestMethodEnum() : RequestMethodEnum.GET);
        return response;
    }

    /*
     * @description 数据处理
     * @param [data]
     * @return java.lang.Object
     * @author anonymous
     * @date 2019/11/27
     */
    Object deal(String sourceData) throws Exception;

    /*
     * @description 数据保存
     * @param [data]
     * @return com.sdu.fund.common.result.Result
     * @author anonymous
     * @date 2019/11/28
     */
    public Result save(Object data);

    public Result execute();

}

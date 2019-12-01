/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sdu.fund.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.common.result.Result;
import com.sdu.fund.core.request.CrawingRequest;
import com.sdu.fund.core.service.DataCrawlingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qilong.zql
 * @since 2.5.8
 */
@RestController
public class SampleRestController {

    @SofaReference
    private DataCrawlingService fundCompanyDataCrawlingService;

    @RequestMapping("/test")
    public Result test() {
        CrawingRequest crawingRequest = new CrawingRequest();
        crawingRequest.setUrl("http://fund.eastmoney.com/Data/FundRankScale.aspx?_=1574132995835");
        return fundCompanyDataCrawlingService.save(fundCompanyDataCrawlingService.deal(fundCompanyDataCrawlingService.crawing(crawingRequest)));
    }


//    public void setFundCompanyDataCrawlingService(FundCompanyDataCrawlingServiceImpl fundCompanyDataCrawlingService) {
//        this.fundCompanyDataCrawlingService = fundCompanyDataCrawlingService;
//    }
}
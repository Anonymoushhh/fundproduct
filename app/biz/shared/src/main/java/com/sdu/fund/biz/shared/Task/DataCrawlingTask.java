package com.sdu.fund.biz.shared.Task;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.core.service.DataCrawlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2019-11-30 21:54
 **/
@Component
public class DataCrawlingTask implements Task{

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCrawlingTask.class);

    @SofaReference(uniqueId="fundArchiveCrawlingService")
    private DataCrawlingService fundArchiveCrawlingService;

    @SofaReference(uniqueId="fundCompanyCrawlingService")
    private DataCrawlingService fundCompanyCrawlingService;

    @SofaReference(uniqueId="fundDataCrawlingService")
    private DataCrawlingService fundDataCrawlingService;

    @SofaReference(uniqueId="fundManagerCrawlingService")
    private DataCrawlingService fundManagerCrawlingService;

    @Override
    @Scheduled(cron = "0 0 22 * * ?")
    public void execute() {
        LOGGER.info("execute task start: " + new Date());
        //fundArchiveCrawlingService.execute();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                fundCompanyCrawlingService.execute();
            }
        });
//        cachedThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                fundManagerCrawlingService.execute();
//            }
//        });
        // fundDataCrawlingService.execute();
        //fundManagerCrawlingService.execute();
        LOGGER.info("execute task end: " + new Date());
    }
}

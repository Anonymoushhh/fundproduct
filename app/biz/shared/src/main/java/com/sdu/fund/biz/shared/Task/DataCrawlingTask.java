package com.sdu.fund.biz.shared.Task;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.core.service.DataCrawlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2019-11-30 21:54
 **/
@Component
public class DataCrawlingTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCrawlingTask.class);

    public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @SofaReference(uniqueId="fundArchiveCrawlingService")
    private DataCrawlingService fundArchiveCrawlingService;

    @SofaReference(uniqueId="fundCompanyCrawlingService")
    private DataCrawlingService fundCompanyCrawlingService;

    @SofaReference(uniqueId="fundDataCrawlingService")
    private DataCrawlingService fundDataCrawlingService;

    @SofaReference(uniqueId="fundManagerCrawlingService")
    private DataCrawlingService fundManagerCrawlingService;

    @Scheduled(cron = "0 30 11 * * ?")
    public void fundDataCrawlingTaskExecute() {
        LOGGER.info("基金数值数据爬取定时任务触发");
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                 fundDataCrawlingService.execute();
            }
        });
    }
    @Scheduled(cron = "0 0 1 * * ?")
    public void execute() {
        LOGGER.info("基金数据爬取定时任务触发");
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                fundCompanyCrawlingService.execute();
            }
        });
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                fundManagerCrawlingService.execute();
            }
        });
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                fundArchiveCrawlingService.execute();
            }
        });
    }
}

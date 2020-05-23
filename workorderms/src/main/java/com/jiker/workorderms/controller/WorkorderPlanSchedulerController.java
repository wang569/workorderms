package com.jiker.workorderms.controller;

import com.jiker.workorderms.service.WorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 20:51
 * @Description TODO
 */
@Configuration
@EnableScheduling
@Slf4j
public class WorkorderPlanSchedulerController {

    @Autowired
    private WorkOrderService workOrderService;

    /**
     * 2.每天凌晨0点启动工单计划调度器
     *
     * @throws ParseException
     */
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 3 * * * ?")
    private void workOrderScheduler() throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        log.info(LocalDateTime.now().format(formatter) + ":工单计划调度器启动");
        workOrderService.produceWorkOrder();
        log.info(LocalDateTime.now().format(formatter) + ":工单计划调度器结束");
    }
}

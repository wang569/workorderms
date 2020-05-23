package com.jiker.workorderms.controller;

import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.service.WorkOrderPlanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/20 20:12
 * @Description TODO
 */
@RestController
public class WorkOrderPlanController {

    @Autowired
    private WorkOrderPlanService workOrderPlanService;

    @RequestMapping("/createWorkOrderPlan")
    public String CreateWorkOrderPlan(@RequestBody WorkOrderPlan workOrderPlan) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String resDate = LocalDateTime.now().format(formatter);

        //用于接收SQL执行返回码
        int result = 0;

        //1、判断前端传过来的数据是否满足要求
        //需要处理字段Number、Name、Content、Cycle、Role、Executor
        if (!StringUtils.isNoneBlank(workOrderPlan.getName(),
                workOrderPlan.getContent(),workOrderPlan.getCycle(),workOrderPlan.getRole(),
                workOrderPlan.getExecutor())
                || Objects.isNull(workOrderPlan.getNumber())
                || Objects.isNull(workOrderPlan.getPlan_start_time())
                || Objects.isNull(workOrderPlan.getPlan_end_time())
                || (workOrderPlan.getPlan_start_time().isAfter(workOrderPlan.getPlan_end_time()))) {
            return "参数不能为空或开始时间大于等于结束时间";
        }

        result = workOrderPlanService.CreateWorkOrderPlan(workOrderPlan);
        //根据返回结果值,完成工单计划成功或失败逻辑处理
        return (result > 0) ? (resDate + ":创建工单计划成功") : (resDate + ":创建工单计划失败");
    }
}

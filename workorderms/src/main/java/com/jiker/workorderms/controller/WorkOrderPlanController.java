package com.jiker.workorderms.controller;

import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.service.WorkOrderPlanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

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
    public String createWorkOrderPlan(@RequestBody WorkOrderPlan workOrderPlan) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String resDate = LocalDateTime.now().format(formatter);

        //用于接收SQL执行返回码
        int result = 0;

        //1、判断前端传过来的数据是否满足要求
        //需要处理字段Number、Name、Content、Cycle、Role、Executor
        if (!StringUtils.isNoneBlank(workOrderPlan.getName(),
                workOrderPlan.getContent(), workOrderPlan.getCycle(), workOrderPlan.getRole(),
                workOrderPlan.getExecutor())
                || Objects.isNull(workOrderPlan.getNumber())
                || Objects.isNull(workOrderPlan.getPlan_start_time())
                || Objects.isNull(workOrderPlan.getPlan_end_time())
                || (workOrderPlan.getPlan_start_time().isAfter(workOrderPlan.getPlan_end_time()))) {
            return "参数不能为空或开始时间大于等于结束时间";
        }

        result = workOrderPlanService.createWorkOrderPlan(workOrderPlan);
        //根据返回结果值,完成工单计划成功或失败逻辑处理
        return (result > 0) ? (resDate + ":创建工单计划成功") : (resDate + ":创建工单计划失败");
    }


    @RequestMapping("/editWorkOrderPlan")
    public String editWorkOrderPlan(@RequestBody WorkOrderPlan workOrderPlan) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        //1. 判断非空
        if (Objects.isNull(workOrderPlan.getId()) ||
                Objects.isNull(workOrderPlan.getContent()) ||
                StringUtils.isBlank(workOrderPlan.getExecutor()) ||
                Objects.isNull(workOrderPlan.getPlan_start_time()) ||
                Objects.isNull(workOrderPlan.getPlan_end_time()) ||
                workOrderPlan.getPlan_start_time().isAfter(workOrderPlan.getPlan_end_time())) {
            return "参数不能为空或开始时间大于等于结束时间";
        }
        boolean flag = workOrderPlanService.editWorkOrderPlan(workOrderPlan);
        String resDate = LocalDateTime.now().format(formatter);
        return flag ? (resDate + ":编辑工单计划成功") : (resDate + ":编辑工单计划失败");
    }

    @RequestMapping(value = "/deleteWorkOrderPlan", method = RequestMethod.DELETE)
    public String deleteWorkOrderPlan(@RequestBody WorkOrderPlan workOrderPlan) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        Scanner scanner = new Scanner(System.in);
        String input = null;
        System.out.println("请问确认要删除该条工单计划么？确定Y,取消N");
        while(scanner.hasNextLine()) {
            input = scanner.next();
            if (StringUtils.equalsAnyIgnoreCase(input,"y","n")) {
                break;
            } else {
                System.out.println("您输入的格式不正确，请问确认要删除该条工单计划么？确定Y,取消N");
            }
        }
        scanner.close();
        if(StringUtils.equalsIgnoreCase(input, "n")) {
            return "已取消删除请求";
        }else{
            if (Objects.isNull(workOrderPlan.getId())) {
                return "ID不能为空";
            }
            boolean flag = workOrderPlanService.deleteWorkOrderPlan(workOrderPlan);
            String resDate = LocalDateTime.now().format(formatter);
            return flag ? (resDate + ":删除工单计划成功") : (resDate + ":删除工单计划失败");
        }
    }
}

package com.jiker.workorderms.controller;

import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.service.CountWorkOrderPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 21:09
 * @Description TODO
 */
@RestController
public class CountWorkOrderPlanController {

    @Autowired
    private CountWorkOrderPlanService countWorkOrderPlanService;

    /**
     * 统计某个时间段内的工单计划数
     *
     * @param workOrderPlan
     * @return
     */
    @RequestMapping("/queryWorkOrderPlanForTimeRegion")
    public List<WorkOrderPlan> queryWorkOrderPlanForTimeRegion(@RequestBody WorkOrderPlan workOrderPlan) {
        List<WorkOrderPlan> workOrderPlanList = new ArrayList<WorkOrderPlan>();
        //1 校验时间参数是否为空，且开始时间不能大于结束时间
        if ((workOrderPlan.getPlan_start_time() == null) || (workOrderPlan.getPlan_end_time() == null)
                || (workOrderPlan.getPlan_start_time().isAfter(workOrderPlan.getPlan_end_time()))) {
            return null;
        } else {
            //2 调用业务逻辑层queryWorkOrderPlanForTimeRegion方法查询数据
            workOrderPlanList = countWorkOrderPlanService.queryWorkOrderPlanForTimeRegion(workOrderPlan);
        }
        return workOrderPlanList;
    }

    /**
     * 统计已生成工单的工单计划数
     * @return
     */
    @RequestMapping("/queryWorkOrderPlanComplete")
    public List<WorkOrderPlan> queryWorkOrderPlanComplete(){
        return countWorkOrderPlanService.queryWorkOrderPlanComplete();
    }

    /**
     * 统计未生成工单的工单计划数
     * @return
     */
    @RequestMapping("/queryWorkOrderPlanNotComplete")
    public List<WorkOrderPlan> queryWorkOrderPlanNotComplete(){
        return countWorkOrderPlanService.queryWorkOrderPlanNotComplete();
    }

    /**
     * 统计提交的工单计划数
     * @return
     */
    @RequestMapping("/queryWorkOrderPlanAll")
    public List<WorkOrderPlan> queryWorkOrderPlanAll(){
        return countWorkOrderPlanService.queryWorkOrderPlanAll();
    }
}

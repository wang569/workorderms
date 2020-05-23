package com.jiker.workorderms.dao;

import com.jiker.workorderms.bean.WorkOrderPlan;

import java.util.List;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 21:10
 * @Description TODO
 */
public interface ICountWorkOrderPlanDao {

    /**
     * 根据时间区间查询工单计划表
     * @param workOrderPlan
     * @return
     */
    List<WorkOrderPlan> queryWorkOrderPlanForTimeRegion(WorkOrderPlan workOrderPlan);

    /**
     * 根据状态查询工单列表
     * @param workOrderPlan
     * @return
     */
    List<WorkOrderPlan> queryWorkOrderPlanByStatus(WorkOrderPlan workOrderPlan);

    /**
     * 查询工单列表
     * @return
     */
    List<WorkOrderPlan> queryWorkOrderPlan();
}

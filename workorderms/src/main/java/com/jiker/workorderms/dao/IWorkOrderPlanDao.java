package com.jiker.workorderms.dao;

import com.jiker.workorderms.bean.WorkOrderPlan;

import java.util.List;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/20 20:02
 * @Description TODO
 */
public interface IWorkOrderPlanDao {

    /**
     * 向工单计划表中插入数据
     * @param workOrderPlan
     * @return
     */
    int insert(WorkOrderPlan workOrderPlan);

    /**
     * 根据SQL查询一个字段值
     * @param sql
     * @param field
     * @return
     */
    String queryField(String sql,String field);

    /**
     * 批量更新工单计划状态
     * @param workOrderPlanList
     * @return
     */
    boolean batchUpdateStatus(List<WorkOrderPlan> workOrderPlanList);

    /**
     * 更新工单计划
     * @param workOrderPlan
     * @return
     */
    int update(WorkOrderPlan workOrderPlan);

    /**
     * 删除工单计划
     * @param workOrderPlan
     * @return
     */
    int delete(WorkOrderPlan workOrderPlan);
}

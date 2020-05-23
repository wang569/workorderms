package com.jiker.workorderms.dao;

import com.jiker.workorderms.bean.WorkOrder;
import com.jiker.workorderms.bean.WorkOrderPlan;

import java.util.List;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 20:22
 * @Description TODO
 */
public interface IWorkOrderDao {
    /**
     * 根据SQL查询workOrderPlan，返回结果封装在List中
     *
     * @param sql
     * @return workOrdersPlanList
     */
    List<WorkOrderPlan> queryWorkOrder(String sql);

    /**
     * 向数据库批量提交工单数据
     *
     * @param workOrderList
     * @return result
     */
     boolean insertWorkOrder(List<WorkOrder> workOrderList);
}

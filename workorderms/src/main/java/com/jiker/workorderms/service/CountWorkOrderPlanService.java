package com.jiker.workorderms.service;

import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.dao.ICountWorkOrderPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 21:10
 * @Description TODO
 */
@Service
public class CountWorkOrderPlanService {

    @Autowired
    private ICountWorkOrderPlanDao countWorkOrderPlanDao;

    /**
     * 统计某个时间段内的工单计划数
     *
     * @param workOrderPlan
     * @return List<WorkOrderPlan>
     */
    public List<WorkOrderPlan> queryWorkOrderPlanForTimeRegion(WorkOrderPlan workOrderPlan) {
        return countWorkOrderPlanDao.queryWorkOrderPlanForTimeRegion(workOrderPlan);
    }

    /**
     * 统计已生成工单的工单计划数
     * @return List<WorkOrderPlan>
     */
    public List<WorkOrderPlan> queryWorkOrderPlanComplete(){
        WorkOrderPlan workOrderPlan = new WorkOrderPlan();
        workOrderPlan.setStatus("1");
        return  countWorkOrderPlanDao.queryWorkOrderPlanByStatus(workOrderPlan);
    }
    /**
     * 统计未生成工单的工单计划数
     * @return List<WorkOrderPlan>
     */
    public List<WorkOrderPlan> queryWorkOrderPlanNotComplete() {
        WorkOrderPlan workOrderPlan = new WorkOrderPlan();
        workOrderPlan.setStatus("0");
        return  countWorkOrderPlanDao.queryWorkOrderPlanByStatus(workOrderPlan);
    }


    /**
     * 统计提交的所有工单计划数
     * @return List<WorkOrderPlan>
     */
    public List<WorkOrderPlan> queryWorkOrderPlanAll() {
        return countWorkOrderPlanDao.queryWorkOrderPlan();
    }
}

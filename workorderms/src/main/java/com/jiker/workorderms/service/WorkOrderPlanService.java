package com.jiker.workorderms.service;

import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.dao.IWorkOrderPlanDao;
import com.jiker.workorderms.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/20 20:07
 * @Description TODO
 */
@Service
public class WorkOrderPlanService {

    @Autowired
    private IWorkOrderPlanDao workOrderPlanDao;

    @Autowired
    private Utils utils;

    /**
     * 创建工单业务逻辑处理
     *
     * @param workOrderPlan
     * @return
     */
    public int CreateWorkOrderPlan(WorkOrderPlan workOrderPlan) {

        //1 根据科室代码生成工单号
        String number = utils.createWorkOrderNumber(workOrderPlan.getNumber());
        //2 根据周期生成对应的cron
        String cycle = utils.analysisCycle(workOrderPlan.getCycle());
        //3 调用Dao层入库
        workOrderPlan.setNumber(number);
        workOrderPlan.setCycle(cycle);
        int result = workOrderPlanDao.insert(workOrderPlan);
        return result;
    }
}

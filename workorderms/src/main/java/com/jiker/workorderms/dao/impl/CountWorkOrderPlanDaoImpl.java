package com.jiker.workorderms.dao.impl;

import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.dao.ICountWorkOrderPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 21:11
 * @Description TODO
 */
@Repository
public class CountWorkOrderPlanDaoImpl implements ICountWorkOrderPlanDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<WorkOrderPlan> queryWorkOrderPlanForTimeRegion(WorkOrderPlan workOrderPlan) {
        String sql = "select * from  workorderms.workorder_plan m where m.plan_start_time >= ? and m.plan_end_time <=?";
        List<WorkOrderPlan> workOrdersPlanList = jdbcTemplate.
                query(sql, new Object[]{workOrderPlan.getPlan_start_time(), workOrderPlan.getPlan_end_time()},
                        new BeanPropertyRowMapper(WorkOrderPlan.class));
        return workOrdersPlanList;
    }

    @Override
    public List<WorkOrderPlan> queryWorkOrderPlanByStatus(WorkOrderPlan workOrderPlan) {
        String sql = "select * from  workorderms.workorder_plan where status=?";
        List<WorkOrderPlan> workOrdersPlanList = jdbcTemplate.
                query(sql, new Object[]{workOrderPlan.getStatus()},
                        new BeanPropertyRowMapper(WorkOrderPlan.class));
        return workOrdersPlanList;
    }

    @Override
    public List<WorkOrderPlan> queryWorkOrderPlan() {
        String sql = "select * from  workorderms.workorder_plan";
        List<WorkOrderPlan> workOrdersPlanList = jdbcTemplate.
                query(sql, new BeanPropertyRowMapper(WorkOrderPlan.class));
        return workOrdersPlanList;
    }
}

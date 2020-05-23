package com.jiker.workorderms.dao.impl;

import com.jiker.workorderms.bean.WorkOrder;
import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.dao.IWorkOrderPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/20 20:03
 * @Description TODO
 */
@Repository
public class WorkOrderPlanDaoImpl implements IWorkOrderPlanDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int insert(WorkOrderPlan workOrderPlan) {
        int result = jdbcTemplate.update("INSERT INTO workorder_plan(number,name,content,cycle," +
                        "plan_start_time,plan_end_time,role,executor,status) values (?,?,?,?,?,?,?,?,?)",
                workOrderPlan.getNumber(), workOrderPlan.getName(), workOrderPlan.getContent(), workOrderPlan.getCycle(),
                workOrderPlan.getPlan_start_time(), workOrderPlan.getPlan_end_time(), workOrderPlan.getRole(),
                workOrderPlan.getExecutor(), Objects.isNull(workOrderPlan.getStatus())?"0":workOrderPlan.getStatus());
        return result;
    }

    @Override
    public String queryField(String sql, String field) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        String result = null;
        while (sqlRowSet.next()) {
            if (sqlRowSet.getString(field) != null) {
                result = sqlRowSet.getString(field);
            }
        }
        return result;
    }

    @Override
    public boolean batchUpdateStatus(List<WorkOrderPlan> workOrderPlanList) {
        String sql = "update workorderms.workorder_plan set status = ? where id = ?";
        boolean result = true;
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    WorkOrderPlan workOrderPlan = workOrderPlanList.get(i);
                    if (Objects.nonNull(workOrderPlan)) {
                        ps.setString(1, "1");
                        ps.setInt(2, workOrderPlan.getId());
                    }
                }

                @Override
                public int getBatchSize() {
                    return workOrderPlanList.size();
                }
            });
        }catch (DataAccessException e){
            result = false;
        }
        return result;
    }
}

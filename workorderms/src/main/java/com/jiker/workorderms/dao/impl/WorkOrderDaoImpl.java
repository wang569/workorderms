package com.jiker.workorderms.dao.impl;

import com.jiker.workorderms.bean.WorkOrder;
import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.dao.IWorkOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 20:23
 * @Description TODO
 */
@Repository
public class WorkOrderDaoImpl implements IWorkOrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<WorkOrderPlan> queryWorkOrder(String sql) {
        //连接数据库处理....
        List<WorkOrderPlan> workOrdersPlanList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WorkOrderPlan.class));
        return workOrdersPlanList;
    }

    @Override
    public boolean insertWorkOrder(List<WorkOrder> workOrderList) {
        String sql = "insert into workorderms.workorder(number,name,content,start_time,end_time,role,executor) values(?,?,?,?,?,?,?)";
        boolean result = true;
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    WorkOrder workOrder = workOrderList.get(i);
                    if (Objects.nonNull(workOrder)) {
                        ps.setString(1, workOrder.getNumber());
                        ps.setString(2, workOrder.getName());
                        ps.setString(3, workOrder.getContent());
                        ps.setObject(4, workOrder.getStart_time());
                        ps.setObject(5, workOrder.getEnd_time());
                        ps.setString(6, workOrder.getRole());
                        ps.setString(7, workOrder.getExecutor());
                    }
                }

                //返回更新的结果集条数
                @Override
                public int getBatchSize() {
                    return workOrderList.size();
                }
            });
        }catch (DataAccessException e){
            result = false;
        }
        return result;
    }
}

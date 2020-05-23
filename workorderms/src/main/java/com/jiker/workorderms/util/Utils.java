package com.jiker.workorderms.util;

import com.jiker.workorderms.bean.WorkOrder;
import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.constant.DateType;
import com.jiker.workorderms.dao.IWorkOrderPlanDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/20 20:01
 * @Description TODO
 */
@Component
@Slf4j
public class Utils {
    /**
     * 调度周期-按小时
     */
    private final String CYCLE_BY_HOUR = "时";

    /**
     * 调度周期-按日
     */
    private final String CYCLE_BY_DAY = "日";


    @Autowired
    private IWorkOrderPlanDao iWorkOrderPlanDao;

    /**
     * 生成工单编号
     *
     * @param department
     * @return newNumber
     */
    public String createWorkOrderNumber(String department) {

        String newNumber = "";
        //获取当前的8位日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String time = LocalDateTime.now().format(formatter);
        //获取6位序列号
        String sql = "select max(number) as number from workorderms.workorder_plan";
        //获取当前工单表中最大的工单号
        String number = iWorkOrderPlanDao.queryField(sql, "number");
        log.info("number = " + number);
        if (Objects.isNull(number)) {
            number = "000001";
        } else {
            number = number.substring(10);
            number = String.format("%0" + 6 + "d", Integer.parseInt(number) + 1);
        }
        newNumber = department + time + number;
        return newNumber;
    }

    /**
     * 解析执行周期为crontab格式
     *
     * @param cycle
     * @return cycle
     */
    public String analysisCycle(String cycle) {
        if (CYCLE_BY_HOUR.equals(cycle)) {
            cycle = "0 0 0 * * *";
        } else if (CYCLE_BY_DAY.equals(cycle)) {
            cycle = "0 0 2 * * *";
        } else {
            cycle = "";
        }
        return cycle;
    }

    /**
     * 根据工单计划表中的周期字段生成工单
     *
     * @param workOrderPlanList 满足条件的工单计划数据
     * @return workOrderList 已生成的工单数据
     */
    public Map<String, Object> produceWorkOrder(List<WorkOrderPlan> workOrderPlanList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime plan_start_time = null;
        LocalDateTime plan_end_time = null;
        List<WorkOrder> workOrderList = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        //1 遍历取出workOrderPlanList中的每一条数据
        for (int i = 0; i < workOrderPlanList.size(); i++) {
            //1.1 计划出当前工单计划需要生成的工单数量（计划结束时间-计划开始时间）/天
            plan_start_time = workOrderPlanList.get(i).getPlan_start_time();
            plan_end_time = workOrderPlanList.get(i).getPlan_end_time();
            //默认为1小时的毫秒数
            int dateNumber = 1000 * 60 * 60;
            //默认为小时的编号
            DateType dateType = DateType.HOUR;
            if (workOrderPlanList.get(i).getCycle().equals("0 0 2 * * *")) {
                //1天的毫秒数
                dateNumber = 1000 * 60 * 60 * 24;
                //天的编号
                dateType = DateType.DAY;
            }
            Duration duration = Duration.between(plan_start_time, plan_end_time);
            int num = (int) (duration.toMillis() / dateNumber);

            //1.2 循环生成工单，并写入WorkOrder
            for (int n = 0; n < num; n++) {
                WorkOrder workOrder = new WorkOrder();
                workOrder.setNumber(workOrderPlanList.get(i).getNumber());
                workOrder.setName(workOrderPlanList.get(i).getName());
                workOrder.setContent(workOrderPlanList.get(i).getContent());
                //调用时间相加方法，计算每个工单的开始时间和结束时间
                workOrder.setStart_time(dateAdd(workOrderPlanList.get(i).getPlan_start_time(), dateType, n));
                workOrder.setEnd_time(dateAdd(workOrderPlanList.get(i).getPlan_start_time(), dateType, n + 1));
                workOrder.setRole(workOrderPlanList.get(i).getRole());
                workOrder.setExecutor(workOrderPlanList.get(i).getExecutor());
                workOrderList.add(workOrder);
            }
            //1.3 更新工单计划表中的工单生成状态（status）字段
            workOrderPlanList.get(i).setStatus("1");
        }
        //2 生成的工单数据写入map中
        map.put("workOrder", workOrderList);
        //3 更新后的工单计划数据写入map中
        map.put("workOrderPlan", workOrderPlanList);
        log.info(LocalDateTime.now().format(formatter) + ":工单生成成功");
        return map;
    }

    /**
     * 时间相加
     *
     * @param date   传入初始时间值
     * @param type   年:1 月:2 天:5 小时:11
     * @param number 时间需要加的数量
     * @return date 返回相加后的值
     */
    public LocalDateTime dateAdd(LocalDateTime date, DateType type, Integer number) {
        if (DateType.DAY.equals(type)) {
            date = date.plusDays(number);
        } else if (DateType.HOUR.equals(type)) {
            date = date.plusHours(number);
        }
        return date;
    }
}

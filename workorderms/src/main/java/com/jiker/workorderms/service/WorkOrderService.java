package com.jiker.workorderms.service;

import com.jiker.workorderms.bean.WorkOrder;
import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.dao.IWorkOrderDao;
import com.jiker.workorderms.dao.IWorkOrderPlanDao;
import com.jiker.workorderms.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.applet.AppletAudioClip;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 20:21
 * @Description TODO
 */
@Service
@Slf4j
public class WorkOrderService {
    @Autowired
    private IWorkOrderDao workOrderDao;

    @Autowired
    private IWorkOrderPlanDao workOrderPlanDao;

    @Autowired
    private Utils utils;

    /**
     * 根据工单计划生成工单
     *
     * @throws ParseException
     */
    public void produceWorkOrder() throws ParseException {

        List<WorkOrderPlan> workOrderPlanList = new ArrayList<>();
        List<WorkOrder> workOrderList = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        //1.获取当前日期
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        String date = df.format(dateTime);
        String newDate = df.format(dateTime.plusDays(1));
        //2.写出满足条件的SQL
        String sql = "select * from workorderms.workorder_plan where status=0 and plan_start_time>=date_format('" + date
                + "','%Y-%m-%d %H:%i:%s') and plan_start_time < date_format('" + newDate + "','%Y-%m-%d %H:%i:%s')";
        //3.调用查询数据的方法
        workOrderPlanList = workOrderDao.queryWorkOrder(sql);
        //4.更新时间值，作为打印时间戳
        String strDate = df.format(LocalDateTime.now());
        //5.调用工单生成方法
        if (workOrderPlanList.size() <= 0) {
            //当日无工单计划
            log.info("当日无工单计划");
            return;
        }
        map = utils.produceWorkOrder(workOrderPlanList);
        workOrderList = (List<WorkOrder>) map.get("workOrder");
        workOrderPlanList = (List<WorkOrderPlan>) map.get("workOrderPlan");
        //5.1 工单数据入库
        boolean flag = workOrderDao.insertWorkOrder(workOrderList);
        if (!flag) {
            //根据工单号生成工单入库失败
            log.error("根据工单号生成工单入库失败");
            return;
        }
        log.info(strDate + ":根据工单号生成工单入库成功");
        //5.2 更新工单计划数据：工单生成状态置为1-已生成
        //...
        boolean result = workOrderPlanDao.batchUpdateStatus(workOrderPlanList);
        if(!result){
            log.error("更新工单计划数据失败");
            return;
        }
        log.info(strDate + "更新工单计划数据成功");
    }
}

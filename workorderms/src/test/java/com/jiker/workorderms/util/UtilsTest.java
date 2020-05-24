package com.jiker.workorderms.util;

import com.jiker.workorderms.bean.WorkOrder;
import com.jiker.workorderms.bean.WorkOrderPlan;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class UtilsTest {

    @Autowired
    private Utils utils;

    @Test
    void createWorkOrderNumber() {

        String department = "DS";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String newNumber = department + date + "000011";
        log.info(newNumber);
        String num = utils.createWorkOrderNumber("DS");
        log.info(num);
        TestCase.assertEquals(newNumber, num);
    }

    @Test
    @Transactional
    @Rollback
    void produceWorkOrderByHour() {
        List<WorkOrderPlan> workOrderPlanList = new ArrayList<>();
        WorkOrderPlan workOrderPlan = new WorkOrderPlan();
        workOrderPlan.setNumber("DS20200524000001");
        workOrderPlan.setName("测试通用汽车");
        workOrderPlan.setContent("通用汽车测试新增工单");
        workOrderPlan.setCycle("0 0 0 * * *");
        workOrderPlan.setPlan_start_time(LocalDateTime.now());
        workOrderPlan.setPlan_end_time(LocalDateTime.now().plusHours(4));
        workOrderPlan.setExecutor("jiker");
        workOrderPlan.setRole("test");
        workOrderPlanList.add(workOrderPlan);
        Map<String, Object> stringObjectMap = utils.produceWorkOrder(workOrderPlanList);
        List<WorkOrder> workOrderList = (List<WorkOrder>) stringObjectMap.get("workOrder");
        TestCase.assertEquals(4, workOrderList.size());
    }

    @Test
    @Transactional
    @Rollback
    void produceWorkOrderByDay() {
        List<WorkOrderPlan> workOrderPlanList = new ArrayList<>();
        WorkOrderPlan workOrderPlan = new WorkOrderPlan();
        workOrderPlan.setNumber("DS20200524000001");
        workOrderPlan.setName("测试通用汽车");
        workOrderPlan.setContent("通用汽车测试新增工单");
        workOrderPlan.setCycle("0 0 2 * * *");
        workOrderPlan.setPlan_start_time(LocalDateTime.now());
        workOrderPlan.setPlan_end_time(LocalDateTime.now().plusDays(4));
        workOrderPlan.setExecutor("jiker");
        workOrderPlan.setRole("test");
        workOrderPlanList.add(workOrderPlan);
        Map<String, Object> stringObjectMap = utils.produceWorkOrder(workOrderPlanList);
        List<WorkOrder> workOrderList = (List<WorkOrder>) stringObjectMap.get("workOrder");
        TestCase.assertEquals(4, workOrderList.size());
    }

}
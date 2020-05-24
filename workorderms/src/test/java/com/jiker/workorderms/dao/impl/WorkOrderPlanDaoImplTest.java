package com.jiker.workorderms.dao.impl;

import com.jiker.workorderms.bean.WorkOrderPlan;
import com.jiker.workorderms.dao.IWorkOrderPlanDao;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class WorkOrderPlanDaoImplTest {

    @Autowired
    private IWorkOrderPlanDao iWorkOrderPlanDao;

    @Test
    @Transactional
    @Rollback
    void testInsert() {
        WorkOrderPlan workOrderPlan = new WorkOrderPlan();
        workOrderPlan.setNumber("TEST20200524000001");
        workOrderPlan.setName("测试通用汽车");
        workOrderPlan.setContent("通用汽车测试新增工单");
        workOrderPlan.setCycle("0 0 0 * * *");
        workOrderPlan.setPlan_start_time(LocalDateTime.now());
        workOrderPlan.setPlan_end_time(LocalDateTime.now().plusHours(3));
        workOrderPlan.setExecutor("jiker");
        workOrderPlan.setRole("test");
        int insertNum = iWorkOrderPlanDao.insert(workOrderPlan);
        TestCase.assertEquals(1,insertNum);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate(){
        WorkOrderPlan workOrderPlan = new WorkOrderPlan();
        workOrderPlan.setId(20);
        workOrderPlan.setContent("通用汽车新增工单修改");
        workOrderPlan.setPlan_start_time(LocalDateTime.of(2020,5,24,8,0,0));
        workOrderPlan.setPlan_end_time(LocalDateTime.of(2020,5,24,18,0,0));
        workOrderPlan.setExecutor("jikerUpdate");
        int updateNum = iWorkOrderPlanDao.update(workOrderPlan);
        WorkOrderPlan workOrderPlanAfterUpdate = iWorkOrderPlanDao.queryById(20);
        log.info(workOrderPlanAfterUpdate.toString());
        TestCase.assertEquals(1, updateNum);
        TestCase.assertEquals("通用汽车新增工单修改",workOrderPlanAfterUpdate.getContent());
    }

    @Test
    @Transactional
    @Rollback
    public void testDelete(){
        WorkOrderPlan workOrderPlan = new WorkOrderPlan();
        workOrderPlan.setId(20);
        TestCase.assertEquals(1, iWorkOrderPlanDao.delete(workOrderPlan));
    }
}
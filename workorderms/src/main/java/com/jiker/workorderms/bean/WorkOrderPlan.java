package com.jiker.workorderms.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Generated;
import java.time.LocalDateTime;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/20 20:11
 * @Description TODO
 */
public class WorkOrderPlan {

    @Getter
    @Setter
    private int id;
    /**
     * 工单编号
     */
    @Setter
    @Getter
    private String number;
    /**
     *  工单名称
     */
    @Setter
    @Getter
    private String name;
    /**
     * 工单内容
     */
    @Setter
    @Getter
    private String content;
    /**
     * 执行周期
     */
    @Setter
    @Getter
    private String cycle;
    /**
     * 计划开始时间
     */
    @Setter
    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime plan_start_time;
    /**
     * 计划完成时间
     */
    @Setter
    @Getter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime plan_end_time;
    /**
     * 角色
     */
    @Setter
    @Getter
    private String role;
    /**
     * 执行人
     */
    @Setter
    @Getter
    private String executor;
    /**
     * 工单生成状态：1-已生成，0-未生成
     */
    @Setter
    @Getter
    private String status;
}

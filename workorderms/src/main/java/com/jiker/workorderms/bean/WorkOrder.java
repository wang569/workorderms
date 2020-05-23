package com.jiker.workorderms.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author xiaomingabcd@126.com
 * @Date 2020/5/21 19:32
 * @Description TODO
 */
@Data
public class WorkOrder {

    @Getter
    @Setter
    private int id;
    /**
     * 工单编号
     */
    @Getter
    @Setter
    private String number;
    /**
     * 工单名称
     */
    @Getter
    @Setter
    private String name;
    /**
     * 工单内容
     */
    @Getter
    @Setter
    private String content;
    /**
     * 开始时间
     */
    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime start_time;
    /**
     * 完成时间
     */
    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime end_time;
    /**
     * 角色
     */
    @Getter
    @Setter
    private String role;
    /**
     * 执行人
     */
    @Getter
    @Setter
    private String executor;
    /**
     * 工单执行状态：1-已执行，0-未执行
     */
    @Getter
    @Setter
    private String status;
}

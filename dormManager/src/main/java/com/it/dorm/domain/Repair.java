package com.it.dorm.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.it.dorm.common.pojo.BaseSearchEntity;
import lombok.Data;

import java.util.Date;

/**
 * 保修对象 repair
 */
@Data
@TableName("repair")
public class Repair extends BaseSearchEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 报修人
     */
    private String repairer;

    /**
     * 报修宿舍楼
     */
    private Long buildId;

    /**
     * 宿舍楼名
     */
    @TableField(exist = false)
    private String buildName;

    /**
     * 报修宿舍房间号
     */
    private Long roomId;

    /**
     *宿舍号
     */
    @TableField(exist = false)
    private String roomNo;

    /**
     * 表单标题
     */
    private String title;

    /**
     * 表单内容
     */
    private String content;

    /**
     * 订单状态（是否维修完成）
     */
    private String state;

    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderBuildTime;

    /**
     * 订单完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderFinishTime;

}

package com.it.dorm.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 宿舍卫生检查，记录每次宿舍的卫生检查情况对象 dorm_check
 */
@Data
@TableName("dorm_check")
public class DormCheck extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 检查记录ID，自增
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long checkId;

    /**
     * 宿舍ID，外键，指向宿舍表
     */
    private Long dormId;

    /**
     * 检查日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    /**
     * 检查人姓名
     */
    private String checkerName;

    /**
     * 卫生评分，0-100分
     */
    private Long cleanlinessScore;

    /**
     * 检查状态，合格或不合格
     */
    private String checkStatus;

    @TableField(exist = false)
    private String roomNo;


}

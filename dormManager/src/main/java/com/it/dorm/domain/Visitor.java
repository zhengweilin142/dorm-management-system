package com.it.dorm.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 到访对象 visitor
 */
@Data
@TableName("visitor")
public class Visitor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Long gender;

    /**
     * 电话
     */
    private String phoneNum;

    /**
     * 来源城市
     */
    private String originCity;

    /**
     * 来访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitTime;

    /**
     * 事情
     */
    private String content;

}

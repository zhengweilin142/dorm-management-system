package com.it.dorm.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单对象 order
 */
@Data
@TableName("`order`")
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long orderId;

    /**
     * 充值人
     */
    private Long userId;

    /**
     * 宿舍Id
     */
    private Long dormId;

    /**
     * 宿舍号
     */
    @TableField(exist = false)
    private String dormNo;

    /**
     * 订单类型 enum('水费', '电费', '其他')
     */
    private String orderType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private String status;

    /**
     *
     */
    @TableField(exist = false)
    private String roomNo;
}

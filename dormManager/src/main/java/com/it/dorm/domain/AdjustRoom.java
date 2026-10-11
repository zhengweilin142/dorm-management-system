package com.it.dorm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;


/**
 * 申请换宿对象 adjust_room
 */
@Data
@TableName("adjust_room")
public class AdjustRoom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 申请人
     */
    private Long userId;

    /**
     * 申请人
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 当前房间
     */
    private Long currentRoomId;
    @TableField(exist = false)
    private String currentRoomNo;

    /**
     * 目标房间
     */
    private Long towardsRoomId;
    @TableField(exist = false)
    private String towardsRoomNo;

    /**
     * 申请状态
     */
    private String state;

    /**
     * 申请时间
     */
    private String applyTime;

    /**
     * 处理时间
     */
    private String finishTime;
}

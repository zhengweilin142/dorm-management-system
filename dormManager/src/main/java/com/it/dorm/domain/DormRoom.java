package com.it.dorm.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 宿舍对象 dorm_room
 */
@Data
@TableName("dorm_room")
public class DormRoom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 宿舍房间号
     */
    private String roomNo;

    /**
     * 宿舍楼号
     */
    private Long buildId;

    /**
     * 楼层
     */
    private Integer floorNum;

    /**
     * 房间最大入住人数
     */
    private Integer maxCapacity;

    /**
     * 当前房间入住人数
     */
    private Integer currentCapacity;

    /**
     * 剩余电/ ？度
     */
    private BigDecimal electricity;

    /**
     * 剩余？吨水
     */
    private BigDecimal water;

    /**
     * 楼名
     */
    @TableField(exist = false)
    private String buildName;
    @TableField(exist = false)
    private Integer notFullRoomCount;


}

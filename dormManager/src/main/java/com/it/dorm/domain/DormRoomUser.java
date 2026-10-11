package com.it.dorm.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;

/**
 * 宿舍人员对象 dorm_room_user
 */
@Data
@TableName("dorm_room_user")
public class DormRoomUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 宿舍房间号
     */
    private Long roomId;
    @TableField(exist = false)
    private String roomNo;

    /**
     * userIds
     */
    @TableField(exist = false)
    private Long[] userIds;

    @TableField(exist = false)
    private String type;
    @TableField(exist = false)
    private String phonenumber;
}

package com.it.dorm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;

/**
 * 用户和角色关联表
 *
 * @TableName sys_user_role
 */
@Data
@TableName(value = "sys_user_role")
public class SysUserRole implements Serializable {
   
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleName;

    /**
     * 角色key
     */
    @TableField(exist = false)
    private String roleKey;


}
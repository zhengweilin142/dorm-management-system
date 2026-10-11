package com.it.dorm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;

/**
 * 角色信息表
 *
 * @TableName sys_role
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_role")
public class SysRole extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0存在 1删除）
     */
    private String delFlag;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private Long[] menuIds;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    @TableField(exist = false)
    private boolean flag = false;
    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    @TableField(exist = false)
    private boolean menuCheckStrictly;

    @TableField(exist = false)
    private String deptCheckStrictly;
    @TableField(exist = false)
    private String admin;

    public static boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }


    public boolean isAdmin() {
        return isAdmin(this.roleId);
    }

}
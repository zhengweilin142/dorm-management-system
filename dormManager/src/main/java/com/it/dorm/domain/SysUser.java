package com.it.dorm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;
import java.util.List;

/**
 * 系统用户表
 *
 * @TableName sys_user
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_user")
public class SysUser extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键Id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;
    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */

    private String email;

    /**
     * 手机号码
     */

    private String phonenumber;

    /**
     * 用户性别
     */

    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0存在 1删除）
     */
    private String delFlag;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private List<SysUserRole> userRoleList;
    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 角色Id
     */
    @TableField(exist = false)
    private Long roleId;

    /**
     * 班级Id
     */
    @TableField(exist = false)
    private Long classId;

    /**
     * 班级Id
     */
    @TableField(exist = false)
    private Long roomId;

    /**
     * 班级Id
     */
    @TableField(exist = false)
    private String roleKey;


    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

}
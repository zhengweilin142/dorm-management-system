package com.it.dorm.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;
import java.util.Set;

/**
 * 角色信息表
 *
 * @TableName sys_role
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleDto extends BaseEntity implements Serializable {
   
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
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
     * 是否有效(F无效 T有效)
     */
    private String isValid;

    /**
     * 角色菜单权限
     */
    private Set<String> permissions;

    /**
     * 菜单组
     */
    private Long[] menuIds;

}
package com.it.dorm.service.impl;

import com.it.dorm.domain.SysUser;
import com.it.dorm.domain.dto.SysRoleDto;
import com.it.dorm.domain.dto.SysUserDto;
import com.it.dorm.utils.StringUtils;
import com.it.dorm.constant.UserConstants;
import com.it.dorm.service.ISysMenuService;
import com.it.dorm.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 
 * @version 1.0
 *  用户权限处理
 * @date 2024/11/2 13:59
 */
@Service
public class PermissionServiceImpl {

    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysMenuService menuService;


    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        return new HashSet<>(roleService.selectRolePermissionByUserId(user.getUserId()));
    }


    /**
     * 获取菜单数据权限
     *
     * @param userDto 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUserDto userDto) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (userDto.isAdmin()) {
            perms.add("*:*:*");
        } else {

            List<SysRoleDto> roles = userDto.getRoleList();
            // 获取用户角色
            if (!CollectionUtils.isEmpty(roles)) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRoleDto role : roles) {
                    if (StringUtils.equals(role.getStatus(), UserConstants.ROLE_NORMAL)) {
                        Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                        role.setPermissions(rolePerms);
                        perms.addAll(rolePerms);
                    }
                }
            } else {
                perms.addAll(menuService.selectMenuPermsByUserId(userDto.getUserId()));
            }
        }
        return perms;
    }
}

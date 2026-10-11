package com.it.dorm.controller;

import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.domain.dto.SysUserDto;
import com.it.dorm.domain.SysMenu;
import com.it.dorm.domain.SysUser;
import com.it.dorm.service.ISysMenuService;
import com.it.dorm.service.LoginService;
import com.it.dorm.service.impl.PermissionServiceImpl;
import com.it.dorm.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 
 * @version 1.0
 *  登入controller
 */
@RestController
@Api(value = "用户登入", tags = "用户登入")
public class LoginController extends BaseController {

    @Resource
    private LoginService loginService;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private PermissionServiceImpl permissionService;


    @PostMapping("/user/login")
    @ApiOperation(value = "用户登入", notes = "用户")
    public AjaxResult login(@RequestBody SysUserDto sysUserDto) {
        //登录
        return loginService.login(sysUserDto);
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "登出")
    public AjaxResult logout() {
        return loginService.logout();
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUserDto sysUserDto = new SysUserDto();
        SysUser user = SecurityUtils.getLoginUser().getSysUser();
        BeanUtils.copyProperties(user, sysUserDto);
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUserDto);
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("roles", roles);
        map.put("permissions", permissions);
        return success(map);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    @ApiOperation(value = "获取路由信息")
    //@PreAuthorize("hasAnyAuthority('system:role:list')")
    public AjaxResult getRouterList() {
        Long userId = SecurityUtils.getUserId();
        //Integer userId = 2;
        List<SysMenu> menuList = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menuList));
    }

}
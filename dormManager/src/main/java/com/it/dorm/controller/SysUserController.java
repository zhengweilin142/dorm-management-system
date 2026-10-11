package com.it.dorm.controller;

import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.SysRole;
import com.it.dorm.domain.SysUser;
import com.it.dorm.service.ISysRoleService;
import com.it.dorm.service.ISysUserService;
import com.it.dorm.utils.SecurityUtils;
import com.it.dorm.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息Controller
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "用户信息", value = "用户信息")
public class SysUserController extends BaseController {

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysRoleService roleService;

    /**
     * 查询用户信息列表
     */
    @GetMapping("/list")
    @ApiOperation("用户信息列表")
    public TableDataInfo list(SysUser sysUser) {
        startPage();
        List<SysUser> list = userService.selectSysUserList(sysUser);
        return getDataTable(list);
    }

    /**
     * 查询用户信息列表(不分页)
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(SysUser sysUser) {
        List<SysUser> list = userService.selectSysUserList(sysUser);
        return success(list);
    }


    /**
     * 获取用户信息详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("用户详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long userId) {
        return success(userService.selectSysUserByUserId(userId));
    }

    /**
     * 新增用户
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysUser user) {

        if (!userService.checkUserNameUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }

        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SysUser user) {
        if (!userService.checkUserNameUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    //@PreAuthorize("@ss.hasPermi('system:user:remove')")
    @DeleteMapping("/{userId}")
    public AjaxResult remove(@PathVariable("userId") Long userId) {
        if (userId.equals(getUserId())) {
            return error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserById(userId));
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    //@PreAuthorize("@ss.hasPermi('system:user:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    //@PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId) {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectSysUserByUserId(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    //@PreAuthorize("@ss.hasPermi('system:user:edit')")
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {

        userService.insertUserAuth(userId, roleIds);
        return success();
    }


}

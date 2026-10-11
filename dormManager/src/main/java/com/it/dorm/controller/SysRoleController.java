package com.it.dorm.controller;

import com.it.dorm.domain.SysRole;
import com.it.dorm.domain.dto.SysRoleDto;
import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.service.impl.SysRoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * 角色信息
 * @date 2024/11/10 00:49
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "角色管理", value = "角色管理")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleServiceImpl roleService;

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    @ApiOperation("获取角色列表(分页)")
    public TableDataInfo list(SysRoleDto roleDto) {
        startPage();
        List<SysRole> list = roleService.selectRoleList(roleDto);
        return getDataTable(list);
    }

    /**
     * 获取角色列表
     */
    @GetMapping("/listAll")
    @ApiOperation("获取角色列表(不分页)")
    public AjaxResult listAll(SysRoleDto roleDto) {
        List<SysRole> list = roleService.selectRoleList(roleDto);
        return success(list);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    @ApiOperation("获取角色详细")
    public AjaxResult getInfo(@PathVariable Long roleId) {
        return success(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @PostMapping
    @ApiOperation("新增角色")
    public AjaxResult add(@RequestBody SysRole role) {


        if (!roleService.checkRoleNameUnique(role)) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        role.setCreateTime(new Date());
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */

    @PutMapping
    @ApiOperation("修改角色")
    public AjaxResult edit(@RequestBody SysRole role) {

        if (!roleService.checkRoleNameUnique(role)) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }

        if (roleService.updateRole(role)) {

            return success();
        }
        return error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }


    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    @ApiOperation("修改角色状态")
    public AjaxResult changeStatus(@RequestBody SysRole role) {
        return toAjax(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleIds}")
    @ApiOperation("删除角色")
    public AjaxResult remove(@PathVariable Long[] roleIds) {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }


}

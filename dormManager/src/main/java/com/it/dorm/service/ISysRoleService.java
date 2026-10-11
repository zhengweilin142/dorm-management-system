package com.it.dorm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.dorm.domain.SysRole;
import com.it.dorm.domain.dto.SysRoleDto;

import java.util.List;
import java.util.Set;

/**
 
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service
 * @createDate 2024-10-26 22:54:05
 */
public interface ISysRoleService extends IService<SysRole> {



    /**
     * 根据条件分页查询角色数据
     * @param roleDto 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRoleDto roleDto);

    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public SysRole selectRoleById(Long roleId);


    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    public boolean checkRoleNameUnique(SysRole role);


    /**
     * 新增保存角色信息
     *
     * @param roleDto 角色信息
     * @return 结果
     */
    public int insertRole(SysRole role);


    /**
     * 修改保存角色信息
     * @param roleDto 角色信息
     * @return 结果
     */
    public boolean updateRole(SysRole role);


    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRoleStatus(SysRole role);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] roleIds);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserId(Long userId);

}

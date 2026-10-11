package com.it.dorm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.SysRole;
import com.it.dorm.domain.dto.SysRoleDto;

import java.util.List;

/**
 
 * @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
 * @createDate 2024-10-26 22:54:05
 * @Entity com.it.tourRoute.domain.SysRole
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据条件分页查询角色数据
     *
     * @param roleDto 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRoleDto roleDto);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<SysRole> selectRolePermissionByUserId(Long userId);

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
     * @param roleName 角色名称
     * @return 角色信息
     */
    public SysRole checkRoleNameUnique(String roleName);

}





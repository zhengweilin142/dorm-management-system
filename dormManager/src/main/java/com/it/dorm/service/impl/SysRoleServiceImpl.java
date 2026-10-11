package com.it.dorm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.constant.UserConstants;
import com.it.dorm.domain.SysRole;
import com.it.dorm.domain.SysRoleMenu;
import com.it.dorm.domain.SysUserRole;
import com.it.dorm.domain.dto.SysRoleDto;
import com.it.dorm.exception.ServiceException;
import com.it.dorm.mapper.SysRoleMapper;
import com.it.dorm.mapper.SysUserRoleMapper;
import com.it.dorm.utils.StringUtils;
import com.it.dorm.service.ISysRoleMenuService;
import com.it.dorm.service.ISysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2024-10-26 22:54:05
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private ISysRoleMenuService roleMenuService;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public List<SysRole> selectRoleList(SysRoleDto roleDto) {
        return this.baseMapper.selectRoleList(roleDto);
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = this.baseMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public SysRole selectRoleById(Long roleId) {
        return this.baseMapper.selectRoleById(roleId);
    }


    @Override
    public boolean checkRoleNameUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1 : role.getRoleId();
        SysRole info = this.baseMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().intValue() != roleId.intValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role) {

        // 新增角色信息

        int row = roleMapper.insert(role);
        insertRoleMenu(role);
        return row;
    }

    @Override
    @Transactional
    public boolean updateRole(SysRole role) {

        // 修改角色信息
        this.baseMapper.updateById(role);
        // 删除角色与菜单关联
        LambdaUpdateWrapper<SysRoleMenu> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysRoleMenu::getRoleId, role.getRoleId());
        roleMenuService.remove(lambdaUpdateWrapper);
        return insertRoleMenu(role);
    }

    @Override
    public int updateRoleStatus(SysRole role) {
        LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysRole::getRoleId, role.getRoleId()).set(SysRole::getStatus, role.getStatus());
        return this.baseMapper.update(updateWrapper);
    }

    @Override
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            SysRole role = selectRoleById(roleId);
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getRoleId, roleId);
            Long count = sysUserRoleMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        LambdaQueryWrapper<SysRoleMenu> lambdaUpdateWrapper = new LambdaQueryWrapper<>();
        lambdaUpdateWrapper.in(SysRoleMenu::getRoleId, Arrays.asList(roleIds));
        roleMenuService.remove(lambdaUpdateWrapper);
        return this.baseMapper.deleteBatchIds(Arrays.asList(roleIds));
    }

    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {


        List<SysRole> userRoles = this.baseMapper.selectRolePermissionByUserId(userId);
        List<SysRole> roles = this.baseMapper.selectList(new LambdaQueryWrapper<SysRole>().eq(SysRole::getDelFlag, '0'));
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }


    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public boolean insertRoleMenu(SysRole role) {
        boolean rows = true;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = roleMenuService.saveBatch(list);
        }
        return rows;
    }
}





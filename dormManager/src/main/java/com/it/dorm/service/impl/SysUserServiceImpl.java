package com.it.dorm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.constant.UserConstants;
import com.it.dorm.domain.SysRole;
import com.it.dorm.domain.SysUser;
import com.it.dorm.domain.SysUserRole;
import com.it.dorm.domain.dto.SysRoleDto;
import com.it.dorm.domain.dto.SysUserDto;
import com.it.dorm.exception.ServiceException;
import com.it.dorm.mapper.SysRoleMapper;
import com.it.dorm.mapper.SysUserMapper;
import com.it.dorm.service.ISysUserRoleService;
import com.it.dorm.service.ISysUserService;
import com.it.dorm.utils.SecurityUtils;
import com.it.dorm.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 针对表【sys_user】的数据库操作Service实现
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Resource
    private SysRoleMapper sysRoleMapper;


    /**
     * 查询用户信息列表
     *
     * @param sysUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<SysUser> selectSysUserList(SysUser sysUser) {
        List<SysUser> userList = this.baseMapper.selectSysUserList(sysUser);
        for (SysUser user : userList) {
            List<SysUserRole> list = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getUserId()));
            Long[] roleIds = list.stream().map(SysUserRole::getRoleId).toArray(Long[]::new);
            user.setRoleIds(roleIds);
        }
        return userList;
    }

    /**
     * 查询用户信息
     *
     * @param userId 用户信息主键
     * @return 用户信息
     */
    @Override
    public SysUser selectSysUserByUserId(Long userId) {
        SysUser user = this.baseMapper.selectSysUserByUserId(userId);
        List<SysUserRole> list = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getUserId()));
        Long[] roleIds = list.stream().map(SysUserRole::getRoleId).toArray(Long[]::new);
        user.setRoleIds(roleIds);
        return user;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = this.baseMapper.insert(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        sysUserRoleService.removeById(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserId, user.getUserId());
        return this.baseMapper.updateById(user);
    }

    @Override
    public int updateUserStatus(SysUser user) {
        return this.baseMapper.updateById(user);
    }

    @Override
    public int updateUserProfile(SysUser user) {
        return this.baseMapper.updateUser(user);
    }

    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUserName, userName);
        updateWrapper.set(SysUser::getAvatar, avatar);
        return this.baseMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public int resetPwd(SysUser user) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUserId, user.getUserId());
        updateWrapper.set(SysUser::getPassword, user.getPassword());
        return this.baseMapper.update(null, updateWrapper);
    }

    @Override
    @Transactional
    public boolean updateUserRole(SysUser user, SysUserDto sysUserDto) {
        Long userId = user.getUserId();
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleService.remove(queryWrapper);

        List<SysRoleDto> roleList = sysUserDto.getRoleList();
        ArrayList<SysUserRole> userRoleList = new ArrayList<>();
        LambdaQueryWrapper<SysRole> roleQueryWrapper = new LambdaQueryWrapper<>();
        roleList.forEach(role -> {
            roleQueryWrapper.clear();
            roleQueryWrapper.eq(SysRole::getRoleId, role.getRoleId());
            Long count = sysRoleMapper.selectCount(roleQueryWrapper);
            if (count > 0) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(role.getRoleId());
                userRoleList.add(sysUserRole);
            }
        });
        return sysUserRoleService.saveBatch(userRoleList);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean checkUserNameUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.baseMapper.checkUserNameUnique(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return boolean
     */
    @Override
    public boolean checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.baseMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return boolean
     */
    @Override
    public boolean checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = this.baseMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            sysUserRoleService.saveBatch(list);
        }
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {

        // 删除用户与角色关联

        sysUserRoleService.removeBatchByIds(Arrays.asList(userIds));
        return this.baseMapper.deleteBatchIds(Arrays.asList(userIds));
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        sysUserRoleService.removeById(userId);

        return this.baseMapper.deleteById(userId);
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(Long userId, Long[] roleIds) {
        sysUserRoleService.removeById(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return this.baseMapper.resetUserPwd(SecurityUtils.getUserId(), userName, password);
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        return this.baseMapper.insert(user) > 0;
    }

    @Override
    public int stuNum() {
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRole::getRoleKey, "student");
        SysRole sysRole = sysRoleMapper.selectOne(lambdaQueryWrapper);
        if (StringUtils.isNull(sysRole)) {
            throw new ServiceException("角色不存在");
        }
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getRoleId, sysRole.getRoleId());
        long count = sysUserRoleService.count(queryWrapper);
        return (int) count;
    }
}





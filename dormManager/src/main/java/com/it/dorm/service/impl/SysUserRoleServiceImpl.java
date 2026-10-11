package com.it.dorm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.SysUserRole;
import com.it.dorm.mapper.SysUserRoleMapper;
import com.it.dorm.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**

* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2024-10-26 22:54:05
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public List<SysUserRole> selectSysUserRoleList(SysUserRole userRole)
    {
        return this.baseMapper.selectSysUserRoleList(userRole);
    }
}





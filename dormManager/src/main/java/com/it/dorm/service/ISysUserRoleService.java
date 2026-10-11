package com.it.dorm.service;

import com.it.dorm.domain.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**

* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service
* @createDate 2024-10-26 22:54:05
*/
public interface ISysUserRoleService  extends IService<SysUserRole>
{

    public List<SysUserRole> selectSysUserRoleList(SysUserRole userRole);

}

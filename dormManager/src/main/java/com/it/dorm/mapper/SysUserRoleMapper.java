package com.it.dorm.mapper;

import com.it.dorm.domain.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**

* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2024-10-26 22:54:05
* @Entity com.it.tourRoute.domain.SysUserRole
*/
public interface SysUserRoleMapper extends BaseMapper<SysUserRole>
{

   public List<SysUserRole> selectSysUserRoleList(SysUserRole userRole);
}





package com.it.dorm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
 * @createDate 2024-10-26 22:54:05
 * @Entity com.it.tourRoute.domain.SysMenu
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuListByUserId(SysMenu menu);

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * 根据userId查询权限列表
     *
     * @param userId 用户Id
     * @return
     */
    public List<String> selectPermsByUserId(Long userId);


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户Id
     * @return 权限列表
     */
    public List<String> selectMenuPermsByUserId(Long userId);


    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);


    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId            角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 选中菜单列表
     */
    public List<Integer> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);


    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeAll();
}





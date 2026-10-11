package com.it.dorm.service;

import com.it.dorm.common.AjaxResult;
import com.it.dorm.domain.dto.SysUserDto;

/**
 
 * @version 1.0
 *  用户登入service
 * @date 2024/10/27 00:35
 */
public interface LoginService {

    /**
     * 用户登入功能
     * @param userDto
     * @return
     */
    AjaxResult login(SysUserDto userDto);

    /**
     * 退出登入功能
     * @return
     */
    AjaxResult logout();

}

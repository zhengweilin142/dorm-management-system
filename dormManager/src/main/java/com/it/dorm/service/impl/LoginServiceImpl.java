package com.it.dorm.service.impl;

import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.redis.RedisCache;
import com.it.dorm.constant.CacheConstants;
import com.it.dorm.domain.LoginUser;
import com.it.dorm.domain.dto.SysUserDto;
import com.it.dorm.exception.ServiceException;
import com.it.dorm.service.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 
 * @version 1.0
 *  登入service
 * @date 2024/10/27 00:37
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Resource
    private TokenService tokenService;

    @Override
    public AjaxResult login(SysUserDto userDto) {
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword());
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new ServiceException("登录失败");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = tokenService.createToken(loginUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return new AjaxResult(200, "登录成功", map);
    }

    @Override
    public AjaxResult logout() {
        try {
            //获取SecurityContextHolder中的用户id
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            String token = loginUser.getToken();
            //删除redis中的值
            redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new AjaxResult(200, "注销成功");
    }

}

package com.it.dorm.handler;

import com.alibaba.fastjson.JSON;
import com.it.dorm.common.AjaxResult;
import com.it.dorm.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 
 * @version 1.0
 *  重写AccessDeniedHandler
 * @date 2024/10/26 23:58
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler
{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
    {
        AjaxResult result = new AjaxResult(HttpStatus.FORBIDDEN.value(), "您的权限不足");
        String json = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response, json);
    }
}

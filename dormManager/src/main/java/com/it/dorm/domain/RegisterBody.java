package com.it.dorm.domain;

import lombok.Data;

@Data
public class RegisterBody {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String confirmPassword;
}

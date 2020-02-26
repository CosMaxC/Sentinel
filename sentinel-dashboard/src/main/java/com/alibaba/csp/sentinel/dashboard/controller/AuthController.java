/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.controller;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.auth.SimpleWebAuthServiceImpl;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.domain.SentinelUser;
import com.alibaba.csp.sentinel.dashboard.service.SentinelUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cdfive
 * @since 1.6.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService<HttpServletRequest> authService;

    @Autowired
    private SentinelUserService sentinelUserService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/login")
    public Result<AuthService.AuthUser> login(HttpServletRequest request, String username, String password) {
        // 校验参数
        if (StringUtils.isBlank(username)){
            LOGGER.error("Login failed: 用户名为空！");
            return Result.ofFail(-1, "用户名为空！");
        }
        if (StringUtils.isBlank(password)){
            LOGGER.error("Login failed: 密码为空！");
            return Result.ofFail(-1, "密码为空！");
        }

        // 获取用户名信息
        Result<SentinelUser> sentinelUserResult = sentinelUserService.selectByUsername(username);
        if (!sentinelUserResult.isSuccess()){
            // 错误返回
            LOGGER.error("Login failed: " + sentinelUserResult.getMsg());
            return Result.ofFail(-1, sentinelUserResult.getMsg());
        }

        SentinelUser data = sentinelUserResult.getData();
        if (!encoder.matches(password, data.getPassword())){
            // 失败校验
            LOGGER.error("Login failed: 无效用户名或密码：" + username);
            return Result.ofFail(-1, "无效用户名或密码！");

        }
        if (!data.getState().equals(SentinelUser.STATE.ACTIVATED)){
            LOGGER.error("Login failed: 用户[ "+ username +" ]未启用！");
            return Result.ofFail(-1, "用户[ "+ username +" ]未启用！");
        }
        AuthService.AuthUser authUser = new SimpleWebAuthServiceImpl.SimpleWebAuthUserImpl(username);
        request.getSession().setAttribute(SimpleWebAuthServiceImpl.WEB_SESSION_KEY, authUser);
        return Result.ofSuccess(authUser);

    }

    @PostMapping(value = "/logout")
    public Result<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return Result.ofSuccess(null);
    }

    @PostMapping(value = "/check")
    public Result<?> check(HttpServletRequest request) {
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        if (authUser == null) {
            return Result.ofFail(-1, "Not logged in");
        }
        return Result.ofSuccess(authUser);
    }
}

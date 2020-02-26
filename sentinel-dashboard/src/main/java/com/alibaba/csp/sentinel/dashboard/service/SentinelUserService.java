package com.alibaba.csp.sentinel.dashboard.service;

import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.domain.SentinelUser;

import java.util.List;

/**
 * @program: sentinel-parent
 * @description: 用户表服务
 * @author: Cosmax
 * @create: 2020/02/26 13:13
 */

public interface SentinelUserService {

    /**
     * 新增用户
     * @param sentinelUser {@link SentinelUser}
     * @return 返回状态实体，成功返回id
     */
    Result<Long> insert(SentinelUser sentinelUser);

    /**
     * 通过用户名获取用户信息
     * @param username 用户名
     * @return 返回状态实体，成功返回用户信息
     */
    Result<SentinelUser> selectByUsername(String username);

    /**
     * 通过id删除用户
     * @param id 用户id
     * @return 返回状态实体，成功返回id
     */
    Result<Long> deleteById(Long id);
}

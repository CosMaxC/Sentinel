package com.alibaba.csp.sentinel.dashboard.service.impl;

import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.domain.SentinelUser;
import com.alibaba.csp.sentinel.dashboard.mapper.SentinelUserMapper;
import com.alibaba.csp.sentinel.dashboard.service.SentinelUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: sentinel-parent
 * @description: sentinel用户表服务实现
 * @author: Cosmax
 * @create: 2020/02/26 13:19
 */

@Service
public class SentinelUserServiceImpl implements SentinelUserService {

    @Resource
    private SentinelUserMapper sentinelUserMapper;

    @Override
    public Result<Long> insert(SentinelUser sentinelUser) {
        Result<Long> result = checkSentinel(sentinelUser);
        if (!result.isSuccess()){
            return result;
        }
//        sentinelUser.setPassword(new BCryptPasswordEncoder().encode(sentinelUser.getPassword()));
        sentinelUser.setCreateDate(new Date());
        sentinelUser.setState(SentinelUser.STATE.ACTIVATED);
        long id = System.currentTimeMillis();
        sentinelUser.setId(id);
        int insert = sentinelUserMapper.insert(sentinelUser);
        if (insert <= 0){
            return Result.ofFail(500, "新增失败！");
        }
        return Result.ofSuccess(id);
    }

    @Override
    public Result<SentinelUser> selectByUsername(String username) {
        if (StringUtils.isBlank(username)){
            return Result.ofFail(500, "用户名不能为空！");
        }
        Example example = new Example(SentinelUser.class);
        example.createCriteria().andEqualTo("username", username);
        SentinelUser sentinelUser = sentinelUserMapper.selectOneByExample(example);
        if (sentinelUser == null){
            return Result.ofFail(500, "用户[ "+ username +" ]不存在！");
        }
        return Result.ofSuccess(sentinelUser);
    }

    @Override
    public Result<Long> deleteById(Long id) {
        if (id == null || id <= 0){
            return Result.ofFail(500, "用户id不能为空！");
        }
        int i = sentinelUserMapper.deleteByPrimaryKey(id);
        if (i <= 0){
            return Result.ofFail(500, "删除失败!");
        }
        return Result.ofSuccess(id);
    }

    private Result<Long> checkSentinel(SentinelUser sentinelUser){
        if (sentinelUser == null){
            return Result.ofFail(500, "传入实体不能为空！");
        }
        if (StringUtils.isBlank(sentinelUser.getUsername())){
            return Result.ofFail(500, "用户名不能为空！");
        }
        if (StringUtils.isBlank(sentinelUser.getPassword())){
            return Result.ofFail(500, "密码不能为空！");
        }
        return Result.ofSuccessMsg("校验成功！");
    }
}

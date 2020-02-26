package com.alibaba.csp.sentinel.dashboard.service;

import com.alibaba.csp.sentinel.dashboard.DashboardApplication;
import com.alibaba.csp.sentinel.dashboard.domain.SentinelUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @program: sentinel-parent
 * @description: sentinel用户表服务测试
 * @author: Cosmax
 * @create: 2020/02/26 14:07
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DashboardApplication.class)
public class SentinelUserServiceTest {

    @Resource
    private SentinelUserService sentinelUserService;

    @Test
    public void insertTest(){
        SentinelUser sentinelUser = new SentinelUser();
        sentinelUser.setUsername("admin");
        sentinelUser.setPassword("admin123");
        System.out.println(sentinelUserService.insert(sentinelUser));
    }

    @Test
    public void selectByUsernameTest(){
        System.out.println(sentinelUserService.selectByUsername("cosmax"));
    }

    @Test
    public void deleteByIdTest(){
        System.out.println(sentinelUserService.deleteById(1L));
    }

    @Test
    public void getPassword(){
        System.out.println(new BCryptPasswordEncoder().encode("sentinel"));
    }
}

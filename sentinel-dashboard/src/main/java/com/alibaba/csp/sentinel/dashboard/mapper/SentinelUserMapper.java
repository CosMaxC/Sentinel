package com.alibaba.csp.sentinel.dashboard.mapper;

import com.alibaba.csp.sentinel.dashboard.domain.SentinelUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mapper.MyMapper;

@Mapper
@Component
public interface SentinelUserMapper extends MyMapper<SentinelUser> {
}

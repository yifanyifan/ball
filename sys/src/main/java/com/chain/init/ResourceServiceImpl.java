package com.chain.init;

import com.chain.common.Constant;
import com.chain.service.PermissionService;
import com.chain.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class ResourceServiceImpl {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void initData() {
        log.info("================> 开始初始化 Redis 用户菜单 集合");
        redisService.hSetAll(Constant.REDIS_RESOURCE_ROLE, permissionService.getUrlAndPermissionAll());
        log.info("================> 结束初始化 Redis 用户菜单 集合");
    }
}

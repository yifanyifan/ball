package com.chain.init;

import com.chain.mapper.RolePermissionMapper;
import com.chain.service.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.chain.constant.Constant.REDIS_RESOURCE_ROLE;

@Component
public class RoleMenuRedisInit implements ApplicationRunner {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 初始化 用户和菜单 集合
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1.读取所有权限-菜单标识C/R/U/D
        List<Map<String, Object>> all = rolePermissionMapper.getAllRoleAndPermission();
        // 2.分组处理 Map<角色, Map<菜单,CRUD接口权限>>
        Map<String, Map<String, Object>> obj3 = all.stream().collect(Collectors.groupingBy(item -> String.valueOf(item.get("name")), // 先按名称分组
                Collectors.collectingAndThen(Collectors.toList(),
                        list -> list.stream().collect(Collectors.toMap(
                                map -> String.valueOf(map.get("authority")), // 以某个键值作为内部Map的键
                                map -> map.get("authority_type"), // 以某个键值作为内部Map的值
                                (existing, replacement) -> replacement, // 处理冲突的方式，此处选择替换
                                LinkedHashMap::new // 用于合并的Map类型
                        ))
                )
        ));
        obj3.entrySet().forEach(i -> {
            RedisUtils.hSetAll(REDIS_RESOURCE_ROLE + i.getKey(), i.getValue());
        });
    }
}

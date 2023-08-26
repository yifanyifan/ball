package com.chain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.entity.Permission;
import com.chain.mapper.PermissionMapper;
import com.chain.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Slf4j
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Map<String, String> getUrlAndPermissionAll() {
        List<Map<String, String>> mapList = permissionMapper.getUrlAndPermissionAll();
        Map<String, String> result = mapList.stream().collect(Collectors.toMap(item -> item.get("url"), item -> item.get("roles")));
        return result;
    }

    @Override
    public List<Permission> getMenuList(Long id) {
        return permissionMapper.getMenuList(id);
    }

}

package com.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author 易樊
 * @since 2023-07-17
 */
public interface PermissionService extends IService<Permission> {
    Map<String, String> getUrlAndPermissionAll();

    List<Permission> getMenuList(Long id);

    List<Permission> getPermissionByRoleId(Long roleId);

    List<Permission> listTree(Permission queryWrapper) throws Exception;
}

package com.chain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.common.PageEntity;
import com.chain.entity.Permission;
import com.chain.mapper.PermissionMapper;
import com.chain.param.PermissionPageParam;
import com.chain.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean savePermission(Permission permission) throws Exception {
        return super.save(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePermission(Permission permission) throws Exception {
        return super.updateById(permission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deletePermission(Long id) throws Exception {
        return super.removeById(id);
    }


    @Override
    public PageEntity<Permission> getPermissionPageList(PermissionPageParam permissionPageParam) throws Exception {
        Page<Permission> page = new Page<>(permissionPageParam.getPageIndex(), permissionPageParam.getPageSize());
        LambdaQueryWrapper<Permission> wrapper = getLambdaQueryWrapper(permissionPageParam);
        IPage<Permission> iPage = permissionMapper.selectPage(page, wrapper);
        return new PageEntity<Permission>(iPage);
    }

    @Override
    public List<Permission> getPermissionList(PermissionPageParam permissionPageParam) throws Exception {
        LambdaQueryWrapper<Permission> wrapper = getLambdaQueryWrapper(permissionPageParam);
        List<Permission> PermissionList = permissionMapper.selectList(wrapper);
        return PermissionList;
    }

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

    private LambdaQueryWrapper<Permission> getLambdaQueryWrapper(PermissionPageParam permissionPageParam) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

}

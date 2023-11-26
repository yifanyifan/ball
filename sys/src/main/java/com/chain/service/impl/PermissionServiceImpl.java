package com.chain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.common.BallException;
import com.chain.entity.Permission;
import com.chain.entity.RolePermission;
import com.chain.mapper.PermissionMapper;
import com.chain.mapper.RolePermissionMapper;
import com.chain.service.PermissionService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
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
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Map<String, String> getUrlAndPermissionAll() {
        List<Map<String, String>> mapList = permissionMapper.getUrlAndPermissionAll();
        Map<String, String> result = mapList.stream().collect(Collectors.toMap(item -> item.get("url"), item -> item.get("roles")));
        return result;
    }

    /**
     * 查询某用户对应的菜单
     */
    @Override
    public List<Permission> getMenuList(Long userId) {
        return permissionMapper.getMenuList(userId);
    }

    /**
     * 根据角色ID获取菜单集合
     */
    @Override
    public List<Permission> getPermissionByRoleId(Long roleId) {
        return permissionMapper.getPermissionByRoleId(roleId);
    }

    /**
     * 新增/编辑用户
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean save(Permission permission) {
        if (ObjectUtil.isEmpty(permission.getId())) {
            permissionMapper.insert(permission);
        } else {
            permissionMapper.updateById(permission);
        }
        return true;
    }

    /**
     * 获取菜单集合（树型）
     */
    public List<Permission> listTree(Permission entity) throws Exception {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(entity)) {
            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equals("serialVersionUID")) {
                    continue;
                }
                Object value = field.get(entity);
                if (ObjectUtil.isNotEmpty(value)) {
                    wrapper.eq(StrUtil.toUnderlineCase(field.getName()), value);
                }
            }
        }
        List<Permission> permissionList = this.list(wrapper);
        List<Permission> permissionLevel = getTree(permissionList);

        return permissionLevel;
    }

    public List<Permission> getTree(List<Permission> permissionList) {
        List<Permission> result = new ArrayList<>();

        // 构建一个以权限ID为键的Map，方便快速查找权限
        Map<Long, Permission> permissionMap = permissionList.stream().collect(Collectors.toMap(Permission::getId, Function.identity()));

        for (Permission permission : permissionList) {
            Long parentId = permission.getParentId();

            if (parentId == -1) {
                // 处理顶级权限
                result.add(permission);
            } else {
                // 处理子权限
                Permission parent = permissionMap.get(parentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(permission);
                }
            }
        }

        return result;
    }

    /**
     * 获取菜单集合
     */
    @Override
    public List<Permission> list(Wrapper<Permission> queryWrapper) {
        List<Permission> permissionList = this.getBaseMapper().selectList(queryWrapper);

        for (Permission record : permissionList) {
            record.setHasChildren(CollectionUtil.isNotEmpty(permissionMapper.getListByParentId(record.getId())) ? true : false);
        }

        return permissionList;
    }

    /**
     * 分页
     */
    @Override
    public <E extends IPage<Permission>> E page(E page, Wrapper<Permission> queryWrapper) {
        IPage<Permission> p = permissionMapper.selectPage(page, queryWrapper);
        for (Permission record : p.getRecords()) {
            record.setParentStr(record.getParentId() == -1 ? "" : permissionMapper.selectById(record.getParentId()).getName());
            record.setHasChildren(CollectionUtil.isNotEmpty(permissionMapper.getListByParentId(record.getId())) ? true : false);
        }

        return (E) p;
    }

    /**
     * 删除菜单
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        //获取所有子元素
        List<Permission> permissionList = permissionMapper.selectByParentId((Long) id);
        List<Long> permissionIdList = Optional.ofNullable(permissionList).orElse(Collections.emptyList()).stream().map(Permission::getId).collect(Collectors.toList());
        permissionIdList.add((Long) id);

        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByPermissionIdList(permissionIdList);
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            throw new BallException("有角色关联，无法删除");
        }

        this.removeBatchByIds(permissionIdList);

        return true;
    }
}

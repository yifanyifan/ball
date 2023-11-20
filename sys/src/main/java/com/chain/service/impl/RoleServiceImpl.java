package com.chain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.entity.Permission;
import com.chain.entity.Role;
import com.chain.entity.RolePermission;
import com.chain.entity.UserRole;
import com.chain.mapper.RoleMapper;
import com.chain.mapper.RolePermissionMapper;
import com.chain.mapper.UserRoleMapper;
import com.chain.service.RoleService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> getRoleByUser(Long id) {
        return roleMapper.getRoleByUser(id);
    }

    /**
     * 新增/编辑用户
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean save(Role role) {
        if (ObjectUtil.isEmpty(role.getId())) {
            roleMapper.insert(role);
        } else {
            roleMapper.updateById(role);
        }

        if (CollectionUtil.isNotEmpty(role.getPermissionList())) {
            //参数
            List<Long> permissionIdList = role.getPermissionList().stream().map(i -> i.getId()).collect(Collectors.toList());
            //数据库
            List<RolePermission> rolePermissionListDB = rolePermissionMapper.selectByRoleId(role.getId());
            List<Long> permissionIdListDB = rolePermissionListDB.stream().map(i -> i.getPermissionId()).collect(Collectors.toList());

            //新增
            List<Permission> permissionListAdd = role.getPermissionList().stream().filter(i -> !permissionIdListDB.contains(i.getId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(permissionListAdd)) {
                permissionListAdd.stream().forEach(i -> {
                    rolePermissionMapper.insert(new RolePermission().setRoleId(role.getId()).setPermissionId(i.getId()).setAuthorityType(i.getAuthority()));
                });
            }

            //删除
            List<RolePermission> rolePermissionListDel = rolePermissionListDB.stream().filter(i -> !permissionIdList.contains(i.getPermissionId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(rolePermissionListDel)) {
                rolePermissionMapper.deleteBatchIds(rolePermissionListDel);
            }
        }
        return true;
    }

    /**
     * 删除用户
     */
    @SneakyThrows
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {

        List<UserRole> userRoleList = userRoleMapper.selectByRoleId(id);
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            throw new RuntimeException("有用户关联，无法删除");
        }

        roleMapper.deleteById(id);
        rolePermissionMapper.deleteByRole((Long) id);

        return true;
    }
}

package com.chain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.common.PageEntity;
import com.chain.dto.RoleDTO;
import com.chain.dto.UserDTO;
import com.chain.entity.Permission;
import com.chain.entity.Role;
import com.chain.entity.User;
import com.chain.mapper.PermissionMapper;
import com.chain.mapper.RoleMapper;
import com.chain.mapper.UserMapper;
import com.chain.param.UserPageParam;
import com.chain.service.UserService;
import com.chain.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUser(User user) throws Exception {
        return super.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(User user) throws Exception {
        return super.updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteUser(Long id) throws Exception {
        return super.removeById(id);
    }


    @Override
    public PageEntity<User> getUserPageList(UserPageParam userPageParam) throws Exception {
        Page<User> page = new Page<>(userPageParam.getPageIndex(), userPageParam.getPageSize());
        LambdaQueryWrapper<User> wrapper = getLambdaQueryWrapper(userPageParam);
        IPage<User> iPage = userMapper.selectPage(page, wrapper);
        return new PageEntity<User>(iPage);
    }

    @Override
    public List<User> getUserList(UserPageParam userPageParam) throws Exception {
        LambdaQueryWrapper<User> wrapper = getLambdaQueryWrapper(userPageParam);
        List<User> UserList = userMapper.selectList(wrapper);
        return UserList;
    }

    private LambdaQueryWrapper<User> getLambdaQueryWrapper(UserPageParam userPageParam) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    @Override
    public UserDTO loadUserByUsername(String username) {
        UserDTO userDTO = null;
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (ObjectUtil.isNotEmpty(user)) {
            userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            List<Role> roleList = roleMapper.getRoleByUser(user.getId());
            if (CollectionUtil.isNotEmpty(roleList)) {
                List<RoleDTO> roleDTOList = DataUtil.toBeanList(roleList, RoleDTO.class);
                userDTO.setRoleDTOList(roleDTOList);
                List<Permission> permissionList = permissionMapper.getPermissionByRole(roleList);
                List<String> authorityList = permissionList.stream().map(i -> i.getAuthority()).collect(Collectors.toList());
                userDTO.setPermissionList(authorityList);
            }
        }
        return userDTO;
    }

}

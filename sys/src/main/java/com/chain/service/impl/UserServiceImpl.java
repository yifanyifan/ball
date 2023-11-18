package com.chain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.dto.RoleDTO;
import com.chain.dto.UserDTO;
import com.chain.entity.Role;
import com.chain.entity.User;
import com.chain.entity.UserRole;
import com.chain.mapper.RoleMapper;
import com.chain.mapper.UserMapper;
import com.chain.mapper.UserRoleMapper;
import com.chain.service.UserService;
import com.chain.utils.DataUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO loadUserByUsername(String username) {
        UserDTO userDTO = null;
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (ObjectUtil.isNotEmpty(user)) {
            userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            List<Role> roleList = roleMapper.getRoleByUser(user.getId());
            if (CollectionUtil.isNotEmpty(roleList)) {
                //加载用户角色
                List<RoleDTO> roleDTOList = DataUtil.toBeanList(roleList, RoleDTO.class);
                userDTO.setRoleDTOList(roleDTOList);
            }
        }
        return userDTO;
    }

    /**
     * 新增/编辑用户
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean save(User user) {
        if (ObjectUtil.isEmpty(user.getId())) {
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword("123456789");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insert(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.updateById(user);
        }

        if (CollectionUtil.isNotEmpty(user.getRoleList())) {
            //参数
            List<Long> roleIdList = user.getRoleList().stream().map(i -> i.getId()).collect(Collectors.toList());
            //数据库
            List<UserRole> userRoleListDB = userRoleMapper.selectByUserId(user.getId());
            List<Long> roleIdListDB = userRoleListDB.stream().map(i -> i.getRoleId()).collect(Collectors.toList());

            //新增
            List<Long> roleIdListAdd = roleIdList.stream().filter(i -> !roleIdListDB.contains(i)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(roleIdListAdd)) {
                roleIdListAdd.stream().forEach(i -> {
                    userRoleMapper.insert(new UserRole().setUserId(user.getId()).setRoleId(i));
                });
            }

            //删除
            List<UserRole> userRoleIdListDBDel = userRoleListDB.stream().filter(i -> !roleIdList.contains(i.getRoleId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(userRoleIdListDBDel)) {
                List<Long> userRoleIdListDel = userRoleIdListDBDel.stream().map(i -> i.getId()).collect(Collectors.toList());
                userRoleMapper.deleteBatchIds(userRoleIdListDel);
            }
        }
        return true;
    }

    /**
     * 删除用户
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        userMapper.deleteById(id);
        userRoleMapper.deleteByUserId((Long) id);

        return true;
    }
}

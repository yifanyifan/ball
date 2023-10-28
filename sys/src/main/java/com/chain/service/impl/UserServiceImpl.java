package com.chain.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.dto.RoleDTO;
import com.chain.dto.UserDTO;
import com.chain.entity.Role;
import com.chain.entity.User;
import com.chain.mapper.PermissionMapper;
import com.chain.mapper.RoleMapper;
import com.chain.mapper.UserMapper;
import com.chain.service.UserService;
import com.chain.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

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

                //加载用户权限
                List<Map<String, String>> permissionList = permissionMapper.getPermissionByRole(roleList);
                System.out.println(JSON.toJSONString(permissionList));
                List<String> result = permissionList.stream().flatMap(map -> {
                    String[] typeArray = map.get("types").split(",");
                    return Arrays.stream(typeArray).map(type -> map.get("authority") + "_" + type);
                }).collect(Collectors.toList());
                userDTO.setPermissionList(result);
            }
        }
        return userDTO;
    }

}

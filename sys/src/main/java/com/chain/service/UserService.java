package com.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.dto.UserDTO;
import com.chain.entity.User;

/**
 * 服务类
 *
 * @author 易樊
 * @since 2023-07-17
 */
public interface UserService extends IService<User> {

    UserDTO loadUserByUsername(String username);
}

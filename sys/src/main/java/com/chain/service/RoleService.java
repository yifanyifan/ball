package com.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.entity.Role;

import java.util.List;

/**
 * 服务类
 *
 * @author 易樊
 * @since 2023-07-17
 */
public interface RoleService extends IService<Role> {
    List<Role> getRoleByUser(Long id);
}

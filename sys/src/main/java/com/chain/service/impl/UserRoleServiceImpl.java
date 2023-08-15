package com.chain.service.impl;

import com.chain.entity.UserRole;
import com.chain.mapper.UserRoleMapper;
import com.chain.service.UserRoleService;
import com.chain.param.UserRolePageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.common.PageEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 *  服务实现类
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUserRole(UserRole userRole) throws Exception {
        return super.save(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUserRole(UserRole userRole) throws Exception {
        return super.updateById(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteUserRole(Long id) throws Exception {
        return super.removeById(id);
    }


    @Override
    public PageEntity<UserRole> getUserRolePageList(UserRolePageParam userRolePageParam) throws Exception {
        Page<UserRole> page = new Page<>(userRolePageParam.getPageIndex(), userRolePageParam.getPageSize());
        LambdaQueryWrapper<UserRole> wrapper = getLambdaQueryWrapper(userRolePageParam);
        IPage<UserRole> iPage = userRoleMapper.selectPage(page, wrapper);
        return new PageEntity<UserRole>(iPage);
    }

    @Override
    public List<UserRole> getUserRoleList(UserRolePageParam userRolePageParam) throws Exception {
        LambdaQueryWrapper<UserRole> wrapper = getLambdaQueryWrapper(userRolePageParam);
        List<UserRole> UserRoleList = userRoleMapper.selectList(wrapper);
        return UserRoleList;
    }

    private LambdaQueryWrapper<UserRole> getLambdaQueryWrapper(UserRolePageParam userRolePageParam) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

}

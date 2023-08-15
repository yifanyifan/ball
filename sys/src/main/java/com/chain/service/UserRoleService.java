package com.chain.service;

import com.chain.entity.UserRole;
import com.chain.param.UserRolePageParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.common.PageEntity;
import java.util.List;

/**
 *  服务类
 *
 * @author 易樊
 * @since 2023-07-17
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 保存
     *
     * @param userRole
     * @return
     * @throws Exception
     */
    boolean saveUserRole(UserRole userRole) throws Exception;

    /**
     * 修改
     *
     * @param userRole
     * @return
     * @throws Exception
     */
    boolean updateUserRole(UserRole userRole) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteUserRole(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param userRolePageParam
     * @return
     * @throws Exception
     */
    PageEntity<UserRole> getUserRolePageList(UserRolePageParam userRolePageParam) throws Exception;

    /**
     * 获取列表对象
     *
     * @param userRolePageParam
     * @return
     * @throws Exception
     */
    List<UserRole> getUserRoleList(UserRolePageParam userRolePageParam) throws Exception;

}

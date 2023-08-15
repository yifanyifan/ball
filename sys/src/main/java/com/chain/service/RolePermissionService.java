package com.chain.service;

import com.chain.entity.RolePermission;
import com.chain.param.RolePermissionPageParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.common.PageEntity;
import java.util.List;

/**
 *  服务类
 *
 * @author 易樊
 * @since 2023-07-17
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 保存
     *
     * @param rolePermission
     * @return
     * @throws Exception
     */
    boolean saveRolePermission(RolePermission rolePermission) throws Exception;

    /**
     * 修改
     *
     * @param rolePermission
     * @return
     * @throws Exception
     */
    boolean updateRolePermission(RolePermission rolePermission) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteRolePermission(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param rolePermissionPageParam
     * @return
     * @throws Exception
     */
    PageEntity<RolePermission> getRolePermissionPageList(RolePermissionPageParam rolePermissionPageParam) throws Exception;

    /**
     * 获取列表对象
     *
     * @param rolePermissionPageParam
     * @return
     * @throws Exception
     */
    List<RolePermission> getRolePermissionList(RolePermissionPageParam rolePermissionPageParam) throws Exception;

}

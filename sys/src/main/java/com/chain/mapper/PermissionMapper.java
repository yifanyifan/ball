package com.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chain.entity.Permission;
import com.chain.entity.Role;
import com.chain.param.PermissionPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *  Mapper 接口
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 通过角色获取权限集合
     * @param roleList
     * @return
     */
    List<Permission> getPermissionByRole(@Param("params") List<Role> roleList);

    /**
     * 获取所有URL+名称集合
     * @return
     */
    List<Map<String, String>> getUrlAndPermissionAll();

    /**
     * 通过用户ID获取权限集合
     * @param id
     * @return
     */
    List<Permission> getMenuList(@Param("param") Long id);
}

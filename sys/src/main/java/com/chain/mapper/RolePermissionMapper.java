package com.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chain.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Mapper 接口
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


    List<Map<String, Object>> getAllRoleAndPermission();

    List<RolePermission> selectByRoleId(@Param("roleId") Long roleId);

    void deleteByRole(@Param("roleId") Long roleId);

    List<RolePermission> selectByPermissionIdList(@Param("permissionIdList") List<Long> permissionIdList);
}

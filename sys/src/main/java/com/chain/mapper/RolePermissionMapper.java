package com.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chain.entity.RolePermission;
import com.chain.param.RolePermissionPageParam;

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
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


    List<Map<String, Object>> getAllRoleAndPermission();

}

package com.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chain.entity.Role;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getRoleByUser(@Param("param") Long id);
}

package com.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chain.entity.UserRole;
import com.chain.param.UserRolePageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 *  Mapper 接口
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {


}

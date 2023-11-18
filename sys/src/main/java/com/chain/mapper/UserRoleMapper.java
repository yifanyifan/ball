package com.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chain.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Mapper 接口
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {
    List<UserRole> selectByUserIdAndRoleIdList(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList);

    List<UserRole> selectByUserId(@Param("userId") Long userId);

    void deleteByUserId(@Param("userId") Long userId);
}

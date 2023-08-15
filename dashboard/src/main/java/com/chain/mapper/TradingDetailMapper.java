package com.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chain.entity.TradingDetail;
import com.chain.param.TradingDetailPageParam;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

/**
 * 交易明细表 Mapper 接口
 *
 * @author 易樊
 * @since 2023-07-23
 */
@Repository
public interface TradingDetailMapper extends BaseMapper<TradingDetail> {


}

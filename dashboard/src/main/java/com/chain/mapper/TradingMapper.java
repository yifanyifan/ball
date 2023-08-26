package com.chain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chain.entity.Trading;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 历史委托表 Mapper 接口
 *
 * @author 易樊
 * @since 2023-07-23
 */
@Repository
public interface TradingMapper extends BaseMapper<Trading> {
    List<Trading> testIn(@Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);
}

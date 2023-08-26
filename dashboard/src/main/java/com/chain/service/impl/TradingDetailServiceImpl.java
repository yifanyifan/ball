package com.chain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.entity.TradingDetail;
import com.chain.mapper.TradingDetailMapper;
import com.chain.service.TradingDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易明细表 服务实现类
 *
 * @author 易樊
 * @since 2023-07-23
 */
@Slf4j
@Service
public class TradingDetailServiceImpl extends ServiceImpl<TradingDetailMapper, TradingDetail> implements TradingDetailService {

    @Autowired
    private TradingDetailMapper tradingDetailMapper;

}

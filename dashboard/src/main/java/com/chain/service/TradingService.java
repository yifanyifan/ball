package com.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.entity.Trading;

/**
 * 历史委托表 服务类
 *
 * @author 易樊
 * @since 2023-07-23
 */
public interface TradingService extends IService<Trading> {

    boolean testTrading(Trading trading) throws Exception;

}

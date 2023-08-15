package com.chain.service;

import com.chain.entity.Trading;
import com.chain.param.TradingPageParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.common.PageEntity;
import java.util.List;

/**
 * 历史委托表 服务类
 *
 * @author 易樊
 * @since 2023-07-23
 */
public interface TradingService extends IService<Trading> {

    /**
     * 保存
     *
     * @param trading
     * @return
     * @throws Exception
     */
    boolean saveTrading(Trading trading) throws Exception;

    /**
     * 修改
     *
     * @param trading
     * @return
     * @throws Exception
     */
    boolean updateTrading(Trading trading) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteTrading(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param tradingPageParam
     * @return
     * @throws Exception
     */
    PageEntity<Trading> getTradingPageList(TradingPageParam tradingPageParam) throws Exception;

    /**
     * 获取列表对象
     *
     * @param tradingPageParam
     * @return
     * @throws Exception
     */
    List<Trading> getTradingList(TradingPageParam tradingPageParam) throws Exception;

}

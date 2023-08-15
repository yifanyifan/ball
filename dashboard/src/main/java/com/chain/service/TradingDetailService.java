package com.chain.service;

import com.chain.entity.TradingDetail;
import com.chain.param.TradingDetailPageParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chain.common.PageEntity;
import java.util.List;

/**
 * 交易明细表 服务类
 *
 * @author 易樊
 * @since 2023-07-23
 */
public interface TradingDetailService extends IService<TradingDetail> {

    /**
     * 保存
     *
     * @param tradingDetail
     * @return
     * @throws Exception
     */
    boolean saveTradingDetail(TradingDetail tradingDetail) throws Exception;

    /**
     * 修改
     *
     * @param tradingDetail
     * @return
     * @throws Exception
     */
    boolean updateTradingDetail(TradingDetail tradingDetail) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteTradingDetail(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param tradingDetailPageParam
     * @return
     * @throws Exception
     */
    PageEntity<TradingDetail> getTradingDetailPageList(TradingDetailPageParam tradingDetailPageParam) throws Exception;

    /**
     * 获取列表对象
     *
     * @param tradingDetailPageParam
     * @return
     * @throws Exception
     */
    List<TradingDetail> getTradingDetailList(TradingDetailPageParam tradingDetailPageParam) throws Exception;

}

package com.chain.service.impl;

import com.chain.entity.TradingDetail;
import com.chain.mapper.TradingDetailMapper;
import com.chain.service.TradingDetailService;
import com.chain.param.TradingDetailPageParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.common.PageEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveTradingDetail(TradingDetail tradingDetail) throws Exception {
        return super.save(tradingDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateTradingDetail(TradingDetail tradingDetail) throws Exception {
        return super.updateById(tradingDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteTradingDetail(Long id) throws Exception {
        return super.removeById(id);
    }


    @Override
    public PageEntity<TradingDetail> getTradingDetailPageList(TradingDetailPageParam tradingDetailPageParam) throws Exception {
        Page<TradingDetail> page = new Page<>(tradingDetailPageParam.getPageIndex(), tradingDetailPageParam.getPageSize());
        LambdaQueryWrapper<TradingDetail> wrapper = getLambdaQueryWrapper(tradingDetailPageParam);
        IPage<TradingDetail> iPage = tradingDetailMapper.selectPage(page, wrapper);
        return new PageEntity<TradingDetail>(iPage);
    }

    @Override
    public List<TradingDetail> getTradingDetailList(TradingDetailPageParam tradingDetailPageParam) throws Exception {
        LambdaQueryWrapper<TradingDetail> wrapper = getLambdaQueryWrapper(tradingDetailPageParam);
        List<TradingDetail> TradingDetailList = tradingDetailMapper.selectList(wrapper);
        return TradingDetailList;
    }

    private LambdaQueryWrapper<TradingDetail> getLambdaQueryWrapper(TradingDetailPageParam tradingDetailPageParam) {
        LambdaQueryWrapper<TradingDetail> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

}

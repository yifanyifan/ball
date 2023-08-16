package com.chain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.common.PageEntity;
import com.chain.common.ResultEntity;
import com.chain.dto.RoleDTO;
import com.chain.entity.Trading;
import com.chain.feign.RuleFeign;
import com.chain.mapper.TradingMapper;
import com.chain.param.TradingPageParam;
import com.chain.service.TradingService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 历史委托表 服务实现类
 *
 * @author 易樊
 * @since 2023-07-23
 */
@Slf4j
@Service
public class TradingServiceImpl extends ServiceImpl<TradingMapper, Trading> implements TradingService {

    @Autowired
    private TradingMapper tradingMapper;
    @Autowired
    private RuleFeign ruleFeign;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean saveTrading(Trading trading) throws Exception {
        System.out.println("xid_order1:" + RootContext.getXID());
        //List<Trading> tradingList = tradingMapper.testIn(DateUtil.parse("2023-07-07 17:02:03", "yyyy-MM-dd HH:mm:ss"), DateUtil.parse("2023-07-08 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        super.save(trading);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("dddddddddddddddddddddddd");
        ResultEntity<Boolean> a = ruleFeign.create(roleDTO);
        System.out.println(a);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateTrading(Trading trading) throws Exception {
        return super.updateById(trading);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteTrading(Long id) throws Exception {
        return super.removeById(id);
    }


    @Override
    public IPage<Trading> getTradingPageList(TradingPageParam tradingPageParam) throws Exception {
        Page<Trading> page = new Page<>(tradingPageParam.getPageIndex(), tradingPageParam.getPageSize());
        LambdaQueryWrapper<Trading> wrapper = getLambdaQueryWrapper(tradingPageParam);
        IPage<Trading> iPage = tradingMapper.selectPage(page, wrapper);
        return iPage;
    }

    @Override
    public List<Trading> getTradingList(TradingPageParam tradingPageParam) throws Exception {
        LambdaQueryWrapper<Trading> wrapper = getLambdaQueryWrapper(tradingPageParam);
        List<Trading> TradingList = tradingMapper.selectList(wrapper);
        return TradingList;
    }

    private LambdaQueryWrapper<Trading> getLambdaQueryWrapper(TradingPageParam tradingPageParam) {
        LambdaQueryWrapper<Trading> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

}

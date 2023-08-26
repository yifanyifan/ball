package com.chain.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.common.ResultEntity;
import com.chain.dto.RoleDTO;
import com.chain.entity.Trading;
import com.chain.feign.sys.RuleFeign;
import com.chain.mapper.TradingMapper;
import com.chain.service.TradingService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
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
    //@GlobalTransactional(rollbackFor = Exception.class)
    @Transactional
    @ShardingTransactionType(TransactionType.BASE)
    public boolean testTrading(Trading trading) throws Exception {
        System.out.println("xid_order1:" + RootContext.getXID());
        List<Trading> tradingList = tradingMapper.testIn(DateUtil.parse("2023-07-07 17:02:03", "yyyy-MM-dd HH:mm:ss"), DateUtil.parse("2023-07-08 23:00:00", "yyyy-MM-dd HH:mm:ss"));
        //super.save(trading);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("dddddddddddddddddddddddd");
        ResultEntity<Boolean> a = ruleFeign.create(roleDTO);
        return true;
    }
}

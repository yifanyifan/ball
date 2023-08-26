package com.chain.controller;

import com.chain.common.BaseController;
import com.chain.common.ResultEntity;
import com.chain.entity.Trading;
import com.chain.service.TradingService;
import com.chain.validator.groups.Add;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 历史委托表 控制器
 *
 * @author 易樊
 * @since 2023-07-23
 */
@Slf4j
@RestController
@RequestMapping("/trading")
@Api(value = "历史委托表API", tags = {"历史委托表"})
public class TradingController extends BaseController<Trading> {

    @Autowired
    private TradingService tradingService;

    /**
     * 添加历史委托表
     */
    @PostMapping("/test")
    @ApiOperation(value = "添加历史委托表", response = ResultEntity.class)
    public ResultEntity<Boolean> addTrading(@Validated(Add.class) @RequestBody Trading trading) throws Exception {
        boolean flag = tradingService.testTrading(trading);
        return ResultEntity.success(flag);
    }

}


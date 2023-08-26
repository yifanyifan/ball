package com.chain.controller;

import com.chain.common.BaseController;
import com.chain.entity.TradingDetail;
import com.chain.service.TradingDetailService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易明细表 控制器
 *
 * @author 易樊
 * @since 2023-07-23
 */
@Slf4j
@RestController
@RequestMapping("/tradingDetail")
@Api(value = "交易明细表API", tags = {"交易明细表"})
public class TradingDetailController extends BaseController<TradingDetail> {

    @Autowired
    private TradingDetailService tradingDetailService;


}


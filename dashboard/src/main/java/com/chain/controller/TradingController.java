package com.chain.controller;

import com.chain.common.PageEntity;
import com.chain.common.ResultEntity;
import com.chain.entity.Trading;
import com.chain.param.TradingPageParam;
import com.chain.service.TradingService;
import com.chain.validator.groups.Add;
import com.chain.validator.groups.Update;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class TradingController {

    @Autowired
    private TradingService tradingService;

    /**
     * 添加历史委托表
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加历史委托表", response = ResultEntity.class)
    public ResultEntity<Boolean> addTrading(@Validated(Add.class) @RequestBody Trading trading) throws Exception {
        boolean flag = tradingService.saveTrading(trading);
        return ResultEntity.success(flag);
    }

    /**
     * 修改历史委托表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改历史委托表", response = ResultEntity.class)
    public ResultEntity<Boolean> updateTrading(@Validated(Update.class) @RequestBody Trading trading) throws Exception {
        boolean flag = tradingService.updateTrading(trading);
        return ResultEntity.success(flag);
    }

    /**
     * 删除历史委托表
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除历史委托表", response = ResultEntity.class)
    public ResultEntity<Boolean> deleteTrading(@PathVariable("id") Long id) throws Exception {
        boolean flag = tradingService.deleteTrading(id);
        return ResultEntity.success(flag);
    }

    /**
     * 获取历史委托表详情
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "历史委托表详情", response = Trading.class)
    public ResultEntity<Trading> getTrading(@PathVariable("id") Long id) throws Exception {
        Trading trading = tradingService.getById(id);
        return ResultEntity.success(trading);
    }

    /**
     * 历史委托表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "历史委托表分页列表", response = Trading.class)
    public ResultEntity<PageEntity<Trading>> getTradingPageList(@Validated @RequestBody TradingPageParam tradingPageParam) throws Exception {
        PageEntity<Trading> paging = tradingService.getTradingPageList(tradingPageParam);
        return ResultEntity.success(paging);
    }

    /**
     * 历史委托表列表
     */
    @PostMapping("/getList")
    @ApiOperation(value = "历史委托表列表", response = Trading.class)
    @ApiOperationSupport(ignoreParameters = {"tradingPageParam.pageIndex", "tradingPageParam.pageSorts", "tradingPageParam.pageSize"})
    public ResultEntity<List<Trading>> getTradingList(@Validated @RequestBody TradingPageParam tradingPageParam) throws Exception {
        List<Trading> list = tradingService.getTradingList(tradingPageParam);
        return ResultEntity.success(list);
    }

}


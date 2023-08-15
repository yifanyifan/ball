package com.chain.controller;

import com.chain.entity.TradingDetail;
import com.chain.service.TradingDetailService;
import lombok.extern.slf4j.Slf4j;
import com.chain.param.TradingDetailPageParam;
import com.chain.common.ResultEntity;
import com.chain.common.PageEntity;
import com.chain.validator.groups.Add;
import com.chain.validator.groups.Update;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class TradingDetailController {

    @Autowired
    private TradingDetailService tradingDetailService;

    /**
     * 添加交易明细表
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加交易明细表", response = ResultEntity.class)
    public ResultEntity<Boolean> addTradingDetail(@Validated(Add.class) @RequestBody TradingDetail tradingDetail) throws Exception {
        boolean flag = tradingDetailService.saveTradingDetail(tradingDetail);
        return ResultEntity.success(flag);
    }

    /**
     * 修改交易明细表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改交易明细表", response = ResultEntity.class)
    public ResultEntity<Boolean> updateTradingDetail(@Validated(Update.class) @RequestBody TradingDetail tradingDetail) throws Exception {
        boolean flag = tradingDetailService.updateTradingDetail(tradingDetail);
        return ResultEntity.success(flag);
    }

    /**
     * 删除交易明细表
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除交易明细表", response = ResultEntity.class)
    public ResultEntity<Boolean> deleteTradingDetail(@PathVariable("id") Long id) throws Exception {
        boolean flag = tradingDetailService.deleteTradingDetail(id);
        return ResultEntity.success(flag);
    }

    /**
     * 获取交易明细表详情
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "交易明细表详情", response = TradingDetail.class)
    public ResultEntity<TradingDetail> getTradingDetail(@PathVariable("id") Long id) throws Exception {
        TradingDetail tradingDetail = tradingDetailService.getById(id);
        return ResultEntity.success(tradingDetail);
    }

    /**
     * 交易明细表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "交易明细表分页列表", response = TradingDetail.class)
    public ResultEntity<PageEntity<TradingDetail>> getTradingDetailPageList(@Validated @RequestBody TradingDetailPageParam tradingDetailPageParam) throws Exception {
        PageEntity<TradingDetail> paging = tradingDetailService.getTradingDetailPageList(tradingDetailPageParam);
        return ResultEntity.success(paging);
    }

    /**
     * 交易明细表列表
     */
    @PostMapping("/getList")
    @ApiOperation(value = "交易明细表列表", response = TradingDetail.class)
    @ApiOperationSupport(ignoreParameters = {"tradingDetailPageParam.pageIndex","tradingDetailPageParam.pageSorts","tradingDetailPageParam.pageSize"})
    public ResultEntity<List<TradingDetail>> getTradingDetailList(@Validated @RequestBody TradingDetailPageParam tradingDetailPageParam) throws Exception {
        List<TradingDetail> list = tradingDetailService.getTradingDetailList(tradingDetailPageParam);
        return ResultEntity.success(list);
    }

}


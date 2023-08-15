package com.chain.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.chain.validator.groups.Update;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 交易明细表
 *
 * @author 易樊
 * @since 2023-07-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("db_trading_detail")
@ApiModel(value = "TradingDetail对象")
public class TradingDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "交易ID不能为空")
    @ApiModelProperty("交易ID")
    private Long id;

    @ApiModelProperty("委托ID")
    private Long tradingId;

    @ApiModelProperty("交易时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transactionTime;

    @ApiModelProperty("业务类型")
    private String category;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("成交量")
    private BigDecimal quantity;

    @ApiModelProperty("单位")
    private String union;

    @ApiModelProperty("价格")
    private BigDecimal unionPrice;

    @ApiModelProperty("交易额")
    private BigDecimal price;

    @ApiModelProperty("流动性方案")
    private String liquidity;

    @ApiModelProperty("手续费")
    private BigDecimal handlingCharge;

    @ApiModelProperty("单位")
    private String handlingUnion;

}

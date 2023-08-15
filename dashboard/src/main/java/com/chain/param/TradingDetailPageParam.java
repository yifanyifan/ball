package com.chain.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.chain.common.BasePageParam;

/**
 * <pre>
 * 交易明细表 分页参数对象
 * </pre>
 *
 * @author 易樊
 * @date 2023-07-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "交易明细表分页参数")
public class TradingDetailPageParam extends BasePageParam {
    private static final long serialVersionUID = 1L;
}

package com.chain.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.chain.base.BasePageParam;

/**
 * <pre>
 * 历史委托表 分页参数对象
 * </pre>
 *
 * @author 易樊
 * @date 2023-07-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "历史委托表分页参数")
public class TradingPageParam extends BasePageParam {
    private static final long serialVersionUID = 1L;
}

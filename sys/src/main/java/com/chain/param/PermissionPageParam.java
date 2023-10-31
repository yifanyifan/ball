package com.chain.param;

import com.chain.base.BasePageParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author 易樊
 * @date 2023-07-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class PermissionPageParam extends BasePageParam {
    private static final long serialVersionUID = 1L;
}

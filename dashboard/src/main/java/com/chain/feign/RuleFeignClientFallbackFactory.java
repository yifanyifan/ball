package com.chain.feign;

import com.chain.common.ResultEntity;
import com.chain.dto.RoleDTO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author yzy
 * @date 2022年09月29日 11:28
 */
@Component
public class RuleFeignClientFallbackFactory implements FallbackFactory<RuleFeign> {
    @Override
    public RuleFeign create(Throwable throwable) {
        return new RuleFeign() {
            @Override
            public ResultEntity<Boolean> create(RoleDTO roleDTO) {
                System.out.println("===========>进入了降级");
                return null;
            }
        };
    }
}

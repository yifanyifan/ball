package com.chain.feign.sys;

import com.chain.common.ResultEntity;
import com.chain.dto.RoleDTO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RuleFeignFallbackFactory implements FallbackFactory<RuleFeign> {
    private final static Logger logger = LoggerFactory.getLogger(RuleFeignFallbackFactory.class);

    @Override
    public RuleFeign create(Throwable throwable) {
        return new RuleFeign() {
            @Override
            public ResultEntity<Boolean> create(RoleDTO roleDTO) {
                logger.info("\n===========>进入了降级，class：" + this.getClass().getName() + "，method：create，errorMsg：" + throwable.getMessage());
                return null;
            }
        };
    }
}

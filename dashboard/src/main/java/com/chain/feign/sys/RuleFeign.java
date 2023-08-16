package com.chain.feign.sys;

import com.chain.common.ResultEntity;
import com.chain.dto.RoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "sys", fallbackFactory = RuleFeignFallbackFactory.class)
public interface RuleFeign {
    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    ResultEntity<Boolean> create(RoleDTO roleDTO);
}

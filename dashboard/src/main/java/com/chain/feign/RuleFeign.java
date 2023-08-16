package com.chain.feign;

import com.chain.common.ResultEntity;
import com.chain.dto.RoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yzy
 * @date 2022年09月29日 11:28
 */
@Component
@FeignClient(value = "sys")
public interface RuleFeign {
    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    ResultEntity<Boolean> create(RoleDTO roleDTO);
}

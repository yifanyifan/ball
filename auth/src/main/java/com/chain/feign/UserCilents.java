package com.chain.feign;

import com.chain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "sys")
public interface UserCilents {
    @RequestMapping(value = "/user/loadByUsername", method = RequestMethod.GET)
    UserDTO loadUserByUsername(@RequestParam String username);
}

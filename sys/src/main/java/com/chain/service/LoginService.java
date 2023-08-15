package com.chain.service;


import com.chain.common.ResultEntity;
import com.chain.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务类
 *
 * @author 易樊
 * @since 2022-04-07
 */
public interface LoginService {
    ResultEntity login(String username, String password, HttpServletRequest request) throws Exception;

    User getCurrentUser(HttpServletRequest request);
}

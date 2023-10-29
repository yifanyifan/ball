package com.chain.config;

import com.chain.common.BallException;
import com.chain.common.Constant;
import com.chain.common.ResultEntityEnum;
import com.chain.dto.UserDTO;
import com.chain.entity.SecurityUser;
import com.chain.feign.UserCilents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 设置用户验证服务。
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserCilents userCilent;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // String password = passwordEncoder.encode("123456789");
        String clientId = request.getParameter("client_id");

        UserDTO userDTO = null;
        if (Constant.ADMIN_CLIENT_ID.equals(clientId)) {
            userDTO = userCilent.loadUserByUsername(username);
        } else {
            //userDto = memberService.loadUserByUsername(username);
        }

        if (userDTO == null) {
            throw new BallException(ResultEntityEnum.USERNAME_PASSWORD_ERROR);
        }
        userDTO.setClientId(clientId);
        SecurityUser securityUser = new SecurityUser(userDTO);

        if (!securityUser.isEnabled()) {
            throw new BallException(ResultEntityEnum.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new BallException(ResultEntityEnum.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new BallException(ResultEntityEnum.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new BallException(ResultEntityEnum.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}

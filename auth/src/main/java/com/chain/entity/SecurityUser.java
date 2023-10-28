package com.chain.entity;

import com.chain.dto.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("登录名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户状态")
    private Boolean enabled;

    @ApiModelProperty("登录客户端ID")
    private String clientId;

    private List<SimpleGrantedAuthority> authorities;

    public SecurityUser() {
    }

    public SecurityUser(UserDTO userDto) {
        this.setId(userDto.getId());
        this.setUsername(userDto.getUsername());
        this.setPassword(userDto.getPassword());
        this.setEnabled(userDto.getIsEnable());
        this.setClientId(userDto.getClientId());
        if (userDto.getRoleDTOList() != null) {
            authorities = new ArrayList<>();
            userDto.getRoleDTOList().forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getName())));
            userDto.getPermissionList().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}

package com.example.dto;

import com.example.model.Permission;
import com.example.model.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginUser extends SysUser implements UserDetails {
    private static final long serialVersionUID = -7041017830069888665L;

    private List<Permission> permissions;
    private String token;


    /**@ClassName
     *@Description:    登陆时间戳（毫秒）
     *@Data 2019/3/24
     *Author censhaojie
     */
    private Long loginTime;

    /**@ClassName
     *@Description:      过期时间戳
     *@Data 2019/3/24
     *Author censhaojie
     */
    private Long expireTime;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }

    /**@ClassName isAccountNonExpired
     *@Description:  账户是否未过期
     *@Data 2019/3/24
     *Author censhaojie
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**@ClassName isAccountNonLocked
     *@Description:   账户是否未锁定
     *@Data 2019/3/24
     *Author censhaojie
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return getStatus() != Status.LOCKED;
    }

    /**@ClassName isCredentialsNonExpired
     *@Description:     密码是否未过期
     *@Data 2019/3/24
     *Author censhaojie
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**@ClassName isEnabled
     *@Description:     账户是否激活
     *@Data 2019/3/24
     *Author censhaojie
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }


}
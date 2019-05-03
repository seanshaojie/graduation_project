package com.example.service.impl;

import com.example.dao.PermissionDao;
import com.example.dto.LoginUser;
import com.example.model.Permission;
import com.example.model.SysUser;
import com.example.model.SysUser.Status;
import com.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionDao permissionDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//前的username
        SysUser sysUser=userService.getUser(username);
        if (sysUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        } else if (sysUser.getStatus() == Status.LOCKED) {
            throw new LockedException("用户被锁定,请联系管理员");
        } else if (sysUser.getStatus() == Status.DISABLED) {
            throw new DisabledException("用户已作废");
        }
        List<Permission> permissions=permissionDao.listByUserId(sysUser.getId());
        LoginUser loginUser=new LoginUser();
        BeanUtils.copyProperties(sysUser,loginUser);
        loginUser.setPermissions(permissions);
        return loginUser;
    }
}

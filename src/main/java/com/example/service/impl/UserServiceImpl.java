package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.dto.UserDto;
import com.example.model.SysUser;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired //对密码进行加密(encode)与密码匹配(matches)
    private BCryptPasswordEncoder passwordEncoder;//采用SHA-256 +随机盐+密钥对密码进行加密

    @Override
    public SysUser getUser(String username) {

        return userDao.getUser(username);


    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        SysUser user=userDao.getUser(username);
        if(user==null){
            throw new IllegalArgumentException("用户不存在");
        }
        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            throw new IllegalArgumentException("旧密码错误");
        }else{
            userDao.changePassword(user.getId(),passwordEncoder.encode(newPassword));
        }

    }

    @Override
    public SysUser update(UserDto userDto) {
        userDao.update(userDto);
        saveUserRole(userDto.getId(),userDto.getRoleIds());
        return userDto;
    }

    @Override
    public SysUser saveUser(UserDto userDto) {
        SysUser sysUser=userDto;
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUser.setStatus(SysUser.Status.VALID);
        userDao.save(sysUser);
        saveUserRole(userDto.getId(),userDto.getRoleIds());
        return sysUser;
    }

    private void saveUserRole(Long id, List<Long> roleIds) {
        if(roleIds!=null){
            userDao.deleteUserRole(id);
            if(!CollectionUtils.isEmpty(roleIds)){
                userDao.saveUserRoles(id,roleIds);
            }
        }
    }
}

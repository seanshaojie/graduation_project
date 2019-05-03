package com.example.service;

import com.example.dto.UserDto;
import com.example.model.SysUser;

import java.util.List;
import java.util.Map;

public interface   UserService {

    SysUser getUser(String username);

    void changePassword(String username, String oldPassword, String newPassword);

    SysUser update(UserDto userDto);

    SysUser saveUser(UserDto userDto);
}

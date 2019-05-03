package com.example.dto;

import com.example.model.SysUser;

import java.util.List;

public class UserDto extends SysUser {
    private static final long serialVersionUID = -8644601341762941897L;
    private  List<Long> roleIds;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}

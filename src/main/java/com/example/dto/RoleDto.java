package com.example.dto;

import com.example.model.Permission;
import com.example.model.Role;

import java.util.List;

public class RoleDto extends Role {

    private static final long serialVersionUID = 7493318744446739849L;

    private   List<Long> permissionIds;

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}

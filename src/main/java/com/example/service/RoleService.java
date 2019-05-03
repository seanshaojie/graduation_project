package com.example.service;

import com.example.dto.RoleDto;

public interface RoleService {
    void saveRole(RoleDto roledto);

    void deleteRole(Long id);
}

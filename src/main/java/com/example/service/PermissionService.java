package com.example.service;

import com.example.model.Permission;

import java.util.List;

public interface PermissionService {

    void save(Permission permission);

    void update(Permission permission);

    void delete(Long id);
}

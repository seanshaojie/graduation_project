package com.example.service.impl;

import com.example.dao.RoleDao;
import com.example.dto.RoleDto;
import com.example.model.Role;
import com.example.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public void saveRole(RoleDto roledto) {
        Role role = new Role();
        BeanUtils.copyProperties(roledto,role);
        List<Long>  permissionIds = roledto.getPermissionIds();
        permissionIds.remove(0L);
        if(role.getId() != null){
            update(role,permissionIds);
        }else{
            save(role,permissionIds);
        }
    }

    @Override
    public void deleteRole(Long id) {
        roleDao.deleteRolePermission(id);
        roleDao.deleteRoleUser(id);
        roleDao.delete(id);
    }

    private void save(Role role, List<Long> permissionIds) {
        Role r = roleDao.getByName(role.getName());
        if(r != null){
           throw  new IllegalArgumentException(role.getName()+"已存在");
        }
        roleDao.save(role);
        if(!CollectionUtils.isEmpty(permissionIds)){
            setRolePermission(role.getId(),permissionIds);
        }
    }

    private void update(Role role, List<Long> permissionIds) {
        Role r = roleDao.getByName(role.getName());
        if(r != null&&r.getId() != role.getId()){
            throw new IllegalArgumentException(role.getName()+"已存在");
        }
        roleDao.update(role);
        if(!CollectionUtils.isEmpty(permissionIds)){
            roleDao.deleteRolePermission(role.getId());
        setRolePermission(role.getId(),permissionIds);
        }
}


    private void setRolePermission(Long roleId, List<Long> permissionIds) {
        if(permissionIds != null){
            roleDao.deleteRolePermission(roleId);
            if (!CollectionUtils.isEmpty(permissionIds)){
                roleDao.saveRolePermissions(roleId,permissionIds);
            }
        }
    }
}

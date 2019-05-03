package com.example.dao;

import com.example.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface RoleDao {

    @Select("select * from sys_role r inner join sys_role_user ru on r.id = ru.roleId where ru.userId = #{userId}")
    List<Role> listByUserId(Long userId);

    int count(@Param("params") Map<String, Object> params);

    List<Role> list(@Param("params") Map<String,Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("select * from sys_role where id=#{id}")
    Role getById(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_role(name,description,createTime,updateTime) values(#{name},#{description},now(),now())")
    int save(Role role);

    int saveRolePermissions(@Param("roleId") Long roleId,@Param("permissionIds") List<Long> permissionIds);

    @Update("update sys_role set name = #{name}, description = #{description},updateTime = now() where id = #{id}")
    int update(Role role);

    @Select("select * from sys_role t where t.name = #{name}")
    Role getByName(String name);

    @Delete("delete from sys_role_permission where roleId = #{id}")
    int deleteRolePermission(Long roleId);

    @Delete("delete from sys_role_user where roleId = #{roleId}")
    int deleteRoleUser(Long roleId);

    @Delete("delete from sys_role where id = #{id}")
    int delete(Long id);
}

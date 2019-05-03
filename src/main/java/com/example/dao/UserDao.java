package com.example.dao;

import com.example.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, telephone, email, birthday, sex, status, createTime, updateTime) values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, now(), now())")
    int save(SysUser user);

    @Select("select * from sys_user t where t.username=#{username}")
    public SysUser getUser(String username);

    @Update("update sys_user t set t.password=#{password} where t.id=#{id}")
    int changePassword(@Param("id") Long id, @Param("password") String password);

    @Delete("delete from sys_role_user where userId = #{id}")
    int deleteUserRole(Long id);

    Integer count(@Param("params") Map<String, Object> params);

    List<SysUser> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    int saveUserRoles(@Param("id") Long id, @Param("roleIds") List<Long> roleIds);

    int update(SysUser user);


    @Select("select * from sys_user t where t.id = #{id}")
    SysUser getUserById(String id);
}

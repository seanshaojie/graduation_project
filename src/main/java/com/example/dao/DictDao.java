package com.example.dao;

import com.example.model.Dict;
import com.example.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DictDao {

    @Select("select * from t_dict t where t.type = #{type}")
    List<Dict> listByType(String type);

    @Select("select * from t_dict t where t.id = #{id}")
    Dict getDictById(String id);

    int count(@Param("params") Map<String,Object> params);


    List<Dict> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);


    int update(Dict dict);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into t_dict(type, k, val, createTime, updateTime) values(#{type},#{k},#{val},now(),now())")
    int save(Dict dict);

    @Delete("delete from t_dict where id = #{id}")
    int delete(Long id);
}

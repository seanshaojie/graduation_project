package com.example.dao;

import com.example.model.FileInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileInfoDao {

    @Select("select * from file_info t where t.id = #{id}")
    public FileInfo getById(String id);

    @Update("update file_info t set t.updateTime = now() where t.id = #{id}")
    int update(FileInfo fileInfo);

    @Insert("insert into file_info(id, contentType, size, path, url, type, createTime, updateTime) values(#{id}, #{contentType}, #{size}, #{path}, #{url}, #{type}, now(), now())")
    int save(FileInfo fileInfo);


    List<FileInfo> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
                        @Param("limit") Integer limit);
    int count(@Param("params") Map<String, Object> params);

    @Delete("delete from file_into where id = #{id}")
    int  delete(String id);
}

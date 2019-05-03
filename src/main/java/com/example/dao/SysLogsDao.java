package com.example.dao;

import com.example.model.SysLogs;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysLogsDao {

    @Insert("insert into sys_logs(userId, module, flag, remark, createTime) values(#{user.id}, #{module}, #{flag}, #{remark}, now())")
    int save(SysLogs sysLogs);

    @Delete("delete from sys_logs where createTime <= #{time}")
    int deleteLogs(String time);

    int count(@Param("params") Map<String,Object> params);

    List<SysLogs> list(@Param("params")Map<String,Object> params, @Param("offset") Integer offset,@Param("limit") Integer limit);
}

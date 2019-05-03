package com.example.controller;


import com.example.annotation.LogAnnotation;
import com.example.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.jni.Mmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import java.util.*;

@Api(tags = "excel下载")
@RestController
@RequestMapping("/excels")
public class ExcelController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LogAnnotation
    @ApiOperation("校验sql，并返回sql返回的数量")
    @PostMapping("/sql-count")
    public Integer checkSql(String sql){
        sql = getAndchecksql(sql);
        Integer count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count(1) from ("+sql+") t",Integer.class);

        } catch (DataAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return count;
    }


    private String getAndchecksql(String sql) {
        sql = sql.trim().toLowerCase();
        if(sql.endsWith(";") || sql.endsWith("；")){
            sql.substring(0,sql.length()-1);
        }
        if(!sql.startsWith("select")){
            throw new IllegalArgumentException("仅支持select开头的语句");
        }
        return sql;
    }

    @LogAnnotation
    @ApiOperation("根据sql在页面显示结果")
    @PostMapping("/show-datas")
    @PreAuthorize("hasAuthority('excel:show:datas')")
    public List<Object[]> getDatas(String sql){
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if(list != null){
            Map<String,Object> map = list.get(0);
            String[] header =new String[map.size()];
            int i=0;
            for (String key:map.keySet()){
                header[i++] = key;
            }
            List<Object[]> ls = new ArrayList<>(list.size());
            ls.add(header);

            for (Map<String,Object> m:list){
                Object[] data = new Object[header.length];
                for (int l=0;l<header.length;l++){
                data[l] = m.get(header[l]);
                }
                ls.add(data);
            }
            return ls;
        }
        return Collections.emptyList();
    }


    @LogAnnotation
    @ApiOperation("根据sql导出excel")
    @PostMapping
    @PreAuthorize("hasAuthority('excel:down')")
    public void downloadExcel(String sql, String filename, HttpServletResponse response){
        sql = getAndchecksql(sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if(!CollectionUtils.isEmpty(list)){
            Map<String,Object> map = list.get(0);
            String[] headers = new String[map.size()];
            int i = 0;
            for (String key:map.keySet()){
                headers[i++] = key;
            }
            List<Object[]> datas = new ArrayList<>(list.size());
            for(Map<String,Object> m:list){
                Object[] data = new Object[headers.length];
                for (int j = 0;j<headers.length;j++){
                    data[j] = m.get(headers[j]);
                }
                datas.add(data);
            }
            ExcelUtil.excelExport(filename == null || filename.trim().length() <=0? DigestUtils.md5Hex(sql): filename,headers,datas,response);
        }
    }



}

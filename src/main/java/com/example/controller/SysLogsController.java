package com.example.controller;

import com.example.annotation.LogAnnotation;
import com.example.dao.SysLogsDao;
import com.example.model.Page.*;
import com.example.model.Page.PageTableHandler.*;
import com.example.model.SysLogs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "日志")
@RequestMapping("/logs")
@RestController
public class SysLogsController {

    @Autowired
    private SysLogsDao sysLogsDao;

    @GetMapping
    @ApiOperation(value = "日志列表")
    public PageTableResponse getList(PageTableRequest pageTableRequest ){
        return  new PageTableHandler(new CountHandler() {
            @Override
            public int count(PageTableRequest request) {
                return sysLogsDao.count(request.getParams());
            }
        }, new ListHandler() {
            @Override
            public List<SysLogs> list(PageTableRequest request) {
                return sysLogsDao.list(request.getParams(),request.getOffset(),request.getLimit());
            }
        }).handle(pageTableRequest);
    }
}

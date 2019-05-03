package com.example.controller;


import com.example.annotation.LogAnnotation;
import com.example.dao.FileInfoDao;
import com.example.model.FileInfo;
import com.example.model.Page.PageTableHandler;
import com.example.model.Page.PageTableRequest;
import com.example.model.Page.PageTableResponse;
import com.example.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "文件")
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileInfoDao fileInfoDao;

    @PostMapping
    @LogAnnotation
    @ApiOperation(value = "文件上传")
    @ApiImplicitParam(name = "file",value = "文件上传",dataType = "file",required = true)
    public FileInfo uploadFile(MultipartFile file) throws IOException {
        return fileService.save(file);
    }

    @GetMapping
    @ApiOperation(value = "文件查询")
    @PreAuthorize("hasAuthority('sys:file:query')")
    public PageTableResponse listFiles(PageTableRequest request){
        return new PageTableHandler(new PageTableHandler.CountHandler() {
            @Override
            public int count(PageTableRequest request) {
                return fileInfoDao.count(request.getParams());
            }
        }, new PageTableHandler.ListHandler() {
            @Override
            public List<FileInfo> list(PageTableRequest request) {
                return fileInfoDao.list(request.getParams(),request.getOffset(),request.getLimit());
            }
        }).handle(request);
    }


    @LogAnnotation
    @DeleteMapping("/{id}")
    @ApiOperation(value = "文件删除")
    @PreAuthorize("hasAuthority('sys:file:del')")
    public void delete(@PathVariable String id) {
        fileService.delete(id);
    }

}

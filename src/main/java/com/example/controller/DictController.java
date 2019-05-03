package com.example.controller;

import com.example.annotation.LogAnnotation;
import com.example.dao.DictDao;
import com.example.model.Dict;
import com.example.model.Page.PageTableHandler;
import com.example.model.Page.PageTableHandler.*;
import com.example.model.Page.PageTableRequest;
import com.example.model.Page.PageTableResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "数据字典")
@RestController
@RequestMapping("/dicts")
public class DictController {

    @Autowired
    private DictDao dictDao;


    @GetMapping(params = "type")
    @ApiImplicitParam(name = "type",value = "字典类型",dataType = "String",required = true)
    public List<Dict> ListByType(String type){
        return dictDao.listByType(type);
    }


    @GetMapping("/list")
    @ApiOperation(value = "列表")
    @PreAuthorize("hasAuthority('dict:query')")
    public PageTableResponse list(PageTableRequest pageTableRequest){
        return new PageTableHandler(new CountHandler() {
            @Override
            public int count(PageTableRequest request) {
                return dictDao.count(request.getParams());
            }
        }, new ListHandler() {
            @Override
            public List<Dict> list(PageTableRequest request) {
                return dictDao.list(request.getParams(),request.getOffset(),request.getLimit());
            }
        }).handle(pageTableRequest);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    @ApiImplicitParam(name = "id",value = "字典id",dataType = "String",required = true)
    public Dict getDictById(@PathVariable String id){
        return  dictDao.getDictById(id);
    }

    @PutMapping
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAuthority('dict:add')")
    public Dict update(@RequestBody Dict dict){
         dictDao.update(dict);
         return dict;
    }

    @PostMapping
    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('dict:add')")
    public Dict save(@RequestBody Dict dict){
        dictDao.save(dict);
        return dict;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('dict:del')")
    public  void delete(@PathVariable Long id){
          dictDao.delete(id);
    }
}

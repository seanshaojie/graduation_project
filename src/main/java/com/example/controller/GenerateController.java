package com.example.controller;




import com.example.annotation.LogAnnotation;
import com.example.dto.BeanField;
import com.example.dto.GenerateDetail;
import com.example.dto.GenerateInput;
import com.example.service.GenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "代码生成")
@RestController
@RequestMapping("/generate")
public class GenerateController {

    @Autowired
    private GenerateService generateService;


    @ApiOperation("根据表名显示表信息")
    @GetMapping(params = {"tableName"})
    @PreAuthorize("hasAuthority('generate:edit')")
    public GenerateDetail generateDetail(String tableName){
        GenerateDetail detail = new GenerateDetail();
        detail.setBeanName(generateService.upperFirstChar(tableName));////转成驼峰并大写第一个字母
        List<BeanField> fields = generateService.listBeanField(tableName);
        detail.setFields(fields);
        return detail;
    }

    @LogAnnotation
    @ApiOperation("生成代码")
    @PostMapping
    @PreAuthorize("hasAuthority('generate:edit')")
    public void save(@RequestBody GenerateInput generateInput){
        generateService.save(generateInput);
    }

}

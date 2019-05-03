package com.example.service;

import com.example.dto.BeanField;
import com.example.dto.GenerateInput;

import java.util.List;

public interface GenerateService  {
    
    String upperFirstChar(String tableName);

    List<BeanField> listBeanField(String tableName);

    void save(GenerateInput generateInput);
}

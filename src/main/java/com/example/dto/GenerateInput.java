package com.example.dto;

import java.io.Serializable;
import java.util.List;

public class GenerateInput implements Serializable {
    private static final long serialVersionUID = 7444990048425979980L;

    private String path;//保存路径
    private String tableName;//表名
    private String beanPackageName;//bean的包名
    private String beanName;//java类
    private String daoPackageName;//dao的包名
    private String daoName;//dao类名
    private String controllerPkgName;//controller包名
    private String controllerName;//controller类名
    private List<String> columnNames;//字段名
    private List<String> beanFieldName;//属性名
    private List<String> beanFieldType;//成员变量
    private List<String> beanFieldValue;//默认值

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tablename) {
        this.tableName = tablename;
    }

    public String getBeanPackageName() {
        return beanPackageName;
    }

    public void setBeanPackageName(String beanPackageName) {
        this.beanPackageName = beanPackageName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getDaoPackageName() {
        return daoPackageName;
    }

    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public String getControllerPkgName() {
        return controllerPkgName;
    }

    public void setControllerPkgName(String controllerPkgName) {
        this.controllerPkgName = controllerPkgName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<String> getBeanFieldName() {
        return beanFieldName;
    }

    public void setBeanFieldName(List<String> beanFieldName) {
        this.beanFieldName = beanFieldName;
    }

    public List<String> getBeanFieldType() {
        return beanFieldType;
    }

    public void setBeanFieldType(List<String> beanFieldType) {
        this.beanFieldType = beanFieldType;
    }

    public List<String> getBeanFieldValue() {
        return beanFieldValue;
    }

    public void setBeanFieldValue(List<String> beanFieldValue) {
        this.beanFieldValue = beanFieldValue;
    }
}

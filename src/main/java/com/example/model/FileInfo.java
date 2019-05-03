package com.example.model;

import io.swagger.annotations.ApiModelProperty;

public class FileInfo extends BaseEntity<String> {

    private static final long serialVersionUID = 2268867282946363936L;

    @ApiModelProperty
    private String contentType;

    private long size;

    private String path;

    private String url;

    private Integer type;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

package com.example.service;

import com.example.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {
    FileInfo save(MultipartFile file) throws IOException;
    void delete(String id);
}

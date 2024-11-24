package com.electroinc.store.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String UploadImage(MultipartFile file, String path);

    InputStream getResource(String path, String name) throws FileNotFoundException;
}

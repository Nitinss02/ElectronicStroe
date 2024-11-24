package com.electroinc.store.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electroinc.store.Exception.BadApiRequest;
import com.electroinc.store.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String UploadImage(MultipartFile file, String path) {
        String originalFilename = file.getOriginalFilename();
        logger.info("File NAME is {}", originalFilename);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtesion = fileName + extension;
        String FullPathWithFileName = path + fileNameWithExtesion;

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")
                || extension.equalsIgnoreCase(".jpeg")) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            try {
                Files.copy(file.getInputStream(), Paths.get(FullPathWithFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileNameWithExtesion;

        } else {
            throw new BadApiRequest("file with this extestion " + extension + " Not Allowed");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

}

package com.e_commerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImp implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String orginalFileName= file.getOriginalFilename();
        String randomID= UUID.randomUUID().toString();
        String fileName=randomID.concat(orginalFileName.substring(orginalFileName.lastIndexOf(".")));
        String filePath=path+ File.separator+fileName;
        File folder=new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}

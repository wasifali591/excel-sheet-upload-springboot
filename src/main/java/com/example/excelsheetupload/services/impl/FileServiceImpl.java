package com.example.excelsheetupload.services.impl;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.repositories.FileRepository;
import com.example.excelsheetupload.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<File> findAllFile(){
        System.out.println(fileRepository==null);
        return fileRepository.findAll();

    }

    @Override
    public File saveFile(File file){
////        if (!fileRepository.findByName(file.getName()).isEmpty()) {
//            throw new RuntimeException("File already exists.");
//        }
        return fileRepository.save(file);
    }

    @Override
    @Transactional
    public void deleteFile(Long id) throws IOException {
        String path = fileRepository.getById(id).getPath();
        Path filePath = Paths.get(path);
        Files.delete(filePath);
        fileRepository.deleteFileById(id);
    }

    @Override
    public File getFileById(Long id){
        return fileRepository.getById(id);
    }

}

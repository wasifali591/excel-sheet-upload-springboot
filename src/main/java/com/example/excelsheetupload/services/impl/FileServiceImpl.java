package com.example.excelsheetupload.services.impl;

import java.util.List;

import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.repositories.FileRepository;
import com.example.excelsheetupload.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return fileRepository.save(file);
    }

    @Override
    public void deleteFile(Long id){
        fileRepository.deleteFileById(id);
    }

}

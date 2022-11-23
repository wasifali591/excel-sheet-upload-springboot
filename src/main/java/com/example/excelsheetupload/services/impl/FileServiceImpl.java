package com.example.excelsheetupload.services.impl;

import java.util.List;

import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.repositories.FileRepository;
import com.example.excelsheetupload.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    @Override
    public List<File> findAllFile(){
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

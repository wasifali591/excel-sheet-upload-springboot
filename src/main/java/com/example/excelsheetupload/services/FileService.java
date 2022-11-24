package com.example.excelsheetupload.services;

import java.util.List;

import com.example.excelsheetupload.entities.File;
import org.springframework.stereotype.Service;


public interface FileService {



    /**
     * Get all Files
     * @return List of Files
     */
     List<File> findAllFile();

    /**
     * save File
     * @return File
     */
     File saveFile(File file);

    /**
     * delete a single file by id
     * @param id for the selected file
     */
     void deleteFile(Long id);


}
package com.example.excelsheetupload.services;

import java.io.IOException;
import java.util.List;

import com.example.excelsheetupload.entities.File;


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
     void deleteFile(Long id) throws IOException;

    /**
     * delete selected file with id
     * @param id file id
     * @return file
     */
    File getFileById(Long id);
}
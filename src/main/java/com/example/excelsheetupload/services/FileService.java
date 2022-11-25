package com.example.excelsheetupload.services;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import java.io.IOException;
import java.util.List;

import com.example.excelsheetupload.entities.File;

/**
 * This is a service interface which provides required crud operation for {@link File}.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
public interface FileService {
    /**
     * This method is used to get all the {@link File}
     * @return List of File
     */
     List<File> findAllFile();

    /**
     * This method is used to created records for {@link File}
     * @param file
     * @return File
     */
     File saveFile(File file);

    /**
     * This method is used to delete a single {@link File} using Id
     *
     * @param id for the selected file
     */
     void deleteFile(Long id) throws IOException;

    /**
     * This method is used to get a single {@link File} using Id
     *
     * @param id file id
     * @return file
     */
    File getFileById(Long id);
}
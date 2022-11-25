package com.example.excelsheetupload.services;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.File;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.example.excelsheetupload.exceptions.StorageException;
import com.example.excelsheetupload.exceptions.StorageFileNotFoundException;

/**
 * This is a service interface which provides required crud operation for File Storage.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
public interface FileStorageService {
    /**
     * Initialize local file storage.
     *
     * @throws StorageException
     */
    void init() throws StorageException;

    /**
     * Receives a @MultipartFile file and stores it into local file storage
     * location.
     *
     * @param file File to store
     * @throws StorageException
     * @throws MultipartException
     */
    File store(MultipartFile file) throws StorageException, MultipartException;

    /**
     * Returns a list of stored files within the local file storage.
     *
     * @return List of files
     * @throws StorageException
     */
//    Stream<Path> loadAll() throws StorageException;

    /**
     * Returns a given file as resource.
     *
     * @param filename File name
     * @return File resource
     * @throws StorageFileNotFoundException
     */
//    Resource loadAsResource(String filename) throws StorageFileNotFoundException;

    /**
     * Deletes all files within the local file storage.
     */
    void deleteAll();

}

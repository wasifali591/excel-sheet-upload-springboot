package com.example.excelsheetupload.services.impl;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.example.excelsheetupload.exceptions.StorageException;
import com.example.excelsheetupload.exceptions.StorageFileNotFoundException;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private Path filesStoragePath;

    @Autowired
    private FileServiceImpl fileService;

    public FileStorageServiceImpl(@Value("${files.storage.location}") String filesStorageLocation) {
        // Initialize files upload location from properties
        this.filesStoragePath = Paths.get(filesStorageLocation);
    }

    @Override
    public void init() throws StorageException {
        try {
            // Create directories on files location, also creates non existing directories
            Files.createDirectories(filesStoragePath);
        } catch (IOException ex) {
            throw new StorageException("Could not initialize storage.", ex);
        }
    }

    @Override
    public File store(MultipartFile file) throws StorageException, MultipartException {
        File fileData = new File();
        try {
            // Check for empty file exception
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            // Get the absolute path for the file to upload
            Path destinationFile = this.filesStoragePath.resolve(Paths.get(file.getOriginalFilename())).normalize()
                    .toAbsolutePath();

            /**
             * save file information to databse
             */
            fileData.setName(file.getOriginalFilename());
            fileData.setPath(destinationFile.toString());


            // Security check that file upload path parent is same as storage location.
            // This checks that no path has been appended externally to upload file name.
            if (!destinationFile.getParent().equals(this.filesStoragePath.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }

            // Copy the file to actual storage location
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new StorageException("Failed to store file.", ex);
        }
        /**
         * save the file data and return
         */
        return (fileService.saveFile(fileData));
    }

//    @Override
//    public Stream<Path> loadAll() throws StorageException {
//        try {
//            /*
//             * Reads all files with the location up to a depth of 1 level and filters only
//             * files in the storage location. This way we generate a list of files within
//             * the storage location.
//             */
//            return Files.walk(this.filesStoragePath, 1).filter(path -> !path.equals(this.filesStoragePath))
//                    .map(this.filesStoragePath::relativize);
//        } catch (IOException ex) {
//            throw new StorageException("Failed to read stored files.", ex);
//        }
//    }

//    @Override
//    public Resource loadAsResource(String filename) throws StorageFileNotFoundException {
//        try {
//            // Get file from path and resolve file to resource
//            Path file = filesStoragePath.resolve(filename);
//            Resource resource = new UrlResource(file.toUri());
//
//            // Return if resource exists and readable else throw exception
//            if (resource.exists() && resource.isReadable()) {
//                return resource;
//            } else {
//                throw new StorageFileNotFoundException("Could not read file " + filename);
//            }
//        } catch (MalformedURLException ex) {
//            throw new StorageFileNotFoundException("Could not read file " + filename + " from storage.", ex);
//        }
//    }

    @Override
    public void deleteAll() {
        // Deletes all files from the storage root directory
        FileSystemUtils.deleteRecursively(filesStoragePath.toFile());
    }

}

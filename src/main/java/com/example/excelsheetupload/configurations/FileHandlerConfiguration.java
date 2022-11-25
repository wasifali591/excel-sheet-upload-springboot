package com.example.excelsheetupload.configurations;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.services.impl.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This is a configuration class for file upload
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
@Component
public class FileHandlerConfiguration implements CommandLineRunner {
    @Autowired
    private FileStorageServiceImpl fileStorage;

    @Override
    public void run(String... args) throws Exception {
        /**
         * Initial file storage setup i.e delete all files in storage and initialize
         * storage freshly.
         */
        fileStorage.deleteAll();
        fileStorage.init();
    }
}

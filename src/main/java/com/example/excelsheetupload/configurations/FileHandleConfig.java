package com.example.excelsheetupload.configurations;

import com.example.excelsheetupload.services.impl.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FileHandleConfig implements CommandLineRunner {
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

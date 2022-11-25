package com.example.excelsheetupload.services.impl;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */


import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.entities.Log;
import com.example.excelsheetupload.repositories.LogRepository;
import com.example.excelsheetupload.services.FileService;
import com.example.excelsheetupload.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private FileService fileService;
    /**
     * This method is used to create record
     * @param log
     * @return Log
     */
    @Override
    public Log add(Log log) {
        return logRepository.save(log);
    }
    /**
     * This method is used to get all the {@link Log}
     * @return List of Log
     */
    @Override
    public List<Log> getAll() {
        return logRepository.findAll();
    }
}

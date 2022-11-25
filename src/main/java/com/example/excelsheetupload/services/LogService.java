package com.example.excelsheetupload.services;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.entities.Log;

import java.util.List;
/**
 * This is a service interface which provides required crud operation for {@link Log}.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
public interface LogService {
    /**
     * This method is used to create record
     *
     * @param log
     * @return Log
     */
    public Log add(Log log);

    /**
     * This method is used to get all the {@link Log}
     * @return List of Log
     */
    public List<Log> getAll();
}

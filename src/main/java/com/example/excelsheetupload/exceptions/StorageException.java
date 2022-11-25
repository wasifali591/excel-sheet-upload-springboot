package com.example.excelsheetupload.exceptions;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */


/**
 * This is a custom exception class.
 * It extends {@link Exception}.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
public class StorageException extends Exception{
    private static final long serialVersionUID = 1L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

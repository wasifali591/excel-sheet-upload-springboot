package com.example.excelsheetupload.exceptions;

public class StorageFileNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.example.excelsheetupload.dtos.responses;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a dto class for custom response handler.
 * It is used to generate response.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 23/11/2022
 */
public class ApiResponseDto {
    public ResponseEntity<Object> generateResponse(HttpStatus status, Object body, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.value());
        map.put("payload", body);
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }
}

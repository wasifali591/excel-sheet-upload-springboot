package com.example.excelsheetupload.repositories;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is a repository interface which provides required crud operation for {@link Log}.
 * its extends {@link JpaRepository}
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
public interface LogRepository extends JpaRepository<Log, Long> {
}

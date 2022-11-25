package com.example.excelsheetupload.repositories;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This is a repository interface which provides required crud operation for {@link File}.
 * its extends {@link JpaRepository}
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
public interface FileRepository extends JpaRepository<File, Long> {

    /**
     * Delete a {@link File} by id
     *
     * @param id
     */
    void deleteFileById(Long id);

    /**
     * Find all the {@link File} using name
     * @param name
     * @return List of File
     */
    List<File> findByName(String name);

}

package com.example.excelsheetupload.repositories;

import com.example.excelsheetupload.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FileRepository extends JpaRepository<File, Long> {

    void deleteFileById(Long id);
    List<File> findByName(String name);

}

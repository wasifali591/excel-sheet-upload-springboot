package com.example.excelsheetupload.entities;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * This class is an entity class with 5 member variables.
 * It contains the details of the files accessed by any user
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/2022
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "It contains details of Files access by any user")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;
    /**
     * It represents the file name.
     */
    private String fileName;
    /**
     * It represents the username who has accessed the file.
     */
    private String userName;

    /**
     * It represents record created date.
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdDate;
}

package com.example.excelsheetupload.entities;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * This class is an entity class with 6 member variables.
 * It contains address of different users.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/2022
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "It contains details of Employees which will come from the Excel Sheet")
public class Employee {

    /**
     * it represents the unique id of every record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;
    /**
     * it represents the name of the employee
     */
    private String name;
    /**
     * it represents the gender of the employee
     */
    private String gender;
    /**
     * It has one-to-many relation with {@link File} class.
     * It represents the related data for a particular file.
     */
    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;
    /**
     * It represents record created date.
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdDate;
    /**
     * It represents record updated date.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

}

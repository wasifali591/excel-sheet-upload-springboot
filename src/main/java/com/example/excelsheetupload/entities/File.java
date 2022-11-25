package com.example.excelsheetupload.entities;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * This class is an entity class with 5 member variables.
 * It contains the details of File.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/2022
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "It contains details of Files which will get updated by the user")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;
    private String name;
    private String path;
    /**
     * It has one-to-many relation with {@link Employee} class.
     * It represents business organization provided subscriptions pack.
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "file")
    private List<Employee> employees;
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
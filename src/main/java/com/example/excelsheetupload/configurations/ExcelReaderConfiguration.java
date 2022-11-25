package com.example.excelsheetupload.configurations;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.jobs.BatchJobListener;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;

/**
 * This is a configuration class for Batch Processing and steps
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
@Configuration
@EnableBatchProcessing
public class ExcelReaderConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PoiItemReader excelReader;

    /**
     * Creates a batch job to read employee data from CSV file, process the data and
     * write to a database. The job step chunks records and handles only a chunk of
     * 2 records at time. A job contains a flow of multiple steps to read, process
     * and write data items. This example just uses one step.
     *
     * @param jobBuilderFactory     Creates a job
     * @param stepBuilderFactory    Creates steps for the job
     * @param listener              Listener to handle job completion
     * @param employeeItemProcessor Process the data items
     * @param employeeItemWriter    Writes the data items
     * @return Batch job to handle employee data
     */
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   BatchJobListener listener, ItemProcessor<Employee, Employee> employeeItemProcessor, ItemWriter<Employee> employeeItemWriter) {

        /**
         * Create a step: Setup step name, chunk size, reader, processor and writer.
         * FILE -> READER -> PROCESSOR -> WRITER -> DATABASE
         */
        Step step = stepBuilderFactory.get("BATCH_STEP_1").<Employee, Employee>chunk(2).reader(excelReader)
                .processor(employeeItemProcessor).writer(employeeItemWriter).build();

        /**
         * Create a job: Setup job name, job completion listener and the flow of steps.
         * JOB -> STEP 1 -> STEP 2 -> END
         */
        Job job = jobBuilderFactory.get("BATCH_JOB_1").incrementer(new RunIdIncrementer()).listener(listener).flow(step)
                .end().build();

        return job;
    }
}

package com.example.excelsheetupload.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * Batch job execution status listener to handle pre-processing and
 * post-processing tasks.
 */
@Component
public class BatchJobListener extends JobExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(BatchJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        JobParameters params = jobExecution.getJobParameters();
//        logger.info("Employee data migration batch job started by {}", params.getString("BY_USER"));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            JobParameters params = jobExecution.getJobParameters();
//            logger.info("Employee data migration batch job completed by {}", params.getString("BY_USER"));

        }
    }

}

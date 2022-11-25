package com.example.excelsheetupload.controllers;

/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import com.example.excelsheetupload.dtos.responses.ApiResponseDto;
import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.entities.Log;
import com.example.excelsheetupload.exceptions.StorageException;
import com.example.excelsheetupload.exceptions.StorageFileNotFoundException;
import com.example.excelsheetupload.services.EmployeeService;
import com.example.excelsheetupload.services.FileService;
import com.example.excelsheetupload.services.FileStorageService;
import com.example.excelsheetupload.services.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a controller class of File Handling.
 * It processes the request and return the view as response.
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 25/11/22
 */
@RestController
@RequestMapping("/golify/api")
@Api(tags = "File Controller", value = "FileController", description = "Controller for File Management")
public class FileHandleController {

    @Autowired
    private FileService fileService;
    @Autowired
    private FileStorageService fileStorage;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    private LogService logService;

    private static final Logger logger = LoggerFactory.getLogger(FileHandleController.class);

    private Map<Long, JobExecution> jobTracker = new HashMap<>();

    /**
     * this method is used to upload a file
     * it will automatically create records for file
     * also will create records for the data, which are in the file
     *
     * @param file browse and upload the file
     * @return
     * @throws StorageException
     * @throws MultipartException
     * @throws SizeLimitExceededException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobExecutionAlreadyRunningException
     * @throws JobParametersInvalidException
     * @throws JobRestartException
     */
    @ApiOperation(value = "upload a file", notes = "Browse a file and store the records into database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File Updated Successfully")
    })
    @PostMapping("/file/upload")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file)
            throws StorageException, MultipartException, SizeLimitExceededException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        logger.trace("Uploading file...");
        // Upload file into storage location
        File fileData = fileStorage.store(file);
        logger.trace("Record created for the File {} ", file.getName());
        logger.trace("Starting Batch Processing...");
        startBatchJob(fileData);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, fileData, "File Updated Successfully");
    }

    /**
     * this method used to check file upload status
     *
     * @param fileId
     * @return json
     */
    @ApiOperation(value = "check file upload status",
            notes = "check file upload status for a specific file ", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping("/file/upload/status/{fileId}")
    public BatchStatus checkBatchStatus(@PathVariable Long fileId){
        logger.trace("Getting status of file upload. FileId: {} ", fileId);
        return jobTracker.get(fileId).getStatus();
        //Todo: check for null file id- add validation
    }


    /**
     * This method is used to get all files.
     *
     * @return json
     */
    @ApiOperation(value = "Get list of all files",
            notes = "View list of all files ", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping("files")
    public ResponseEntity<Object> getAllFiles(){
        logger.trace("Getting List of all Files");
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, fileService.findAllFile(),
                        "Successfully data retrieved");
    }

    /**
     * This method is used to get all records related to a specific file.
     *
     * @param fileId specific file id
     * @return json
     */
    @ApiOperation(value = "Get list of all records related to a specific file",
            notes = "View list of all records ", response = ArrayList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully data retrieved"),
            @ApiResponse(code = 404, message = "No data available!")
    })
    @GetMapping("file/{fileId}")
    public ResponseEntity<Object> getAllRecordsRelatedToFile(@PathVariable Long fileId){
        logger.trace("Getting all Employee details from a file with File Id: {} ", fileId);
        //Create Log for accessing the file in database
        File file = fileService.getFileById(fileId);
        logService.add(new Log(null, file.getName(), null, null));

        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, employeeService.getEmployeeByFileId(fileId),
                        "Successfully data retrieved");
    }

    /**
     * This method is used to delete a specific files and related records
     *
     * @param fileId - id of the entity to delete. Must not be null.
     * @return json
     */
    @ApiOperation(value = "Delete a file by id along with all the records related to that file", notes = "Provide a specific plan id to delete information ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Resource doesn't exist!")
    })
    @DeleteMapping("file/{fileId}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long fileId) throws IOException {
        logger.trace("Deleting file with FileId: {} ", fileId);
        fileService.deleteFile(fileId);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, null, "Successfully deleted");
    }


    /**
     * ======================================================================
     */

//    @PostMapping("/employee/{id}")
    public ResponseEntity<Object> addEmployee(@PathVariable Long id, @RequestBody Employee employee){
//        return ResponseEntity.ok().body(fileService.findAllFile());
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, employeeService.saveEmployee(employee),
                        "Successfully data saved");
    }

//    @GetMapping("/employee")
    public ResponseEntity<Object> addEmployee(){
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, employeeService.getEmployee(),
                        "Successfully data saved");
    }


    /**
     * This method is to trigger the batch job for migrating employee data from excel file
     * to database. Starts the batch job with some parameters i.e start time and
     * username for logging on completion of the job.
     *
     * @param file
     * @throws JobExecutionAlreadyRunningException
     * @throws JobRestartException
     * @throws JobInstanceAlreadyCompleteException
     * @throws JobParametersInvalidException
     */
    public void startBatchJob(File file) throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {

        // Setup some job parameters for logging
        Map<String, JobParameter> paramsMap = new HashMap<>();
        paramsMap.put("TIME", new JobParameter(new Date()));
        paramsMap.put("BY_USER", new JobParameter("Batch Admin User"));
        paramsMap.put("FILE_PATH", new JobParameter(file.getPath()));
        paramsMap.put("FILE_ID", new JobParameter(file.getId()));

        JobParameters params = new JobParameters(paramsMap);

        // Start batch job execution
        JobExecution jobExecution = jobLauncher.run(job, params);

        jobTracker.put(file.getId(), jobExecution);
    }






    /**
     * Exception handlers for various storage and file exceptions.
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFoundException(StorageFileNotFoundException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageException(StorageException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> handleMultipartException(MultipartException ex) {
        return new ResponseEntity<>("No valid file found with request.", HttpStatus.BAD_REQUEST);
    }
}

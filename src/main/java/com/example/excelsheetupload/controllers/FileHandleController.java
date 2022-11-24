package com.example.excelsheetupload.controllers;

import com.example.excelsheetupload.dtos.responses.ApiResponseDto;
import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.entities.File;
import com.example.excelsheetupload.services.impl.EmployeeServiceImpl;
import com.example.excelsheetupload.services.impl.FileServiceImpl;

import com.example.excelsheetupload.services.impl.FileStorageServiceImpl;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.example.excelsheetupload.exceptions.StorageException;
import com.example.excelsheetupload.exceptions.StorageFileNotFoundException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
//@Api(tags = "File Controller", value = "FileController", description = "Controller for File Management")
public class FileHandleController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private FileStorageServiceImpl fileStorage;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private Map<Long, JobExecution> jobTracker = new HashMap<>();

    @PostMapping("/employee/{id}")
    public ResponseEntity<Object> addEmployee(@PathVariable Long id, @RequestBody Employee employee){
//        return ResponseEntity.ok().body(fileService.findAllFile());
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, employeeService.saveEmployee(employee),
                        "Successfully data saved");
    }

    @GetMapping("/employee")
    public ResponseEntity<Object> addEmployee(){
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, employeeService.getEmployee(),
                        "Successfully data saved");
    }
    /**
     * This method is used to get all files.
     *
     * @return json
     */
//    @ApiOperation(value = "Get list of all Client for business",
//            notes = "View list of all active or inactive Client information ", response = ArrayList.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully data retrieved"),
//            @ApiResponse(code = 404, message = "No data available!")
//    })
    @GetMapping()
    public ResponseEntity<Object> getAll(){
//        return ResponseEntity.ok().body(fileService.findAllFile());
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, fileService.findAllFile(),
                        "Successfully data retrieved");
    }

    /**
     * This method is used to delete a specific plan
     *
     * @param id - id of the entity to delete. Must not be null.
     * @return json
     */
//    @ApiOperation(value = "Delete a plan by id", notes = "Provide a specific plan id to delete information ")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully deleted"),
//            @ApiResponse(code = 404, message = "Resource doesn't exist!")
//    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long id) throws IOException {
        fileService.deleteFile(id);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, null, "Successfully deleted");
    }

    /**
     * End-point to trigger the batch job for migrating employee data from CSV file
     * to database. Starts the batch job with some parameters i.e start time and
     * user name for logging on completion of the job.
     */
//    @PostMapping("/job")
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
//        return jobExecution.getStatus();
    }

    @GetMapping("/status/{fileId}")
    public BatchStatus checkBatchStatus(@PathVariable Long fileId){
        return jobTracker.get(fileId).getStatus();
        //Todo: check for null file id
    }


    /**
     * File Storage Controller
     */
    @PostMapping("/upload")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file)
            throws StorageException, MultipartException, SizeLimitExceededException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // Upload file into storage location
        File fileData = fileStorage.store(file);
        startBatchJob(fileData);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, fileData, "File Updated Successfully");
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

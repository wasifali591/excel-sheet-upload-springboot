package com.example.excelsheetupload.controllers;

import com.example.excelsheetupload.dtos.requests.EmployeeRequestDto;
import com.example.excelsheetupload.dtos.responses.ApiResponseDto;
import com.example.excelsheetupload.entities.Employee;
import com.example.excelsheetupload.services.impl.EmployeeServiceImpl;
import com.example.excelsheetupload.services.impl.FileServiceImpl;

import com.example.excelsheetupload.services.impl.FileStorageServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.util.ArrayList;

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


    @PostMapping("/employee/{id}")
    public ResponseEntity<Object> addEmployee(@PathVariable Long id, @RequestBody Employee employee){
//        return ResponseEntity.ok().body(fileService.findAllFile());
        return new ApiResponseDto()
                .generateResponse(HttpStatus.OK, employeeService.saveEmployee(employee, id),
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
     * File Storage Controller
     */
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file)
            throws StorageException, MultipartException, SizeLimitExceededException {
        // Upload file into storage location
        fileStorage.store(file);

        return "File uploaded successfully.";
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

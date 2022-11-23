package com.example.excelsheetupload.controllers;

import com.example.excelsheetupload.dtos.responses.ApiResponseDto;
import com.example.excelsheetupload.services.impl.FileServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
//@Api(tags = "File Controller", value = "FileController", description = "Controller for File Management")
public class FileHandleController {

    private FileServiceImpl fileService;


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
    public ResponseEntity<Object> deleteFile(@PathVariable Long id){
        fileService.deleteFile(id);
        return new ApiResponseDto().generateResponse(HttpStatus.OK, null, "Successfully deleted");
    }


}

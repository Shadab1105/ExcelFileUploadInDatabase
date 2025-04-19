package com.controller;

//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.service.ExcelService;
//
//@RestController
//@RequestMapping("/api")
//public class Controller 
//{
//	@Autowired
//	private ExcelService es;
//	
//	@PostMapping("/saveFile")
//    public void insertDataFromExcel(@RequestParam("file") MultipartFile file) throws IOException 
//	{
//            es.SaveData(file);
//     }
//    
//
//}


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.service.ExcelService;

@RestController
@RequestMapping("/api")
public class ExcelController {
    private final ExcelService excelService;

    @Autowired
    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/saveData")
    public ResponseEntity<String> getExcelData(@RequestParam("file") MultipartFile file) {
        try 
        {
            excelService.saveData(file);
            return ResponseEntity.ok("Data inserted successfully");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not Inserted");
        }
    }
}

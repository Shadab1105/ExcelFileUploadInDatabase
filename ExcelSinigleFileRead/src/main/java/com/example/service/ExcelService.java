package com.example.service;


import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.ExcelFile;
import com.example.repository.ExcelRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService 
{
	String questionText;
    String optionOne ;
    String optionTwo ;
	
	
	
	@Autowired
    private ExcelRepository excelRepository;



    public void saveData(MultipartFile file) throws IOException 
    {
        List<ExcelFile> questions = parseDataFromExcel(file);
        for (ExcelFile question : questions) 
        {
            try 
	            {
	                excelRepository.save(question);
	            } 
            catch (Exception ex) {
                System.err.println("Error saving record with questionNumber: " + question.getQuestionNumber());
            }
        }
    }

    private List<ExcelFile> parseDataFromExcel(MultipartFile file) throws IOException {
        List<ExcelFile> questions = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) 
            {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                int questionNumber = (int) getCellValueAsDouble(row.getCell(0));
                String question = getCellValueAsString(row.getCell(1));
                String[] parts = question.split("\\d-");
                if (parts.length >= 3) 
        	        {
        	            questionText = parts[0].trim();
        	            optionOne = parts[1].trim();
        	            optionTwo = parts[2].trim();
        	        } 
                int correctAnswer = (int) getCellValueAsDouble(row.getCell(2));

                ExcelFile excelFile = new ExcelFile();
                excelFile.setQuestionNumber(questionNumber);
                excelFile.setQuestion(questionText);
                excelFile.setOptionone(optionOne);
                excelFile.setOptiontwo(optionTwo);
                excelFile.setCorrectAnswer(correctAnswer);

                questions.add(excelFile);
            }
        }

        return questions;
    }

    private double getCellValueAsDouble(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        return "";
    }
}


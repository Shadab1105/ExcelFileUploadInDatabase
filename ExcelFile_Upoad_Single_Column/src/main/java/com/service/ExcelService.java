package com.service;

// Required imports
import org.apache.commons.math3.stat.descriptive.summary.Product; // ❌ Ye import yahan use nahi ho raha, hata sakte ho
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.model.ExcelFile;
import com.repository.ExcelRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService {

    String questionText;
    String optionOne;
    String optionTwo;

    @Autowired
    private ExcelRepository excelRepository;

    public void saveData(MultipartFile file) throws IOException {
        // ✅ Step 1: Excel file se data read karna
        List<ExcelFile> questions = parseDataFromExcel(file); 

        // ✅ Step 2: Har ek question ko DB me save karna
        for (ExcelFile question : questions) {
            try {
                excelRepository.save(question); // Output: DB me save hoga
            } catch (Exception ex) {
                System.err.println("Error saving record with questionNumber: " + question.getQuestionNumber());
            }
        }
    }

    private List<ExcelFile> parseDataFromExcel(MultipartFile file) throws IOException {
        List<ExcelFile> questions = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); 
            // Output: Sheet = Name: /xl/worksheets/sheet1.xml

            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next(); // ✅ Skipping header row (Question Number, Question, Correct Answer)
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next(); // ✅ Next data row (1, What is 2 + 2? 1-4 2-5, 1)

                // ✅ Reading cell values
                Long questionNumber = (long) getCellValueAsDouble(row.getCell(0)); // Output: 1
                String question = getCellValueAsString(row.getCell(1));             // Output: What is 2 + 2? 1-4 2-5
                int correctAnswer = (int) getCellValueAsDouble(row.getCell(2));     // Output: 1

                // ✅ Splitting question to extract options
                String[] parts = question.split("\\d-"); // parts = ["What is 2 + 2? ", "4 ", "5"]

                if (parts.length >= 3) {
                    questionText = parts[0].trim(); // Output: "What is 2 + 2?"
                    optionOne = parts[1].trim();    // Output: "4"
                    optionTwo = parts[2].trim();    // Output: "5"
                }

                // ✅ Creating ExcelFile object
                ExcelFile excelFile = new ExcelFile();
                excelFile.setQuestionNumber(questionNumber); // 1
                excelFile.setQuestion(questionText);         // "What is 2 + 2?"
                excelFile.setOptionone(optionOne);           // "4"
                excelFile.setOptiontwo(optionTwo);           // "5"
                excelFile.setCorrectAnswer(correctAnswer);   // 1

                questions.add(excelFile); // ✅ List me add kar diya
            }
        }

        return questions; // ✅ Return the list to be saved
    }

    private double getCellValueAsDouble(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
            
                // Output:
                // cell.getNumericCellValue() = 1.0 (for Question Number and Correct Answer)
                return cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                try {
                    System.out.println("Double.parseDouble(cell.getStringCellValue()) = " +
                        Double.parseDouble(cell.getStringCellValue()));
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
                // Output: What is 2 + 2? 1-4 2-5
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                System.out.println("String.valueOf(cell.getNumericCellValue()) = " +
                    String.valueOf(cell.getNumericCellValue()));
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        return "";
    }
}

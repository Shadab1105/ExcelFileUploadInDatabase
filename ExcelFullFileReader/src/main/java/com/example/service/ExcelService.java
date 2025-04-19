package com.example.service;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.ExcelFile;
import com.example.repository.ExcelRepository;

@Service
public class ExcelService 
{

	@Autowired
	private ExcelRepository er;

//	@Transactional
	public ResponseEntity<String> saveExcel(MultipartFile file) 
		{
			try 
				{
					XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
					XSSFSheet worksheet = workbook.getSheetAt(0);
					List<ExcelFile> englishQ = new ArrayList<>();
					List<ExcelFile> pashtoQ = new ArrayList<>();
					List<ExcelFile> dariQ = new ArrayList<>();
					for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i = i + 4) 
						{
							XSSFRow row = worksheet.getRow(i);
							ExcelFile engq = this.getModelFromRow(row, 9, 10, 11, "English");
							ExcelFile pashtoq = this.getModelFromRow(row, 7, 6, 5, "Pashto");
							ExcelFile dariq = this.getModelFromRow(row, 2, 1, 0, "Dari");
							englishQ.add(engq);
							pashtoQ.add(pashtoq);
							dariQ.add(dariq);
						}
					er.saveAll(englishQ);
					er.saveAll(pashtoQ);
					er.saveAll(dariQ);
					workbook.close();
					return new ResponseEntity<>("Saved Successfully", HttpStatus.OK);
				} 
			catch (Exception e) 
				{
					e.printStackTrace();
					return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
		}

	
	
	public ExcelFile getModelFromRow(XSSFRow row, int questionIdCol, int questionCol, int answerCol, String language) {
	    ExcelFile excelFile = new ExcelFile();
	    excelFile.setLanguage(language);
	    if (language.equals("English")) 
		    {
		        String data = row.getCell(questionCol).getStringCellValue().trim();
		        String[] parts = data.split("\n");
		        if (parts.length >= 3) 
			        {
			            String questionText = parts[0].trim();
			            String optionOne = parts[1].trim();
			            String optionTwo = parts[2].trim();
		
			            excelFile.setQuestion(questionText);
			            excelFile.setOption1(optionOne);
			            excelFile.setOption2(optionTwo);
			        }
		    } 
	    
	    else 
	    	{
	        	String data = row.getCell(questionCol).getStringCellValue().trim();
	        	String[] parts = data.split("\n");
	        	if (parts.length >= 3) 
		        	{
			            String questionText = parts[0].trim();
			            String optionOne = parts[1].trim();
			            String optionTwo = parts[2].trim();
		
			            excelFile.setQuestion(questionText);
			            excelFile.setOption1(optionOne);
			            excelFile.setOption2(optionTwo);
		        	}
	    	}

	    int qid = (int) row.getCell(questionIdCol).getNumericCellValue();
	    excelFile.setQid(qid);
	    int ans = (int) row.getCell(answerCol).getNumericCellValue();
	    excelFile.setAnswer(ans);

	    return excelFile;
	}

	
	
	
	
	
//	public ExcelFile getModelFromRow(XSSFRow row, int questionIdCol, int questionCol, int answerCol, String language) {
//		ExcelFile excelFile = new ExcelFile();
//		excelFile.setLanguage(language);
//		if (language.equals("English")) {
//			String[] sp1 = row.getCell(questionCol).getStringCellValue().trim().split("\\?");
//			String[] sp2 = sp1[1].trim().split("\n");
//
//			excelFile.setQuestion(sp1[0] + "?");
//			excelFile.setOption1(sp2[0].substring(sp2[0].indexOf("-") + 1).trim());
//			excelFile.setOption2(sp2[1].substring(sp2[1].indexOf("-") + 1).trim());
//		} else {
//			String[] sp1 = row.getCell(questionCol).getStringCellValue().trim().split("\n");
//
//			excelFile.setQuestion(sp1[0]);
//			excelFile.setOption1(sp1[1].substring(sp1[1].indexOf("-") + 1).trim());
//			excelFile.setOption2(sp1[2].substring(sp1[2].indexOf("-") + 1).trim());
//		}
//		int qid = (int) row.getCell(questionIdCol).getNumericCellValue();
//		excelFile.setQid(qid);
//		int ans = (int) row.getCell(answerCol).getNumericCellValue();
//		excelFile.setAnswer(ans);
//		return excelFile;
//
//	}

	

	
	
}

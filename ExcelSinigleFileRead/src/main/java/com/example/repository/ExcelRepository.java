package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.ExcelFile;

public interface ExcelRepository extends JpaRepository<ExcelFile,Long>
{

	ExcelFile findByQuestionNumber(int questionNumber);

}

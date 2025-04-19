package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.ExcelFile;

public interface ExcelRepository extends JpaRepository<ExcelFile,Long>
{

	ExcelFile findByQuestionNumber(Long questionNumber);

}

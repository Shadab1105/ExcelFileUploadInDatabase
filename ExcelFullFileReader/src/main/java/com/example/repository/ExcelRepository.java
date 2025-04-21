package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ExcelFile;


@Repository
public interface ExcelRepository extends JpaRepository<ExcelFile, Integer> 
{

}

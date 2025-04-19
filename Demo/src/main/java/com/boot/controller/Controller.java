package com.boot.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller 
{
	@Autowired
	private DataSource ds;
	
	@GetMapping("/checkCon")
  public String checkConnection() {
      try {
          Connection con = ds.getConnection();// Kya connection sahi se established hua hai?
          System.out.println("Con= "+con);
          return "Connection successful!";
      } catch (SQLException e) 
      {
          return "Connection failed!" ;
      }
	}
}

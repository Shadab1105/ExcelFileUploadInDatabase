package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ExcelFile 
{
	
	@Id
    private int questionNumber;
    private String question;
    private String optionone;
    private String optiontwo;
    private int correctAnswer;
	
	
	
	
		public int getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOptionone() {
		return optionone;
	}
	public void setOptionone(String optionone) {
		this.optionone = optionone;
	}
	public String getOptiontwo() {
		return optiontwo;
	}
	public void setOptiontwo(String optiontwo) {
		this.optiontwo = optiontwo;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	    
	
	    
	    
	
}

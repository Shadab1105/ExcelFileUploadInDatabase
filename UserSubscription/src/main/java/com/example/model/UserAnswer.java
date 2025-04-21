package com.example.model;



import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
//@AllArgsConstructor
public class UserAnswer 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String answer;
	
	private Integer uid;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Question question;

	public Integer getId() {
		return id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer userId) {
		this.uid = userId;
	}

	public void setId(Integer id) {
		this.id = id;
		
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	

	
}

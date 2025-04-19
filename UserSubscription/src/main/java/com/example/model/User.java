package com.example.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User 
{
	@Id
	private Long Number;
	private String isSubscribe;
	public Long getNumber() {
		return Number;
	}
	public void setNumber(Long number) {
		Number = number;
	}
	public String getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	

}

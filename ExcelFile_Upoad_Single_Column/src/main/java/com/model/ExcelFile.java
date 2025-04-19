package com.model;



import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ExcelFile {

    @Id
    @Column(name = "question_number")
    private Long questionNumber;

    @Column(name = "question")
    private String question;

    @Column(name = "optionone")
    private String optionone;

    @Column(name = "optiontwo")
    private String optiontwo;

    @Column(name = "correct_answer")
    private int correctAnswer;
}

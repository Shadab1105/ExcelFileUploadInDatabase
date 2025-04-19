package com.example.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Question;
import com.example.model.User;
import com.example.model.UserAnswer;
import com.example.repository.QuestionRepository;
import com.example.repository.UserAnswerRepo;
import com.example.repository.UserRepository;
import com.example.service.QuestionService;
import com.example.service.UserService;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository qrepo;

    @Autowired
    private UserAnswerRepo userAnswerRepo;

    @PatchMapping("/getUsers/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long id) {
        Optional<User> userData = userService.getuserById(id);
        if (userData.isPresent()) {
            User user = userData.get();
            if (user.getIsSubscribe().equalsIgnoreCase("true")) 
            {
                return ResponseEntity.ok("Already Subscriber");
            } 
            else 
            {
                user.setIsSubscribe("true");
                userRepository.save(user);
                return ResponseEntity.ok(questionService.getQuestions());
            }
        } 
        else 
        {
            User newUser = new User();
            newUser.setNumber(id);
            newUser.setIsSubscribe("false");
            userRepository.save(newUser);
            return ResponseEntity.ok(questionService.getQuestions());
        }
    }


    @PostMapping("/sendAnswer")
    public ResponseEntity<?> getAnswer(@RequestBody Map<String, Object> req) 
    {
        Integer userId = (Integer) req.get("id");
        String answer = (String) req.get("answer");
        return questionService.saveUserAnswer(userId, answer);
    }

    @GetMapping("/checkAnswer/{uId}")
    public ResponseEntity<?> getCorrectAnswerCount(@PathVariable Integer uId) 
    {
        return questionService.getCorrectAnswerCount(uId);
    }

    
//    @GetMapping("/getNextQuestion/{questionId}")
//    public ResponseEntity<?> nextQuestion(@PathVariable Integer questionId) {
//        return questionService.getNextQuetion(questionId);
//    }
//
//    @GetMapping("/getQuestion")
//    public ResponseEntity<?> getQuestion() {
//        System.out.println("yes");
//        return questionService.getQuestions();
//    }
}


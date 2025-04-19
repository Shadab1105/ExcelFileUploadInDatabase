package com.example.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.model.Question;
import com.example.model.UserAnswer;
import com.example.repository.QuestionRepository;
import com.example.repository.UserAnswerRepo;

@Service
public class QuestionService {

    @Autowired
    private RestTemplate template;

    @Autowired
    private QuestionRepository qrepo;

    @Autowired
	private UserAnswerRepo userAnswerRepo;
    private Map<Integer, Integer> AnswerQuestion = new HashMap<>();


    public ResponseEntity<?> saveUserAnswer(Integer userId, String answer) {
        Integer QN = AnswerQuestion.getOrDefault(userId, 1);
        
        System.out.println("ansQue = "+QN);
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUid(userId);
        userAnswer.setAnswer(answer);
        userAnswer.setQuestion(qrepo.getById(QN));
        userAnswerRepo.save(userAnswer);

        Integer nextQuestionId = QN + 1;

        AnswerQuestion.put(userId, nextQuestionId);

        return getNextQuetion(userId,nextQuestionId);
    }

    public ResponseEntity<?> getNextQuetion(Integer userId,Integer questionId) {
        Optional<Question> nextQuestion = qrepo.findById(questionId);
        if (nextQuestion.isEmpty()) 
	        {
	           // return ResponseEntity.ok("Question not found");
        		return getCorrectAnswerCount(userId);
	        }

        Question nextQ = nextQuestion.get();
        Map<String, Object> res = new HashMap<>();
        //res.put("question_id", nextQuestion.getId());
        res.put("question", nextQ.getQuestion());

        return ResponseEntity.ok(res);
    }

    
    
    
    
    public ResponseEntity<?> getQuestions() {
        List<Map<String, Object>> res = qrepo.findIdAndQuestion();
        return ResponseEntity.ok(res);
    }
    
    
    
    
    public ResponseEntity<?> getCorrectAnswerCount(Integer userId) {
        List<UserAnswer> userAnswers = userAnswerRepo.findByUid(userId);
        int count = 0;

        for (UserAnswer userAnswer : userAnswers) 
        {
            if (userAnswer.getQuestion().getAnswer().equalsIgnoreCase(userAnswer.getAnswer())) 
            {
                count++;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("Correct Answer = ", count);

        return ResponseEntity.ok(response);
    }

    
    
    
    
}

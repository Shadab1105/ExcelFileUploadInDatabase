package com.example.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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


    public void fetchAndSaveQuestions() {
        // Check if questions are already saved
        if (qrepo.count() == 0) {
            String url = "https://opentdb.com/api.php?amount=5&type=multiple";
            Map<String, Object> response = template.getForObject(url, Map.class);
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

            // Save only if questions are not saved
            for (Map<String, Object> q : results) {
                Question question = new Question();
                question.setQuestion((String) q.get("question"));
                question.setAnswer((String) q.get("correct_answer"));
//
//                // Set options as well
//                question.setOptionA((String) q.get("incorrect_answers").get(0));
//                question.setOptionB((String) q.get("incorrect_answers").get(1));
//                question.setOptionC((String) q.get("incorrect_answers").get(2));
//                question.setOptionD((String) q.get("incorrect_answers").get(3));

                qrepo.save(question); // Save question
            }
        }
    }

    
    
    public ResponseEntity<?> saveUserAnswer(Integer userId, String answer) {
        Integer QN = AnswerQuestion.getOrDefault(userId, 1);
        
//        System.out.println("ansQue = "+QN);
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

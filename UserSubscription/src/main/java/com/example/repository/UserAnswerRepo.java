package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.UserAnswer;

public interface UserAnswerRepo extends JpaRepository<UserAnswer, Long>
{

	    @Query("SELECT MAX(ua.question.id) FROM UserAnswer ua WHERE ua.uid = :userId")
	    Integer findLastAnsweredQuestionIdForUser(@Param("userId") Integer userId);

		List<UserAnswer> findByUid(Integer userId);

}

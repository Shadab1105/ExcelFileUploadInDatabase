package com.example.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

//	 @Query("SELECT new map(q.id as question_id, q.question as question) FROM Question q where q.id= :id")
//	    List<Map<String, Object>> findIdAndQuestion(@Param("id") Integer Id);
	 
	
//	@Query("SELECT q.id as id, q.question as question FROM Question q WHERE q.id = 1")
//	List<Map<String, Object>> findIdAndQuestion();
	
	@Query("SELECT q.question as question FROM Question q WHERE q.id = 1")
	List<Map<String, Object>> findIdAndQuestion();

	 
	 @Query(nativeQuery = true,value =  "SELECT q.id as id, q.question as question FROM question q where q.id>= :id limit 2")
	    List<Map<String, Object>> findNextQuestion(@Param("id") Integer qId);
}



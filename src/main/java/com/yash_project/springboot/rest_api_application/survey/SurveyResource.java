package com.yash_project.springboot.rest_api_application.survey;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class SurveyResource {
	
	@Autowired
	SurveyService surveyService;

	@RequestMapping("/surveys")
	public List<Survey> retrieveAllCourses(){
		return surveyService.retrieveAllSurvey();
	}
	
	@RequestMapping("/surveys/{id}")
	public Survey retrieveSurveyById(@PathVariable String id) {
		Optional<Survey> survey = surveyService.retrieveSurveyById(id);
		if(survey.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return survey.get();
	}
	
	@RequestMapping("/surveys/{id}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String id){
		List<Question> questions = surveyService.retrieveAllSurveyQuestions(id);
		if(questions.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return questions;
	}
	
	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSpecificSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId){
		Question question = surveyService.retrieveSpecificSurveyQuestion(surveyId,questionId);
		if(question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return question;
	}
	

	@RequestMapping(value="/surveys/{id}/questions",method=RequestMethod.POST)
	public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String id,@Valid @RequestBody Question question){
		String questionId = surveyService.addNewSurveyQuestion(id,question);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{questionId}")
						.buildAndExpand(questionId).toUri();
		return ResponseEntity.created(location).build();
	}
	

	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId){
		surveyService.deleteSurveyQuestion(surveyId,questionId);
		return ResponseEntity.noContent().build();
	}
	

	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}",method=RequestMethod.PUT)
	public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId,@RequestBody Question question){
		surveyService.updateSurveyQuestion(surveyId,questionId,question);
		return ResponseEntity.noContent().build();
	}
}

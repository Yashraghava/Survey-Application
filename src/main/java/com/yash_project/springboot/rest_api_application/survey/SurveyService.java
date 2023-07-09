package com.yash_project.springboot.rest_api_application.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

	private static List<Survey> surveys = new ArrayList<>();
	static {
		Question question1 = new Question("Question1",
		        "Most Popular Cloud Platform Today", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2",
		        "Fastest Growing Cloud Platform", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3",
		        "Most Popular DevOps Tool", Arrays.asList(
		                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1,
		        question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey",
		        "Description of the Survey", questions);

		surveys.add(survey);
	}
	
	public List<Survey> retrieveAllSurvey(){
		return surveys;
	}

	public Optional<Survey> retrieveSurveyById(String id) {
		// TODO Auto-generated method stub
		Predicate<? super Survey> predicate = (survey -> survey.getId().equalsIgnoreCase(id));
		Optional<Survey> survey= surveys.stream().filter(predicate).findFirst();
		return survey;
	}

	public List<Question> retrieveAllSurveyQuestions(String id) {
		// TODO Auto-generated method stub
		Predicate<? super Survey> predicate = (survey -> survey.getId().equalsIgnoreCase(id));
		Optional<Survey> survey= surveys.stream().filter(predicate).findFirst();
		return survey.isEmpty()?null:survey.get().getQuestions();
		
	}

	public Question retrieveSpecificSurveyQuestion(String surveyId, String questionId) {
		// TODO Auto-generated method stub

		Predicate<? super Survey> predicate = (survey -> survey.getId().equalsIgnoreCase(surveyId));
		Optional<Survey> survey= surveys.stream().filter(predicate).findFirst();
		if(survey.isEmpty()) {
			return null;
		}
		Predicate<? super Question> predicate2 = (question -> question.getId().equalsIgnoreCase(questionId));
		Optional<Question> question = survey.get().getQuestions().stream().filter(predicate2).findFirst();
		if(question.isEmpty()) {
			return null;
		}
		return question.get();
	}

	public String addNewSurveyQuestion(String id, Question question) {
		// TODO Auto-generated method stub
		List<Question> questions = this.retrieveAllSurveyQuestions(id);
		question.setId(generateRandomId());
		questions.add(question);
		return question.getId();
	}

	private String generateRandomId() {
		SecureRandom secureRandom = new SecureRandom();
		String randomId = new BigInteger(32,secureRandom).toString();
		return randomId;
	}

	public String deleteSurveyQuestion(String surveyId, String questionId) {
		// TODO Auto-generated method stub
		List<Question> questions = this.retrieveAllSurveyQuestions(surveyId);
		Predicate<? super Question> predicate = question -> question.getId().equalsIgnoreCase(questionId);
		boolean removed = questions.removeIf(predicate);
		if(!removed) {
			return null;
		}
		return questionId;
	}

	public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
		// TODO Auto-generated method stub
		List<Question> questions = this.retrieveAllSurveyQuestions(surveyId);
		Predicate<? super Question> predicate = quest -> quest.getId().equalsIgnoreCase(questionId);
		boolean removed = questions.removeIf(predicate);
		questions.add(question);
		
	}
	
}

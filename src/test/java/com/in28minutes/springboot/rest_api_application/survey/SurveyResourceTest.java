package com.in28minutes.springboot.rest_api_application.survey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yash_project.springboot.rest_api_application.survey.Question;
import com.yash_project.springboot.rest_api_application.survey.SurveyResource;
import com.yash_project.springboot.rest_api_application.survey.SurveyService;

@WebMvcTest(controllers=SurveyResource.class)
class SurveyResourceTest {

	@MockBean
	private SurveyService surveyService;
	
	@Autowired
	MockMvc mockMvc;
	
//	MOCK ->  SurveyService.retrieveSpecificSurveyQuestion(String surveyId, String questionId)
	
//	FIRE A REQUEST
//	"/surveys/{surveyId}/questions/{questionId}"
//	http://localhost:8080/surveys/survey1/questions/question1
	
	private static String SPECIFIC_QUESTION_URL="http://localhost:8080/surveys/survey1/questions/question1";
	private static String GENERIC_QUESTION_URL="http://localhost:8080/surveys/survey1/questions/";
	
	@Test
	void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
		RequestBuilder RequestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(RequestBuilder).andReturn();
		System.out.println(mvcResult.getResponse());
		System.out.println(mvcResult.getResponse().getContentAsString());
		System.out.println(mvcResult.getResponse().getStatus());
		assertEquals(404,mvcResult.getResponse().getStatus());
	}
	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
		RequestBuilder RequestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		Question question = new Question("Question1",
		        "Most Popular Cloud Platform Today", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		when(surveyService.retrieveSpecificSurveyQuestion("Survey1","Question1")).thenReturn(question);
		MvcResult mvcResult = mockMvc.perform(RequestBuilder).andReturn();
		String expectedResponse = """
				{
				    "id": "Question1",
				    "description": "Most Popular Cloud Platform Today",
				    "options": [
				        "AWS",
				        "Azure",
				        "Google Cloud",
				        "Oracle Cloud"
				    ],
				    "answer": "AWS"
				}
				""";
//		System.out.println(mvcResult.getResponse());
//		System.out.println(mvcResult.getResponse().getContentAsString());
//		System.out.println(mvcResult.getResponse().getStatus());
//		assertEquals(200,mvcResult.getResponse().getStatus());
//		JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
	}

	@Test
	void addNewSurveyQuestion_basicScenario() throws Exception {
		
		String requestBody = """
			{
			    "description": "Your favourite Programming Language",
			    "options": [
			      "Java",
			      "C",
			      "Javascript",
			      "Python"
			    ],
			    "answer": "Java"
			  }
				""";
		RequestBuilder RequestBuilder = MockMvcRequestBuilders.post(GENERIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON)
		.content(requestBody).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(RequestBuilder).andReturn();
		String expectedResponse = """
				{
				    "id": "Question1",
				    "description": "Most Popular Cloud Platform Today",
				    "options": [
				        "AWS",
				        "Azure",
				        "Google Cloud",
				        "Oracle Cloud"
				    ],
				    "answer": "AWS"
				}
				""";
//		System.out.println(mvcResult.getResponse());
//		System.out.println(mvcResult.getResponse().getContentAsString());
//		System.out.println(mvcResult.getResponse().getStatus());
		assertEquals(201,mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
	}
	

}

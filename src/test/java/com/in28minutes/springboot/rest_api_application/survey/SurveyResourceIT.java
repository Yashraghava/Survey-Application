package com.in28minutes.springboot.rest_api_application.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

	//	http://localhost:8080/surveys/survey1/questions/question1
	//	/surveys/survey1/questions/question1

	private static String SPECIFIC_QUESTION_URL = "/surveys/survey1/questions/question1";
	private static String GENERIC_QUESTION_URL = "/surveys/survey1/questions";
	
	@Autowired
	TestRestTemplate template;
	String str = """
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
	
	
	
	@Test
	void retrieveSpecificSurveyQuestion() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL,String.class);
		String expectedResponse = """
				{"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
				"answer":"AWS"}
				""";
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json",responseEntity.getHeaders().get("Content-Type").get(0));
		
		JSONAssert.assertEquals(expectedResponse,responseEntity.getBody(), false);
//		assertEquals(expectedResponse.trim(),responseEntity.getBody());
//		System.out.println(responseEntity.getHeaders());
//		System.out.println(responseEntity.getBody());
//		[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Thu, 08 Jun 2023 21:03:11 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
//		{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"answer":"AWS"}
		
//		<{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"answer":"AWS"}
//		{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"answer":"AWS"}>
			
	}
	
	
	@Test
	void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTION_URL,String.class);
		String expectedResponse = """
							[
			    {
			        "id": "Question1"
			    },
			    {
			        "id": "Question2"
			    },    
			    {
			        "id": "Question3"
			    }
			]
				""";
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json",responseEntity.getHeaders().get("Content-Type").get(0));
		
		JSONAssert.assertEquals(expectedResponse,responseEntity.getBody(), false);
//		assertEquals(expectedResponse.trim(),responseEntity.getBody());
//		System.out.println(responseEntity.getHeaders());
//		System.out.println(responseEntity.getBody());
//		[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Thu, 08 Jun 2023 21:03:11 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
//		{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"answer":"AWS"}
		
//		<{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"answer":"AWS"}
//		{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"answer":"AWS"}>
		
		

	}
	
	
	//	http://localhost:8080/surveys/Survey1/questions
	// 	POST
	//	Content-Type:application/json
	//	201
	//	http://localhost:8080/surveys/Survey1/questions/1410812722
	
	@Test
	void addNewQuestionBasicScenario() {
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
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","application/json");
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody,headers);
		
		ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTION_URL,HttpMethod.POST,httpEntity,String.class);
		
		
		System.out.println(responseEntity.getHeaders());
		System.out.println(responseEntity.getBody());
		
//		/surveys/survey1/questions/

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/survey1/questions/"));
		
		template.delete(locationHeader);
		
//		[Location:"http://localhost:38021/surveys/survey1/questions/1842059247", Content-Length:"0", Date:"Thu, 08 Jun 2023 22:16:50 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
//		null
	}

	
}

package com.in28minutes.springboot.rest_api_application.survey;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class JsonAssertTest {

	@Test
	void jsonAssert_learningBasics() throws JSONException {
		
		String expectedResponse = """
				{	"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"answer":"AWS"}
				""";
		String actualResponse = """
				{  "id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
					"answer":"AWS"}
				""";
		JSONAssert.assertEquals(expectedResponse,actualResponse, false);
	}

}

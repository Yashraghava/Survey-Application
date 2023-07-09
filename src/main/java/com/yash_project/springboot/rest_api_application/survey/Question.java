package com.yash_project.springboot.rest_api_application.survey;

import java.util.List;

public class Question {
	private String id;
	private String description;
	private List<String> options;
	private String answer;
	public Question(String id, String description, List<String> options, String answer) {
		super();
		this.id = id;
		this.description = description;
		this.options = options;
		this.answer = answer;
	}
	public Question() {
		super();
	}
	public String getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public List<String> getOptions() {
		return options;
	}
	public String getAnswer() {
		return answer;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", options=" + options + ", answer=" + answer
				+ "]";
	}
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

}

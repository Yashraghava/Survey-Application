package com.yash_project.springboot.rest_api_application.hello_world;

public class HelloWorldBean {
	private String msg;

	public HelloWorldBean(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [msg=" + msg + "]";
	}

}

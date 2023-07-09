package com.yash_project.springboot.rest_api_application.hello_world;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldResource {

	@RequestMapping("hello-world")
	@ResponseBody
	public String helloWorld() {
		return "Hello World!";
	}
	
	@RequestMapping("hello-world-bean")
	@ResponseBody
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World Bean!");
	}

	@RequestMapping("hello-world-bean-path-param/{name}")
	@ResponseBody
	public HelloWorldBean helloWorldBeanPathParam(@PathVariable String name) {
		return new HelloWorldBean("Hello World Bean Param, "+name);
	}
}

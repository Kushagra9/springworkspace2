package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class RestControllerExample {
	@Autowired
	TestService service;
	
	@GetMapping("/test")
	public String getString() {
		service.setTestService("test");
		return service.getString();
	}
}

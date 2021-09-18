package com.test;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
public class TestServiceImpl implements TestService{

	String str;
	public void setTestService(String str) {
		// TODO Auto-generated method stub
		this.str=str;
		
	}

	public String getString() {
		return this.str;
	}

}

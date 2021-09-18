package com.reddit.exception;

public class SpringRedditNotFoundException extends RuntimeException {
	
public SpringRedditNotFoundException(String exMessage) {
	super(exMessage);
}
}

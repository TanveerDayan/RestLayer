package com.juggler.test;

import org.springframework.beans.factory.annotation.Autowired;

public class Class2 {

	@Autowired
	RequestIdHolder requestIdHolder;

	public void print() {
		System.out.println(requestIdHolder.getId());
	}

	
}

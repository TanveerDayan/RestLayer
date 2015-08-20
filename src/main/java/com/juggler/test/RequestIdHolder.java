package com.juggler.test;

import java.util.UUID;

public class RequestIdHolder {

	public RequestIdHolder() {
		UUID uniqueKey = UUID.randomUUID();
		setId(uniqueKey.toString());
		System.out.println(getId());
	}

	String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RequestIdHolder [id=" + id + "]";
	}

}

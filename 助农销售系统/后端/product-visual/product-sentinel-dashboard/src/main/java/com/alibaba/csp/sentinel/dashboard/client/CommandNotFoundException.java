package com.alibaba.csp.sentinel.dashboard.client;

public class CommandNotFoundException extends Exception {

	public CommandNotFoundException() {
	}

	public CommandNotFoundException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}

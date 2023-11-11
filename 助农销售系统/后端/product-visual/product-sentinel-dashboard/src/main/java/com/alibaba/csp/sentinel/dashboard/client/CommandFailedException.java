package com.alibaba.csp.sentinel.dashboard.client;

public class CommandFailedException extends RuntimeException {

	public CommandFailedException() {
	}

	public CommandFailedException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}

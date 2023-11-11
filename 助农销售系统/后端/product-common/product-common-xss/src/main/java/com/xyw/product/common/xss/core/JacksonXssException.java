package com.xyw.product.common.xss.core;

import lombok.Getter;

import java.io.IOException;

@Getter
public class JacksonXssException extends IOException implements XssException {

	private final String input;

	public JacksonXssException(String input, String message) {
		super(message);
		this.input = input;
	}

}

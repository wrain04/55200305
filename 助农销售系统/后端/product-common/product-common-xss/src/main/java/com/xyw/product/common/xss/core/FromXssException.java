package com.xyw.product.common.xss.core;

import lombok.Getter;

@Getter
public class FromXssException extends IllegalStateException implements XssException {

	private final String input;

	public FromXssException(String input, String message) {
		super(message);
		this.input = input;
	}

}

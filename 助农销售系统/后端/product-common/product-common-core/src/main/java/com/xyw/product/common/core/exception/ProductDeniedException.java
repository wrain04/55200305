package com.xyw.product.common.core.exception;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ProductDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductDeniedException(String message) {
		super(message);
	}

	public ProductDeniedException(Throwable cause) {
		super(cause);
	}

	public ProductDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductDeniedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

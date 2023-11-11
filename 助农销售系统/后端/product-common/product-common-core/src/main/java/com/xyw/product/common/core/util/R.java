/*
 * Copyright (c) 2020 product4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xyw.product.common.core.util;

import com.xyw.product.common.core.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private int code;

	@Getter
	@Setter
	private String msg;

	@Getter
	@Setter
	private String error;

	@Getter
	@Setter
	private T data;

	public static <T> R<T> ok() {
		return restResult(null, CommonConstants.SUCCESS, "success", null);
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, CommonConstants.SUCCESS, "success", null);
	}

	public static <T> R<T> ok(T data, String msg) {
		return restResult(data, CommonConstants.SUCCESS, msg, null);
	}

	public static <T> R<T> failed() {
		return restResult(null, CommonConstants.FAIL, "error", null);
	}

	public static <T> R<T> failed(String error) {
		return restResult(null, CommonConstants.FAIL, "error", error);
	}

	public static <T> R<T> failed(T data) {
		return restResult(data, CommonConstants.FAIL, "error", null);
	}

	public static <T> R<T> failed(T data, String error) {
		return restResult(data, CommonConstants.FAIL, error, error);
	}

	public static <T> R<T> restResult(T data, int code, String msg, String error) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		apiResult.setError(error);
		return apiResult;
	}

}

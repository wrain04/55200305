package com.xyw.product.admin.api.vo;

import lombok.Data;

import java.time.Instant;

public class TokenVo {

	private String id;

	private Long userId;

	private String clientId;

	private String username;

	private String accessToken;

	private Instant issuedAt;

	private Instant expiresAt;

}

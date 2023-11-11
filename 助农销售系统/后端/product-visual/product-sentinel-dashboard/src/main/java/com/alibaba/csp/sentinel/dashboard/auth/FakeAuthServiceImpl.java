package com.alibaba.csp.sentinel.dashboard.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class FakeAuthServiceImpl implements AuthService<HttpServletRequest> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public FakeAuthServiceImpl() {
		this.logger.warn("there is no auth, use {} by implementation {}", AuthService.class, this.getClass());
	}

	@Override
	public AuthUser getAuthUser(HttpServletRequest request) {
		return new AuthUserImpl();
	}

	static final class AuthUserImpl implements AuthUser {

		@Override
		public boolean authTarget(String target, PrivilegeType privilegeType) {
			// fake implementation, always return true
			return true;
		}

		@Override
		public boolean isSuperUser() {
			// fake implementation, always return true
			return true;
		}

		@Override
		public String getNickName() {
			return "FAKE_NICK_NAME";
		}

		@Override
		public String getLoginName() {
			return "FAKE_LOGIN_NAME";
		}

		@Override
		public String getId() {
			return "FAKE_EMP_ID";
		}

	}

}

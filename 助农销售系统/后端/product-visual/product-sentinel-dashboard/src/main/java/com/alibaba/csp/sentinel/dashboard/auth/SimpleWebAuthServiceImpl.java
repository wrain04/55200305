package com.alibaba.csp.sentinel.dashboard.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SimpleWebAuthServiceImpl implements AuthService<HttpServletRequest> {

	public static final String WEB_SESSION_KEY = "session_sentinel_admin";

	@Override
	public AuthUser getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object sentinelUserObj = session.getAttribute(SimpleWebAuthServiceImpl.WEB_SESSION_KEY);
		if (sentinelUserObj != null && sentinelUserObj instanceof AuthUser) {
			return (AuthUser) sentinelUserObj;
		}

		return null;
	}

	public static final class SimpleWebAuthUserImpl implements AuthUser {

		private String username;

		public SimpleWebAuthUserImpl(String username) {
			this.username = username;
		}

		@Override
		public boolean authTarget(String target, PrivilegeType privilegeType) {
			return true;
		}

		@Override
		public boolean isSuperUser() {
			return true;
		}

		@Override
		public String getNickName() {
			return username;
		}

		@Override
		public String getLoginName() {
			return username;
		}

		@Override
		public String getId() {
			return username;
		}

	}

}

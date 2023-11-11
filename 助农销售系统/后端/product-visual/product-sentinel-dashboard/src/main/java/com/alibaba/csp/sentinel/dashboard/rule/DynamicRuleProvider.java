package com.alibaba.csp.sentinel.dashboard.rule;

public interface DynamicRuleProvider<T> {

	T getRules(String appName) throws Exception;

}

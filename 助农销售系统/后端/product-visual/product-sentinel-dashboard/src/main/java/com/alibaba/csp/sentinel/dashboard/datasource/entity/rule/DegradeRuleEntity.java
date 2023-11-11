package com.alibaba.csp.sentinel.dashboard.datasource.entity.rule;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;

import java.util.Date;

public class DegradeRuleEntity implements RuleEntity {

	private Long id;

	private String app;

	private String ip;

	private Integer port;

	private String resource;

	private String limitApp;

	private Double count;

	private Integer timeWindow;

	private Integer grade;

	private Integer minRequestAmount;

	private Double slowRatioThreshold;

	private Integer statIntervalMs;

	private Date gmtCreate;

	private Date gmtModified;

	public static DegradeRuleEntity fromDegradeRule(String app, String ip, Integer port, DegradeRule rule) {
		DegradeRuleEntity entity = new DegradeRuleEntity();
		entity.setApp(app);
		entity.setIp(ip);
		entity.setPort(port);
		entity.setResource(rule.getResource());
		entity.setLimitApp(rule.getLimitApp());
		entity.setCount(rule.getCount());
		entity.setTimeWindow(rule.getTimeWindow());
		entity.setGrade(rule.getGrade());
		entity.setMinRequestAmount(rule.getMinRequestAmount());
		entity.setSlowRatioThreshold(rule.getSlowRatioThreshold());
		entity.setStatIntervalMs(rule.getStatIntervalMs());
		return entity;
	}

	@Override
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getLimitApp() {
		return limitApp;
	}

	public void setLimitApp(String limitApp) {
		this.limitApp = limitApp;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public Integer getTimeWindow() {
		return timeWindow;
	}

	public void setTimeWindow(Integer timeWindow) {
		this.timeWindow = timeWindow;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getMinRequestAmount() {
		return minRequestAmount;
	}

	public DegradeRuleEntity setMinRequestAmount(Integer minRequestAmount) {
		this.minRequestAmount = minRequestAmount;
		return this;
	}

	public Double getSlowRatioThreshold() {
		return slowRatioThreshold;
	}

	public DegradeRuleEntity setSlowRatioThreshold(Double slowRatioThreshold) {
		this.slowRatioThreshold = slowRatioThreshold;
		return this;
	}

	public Integer getStatIntervalMs() {
		return statIntervalMs;
	}

	public DegradeRuleEntity setStatIntervalMs(Integer statIntervalMs) {
		this.statIntervalMs = statIntervalMs;
		return this;
	}

	@Override
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public DegradeRule toRule() {
		DegradeRule rule = new DegradeRule();
		rule.setResource(resource);
		rule.setLimitApp(limitApp);
		rule.setCount(count);
		rule.setTimeWindow(timeWindow);
		rule.setGrade(grade);
		if (minRequestAmount != null) {
			rule.setMinRequestAmount(minRequestAmount);
		}
		if (slowRatioThreshold != null) {
			rule.setSlowRatioThreshold(slowRatioThreshold);
		}
		if (statIntervalMs != null) {
			rule.setStatIntervalMs(statIntervalMs);
		}

		return rule;
	}

}

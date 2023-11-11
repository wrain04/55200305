package com.alibaba.csp.sentinel.dashboard.repository.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemSystemRuleStore extends InMemoryRuleRepositoryAdapter<SystemRuleEntity> {

	private static AtomicLong ids = new AtomicLong(0);

	@Override
	protected long nextId() {
		return ids.incrementAndGet();
	}

}

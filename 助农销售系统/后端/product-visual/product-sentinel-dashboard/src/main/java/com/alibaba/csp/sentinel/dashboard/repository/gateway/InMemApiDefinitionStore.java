package com.alibaba.csp.sentinel.dashboard.repository.gateway;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.repository.rule.InMemoryRuleRepositoryAdapter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemApiDefinitionStore extends InMemoryRuleRepositoryAdapter<ApiDefinitionEntity> {

	private static AtomicLong ids = new AtomicLong(0);

	@Override
	protected long nextId() {
		return ids.incrementAndGet();
	}

}

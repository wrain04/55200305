package com.xyw.product.order.util;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;

@Component
public class RedisCreateOrderUtil {

	static final String REDIS_INC_KEY = "order:redis:inc";

	@Resource
	private RedisTemplate redisTemplate;

	/**
	 * 用Redis计数器生成订单号,生成规则:前缀+年月日+Redis自增长序列号
	 * @param prefix 前缀字符
	 * @return 订单号 eg:Order201906110002
	 */
	public Long getOrderNoByRedis() {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(REDIS_INC_KEY, redisTemplate.getConnectionFactory());
		Long increment = entityIdCounter.incrementAndGet();
		Calendar cs = Calendar.getInstance();
		// 凌晨过期
		cs.add(Calendar.DAY_OF_MONTH, 1);
		cs.set(Calendar.HOUR_OF_DAY, 0);
		cs.set(Calendar.MINUTE, 0);
		cs.set(Calendar.SECOND, 0);
		// 设置第二天的凌晨00：00为失效期
		if (increment == 1L) {
			entityIdCounter.expireAt(cs.getTime());
		}
		// 补四位,缺失的位置用0补位
		String importantKey = increment.toString();
		if (importantKey.length() < 4) {
			importantKey = "0000".substring(0, 4 - importantKey.length()) + importantKey;
		}
		// TODO 报错地点
		Long orderNO = Long.valueOf(
				String.format("%s%s", FastDateFormat.getInstance("yyyyMMddhhmmss").format(cs.getTime()), importantKey));
		return orderNO;
	}

}

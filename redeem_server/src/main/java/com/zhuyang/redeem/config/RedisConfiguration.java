package com.zhuyang.redeem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {
	Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.pool.max-wait}")
	private long maxWaitMillis;

	@Bean
	public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config) {
		return new JedisPool(config, host);
	}

	@Bean(name = "jedis.pool.config")
	public JedisPoolConfig jedisPoolConfig() {
//		JedisPoolConfig config = new JedisPoolConfig();
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}

}

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
	@Value("${spring.redis.master.host}")
	private String masterHost;

	@Value("${spring.redis.master.port}")
	private int masterPort;

	@Value("${spring.redis.master.pool.max-idle}")
	private int masterMaxIdle;

	@Value("${spring.redis.master.pool.max-wait}")
	private long masterMaxWaitMillis;
	
	
	@Value("${spring.redis.slaver.host}")
	private String slaverHost;

	@Value("${spring.redis.slaver.port}")
	private int slaverPort;

	@Value("${spring.redis.slaver.pool.max-idle}")
	private int slaverMaxIdle;

	@Value("${spring.redis.slaver.pool.max-wait}")
	private long slaverMaxWaitMillis;
	
	

	@Bean
	public JedisPool jedisMasterPool(@Qualifier("jedis.master.pool.config") JedisPoolConfig config) {
		return new JedisPool(config, masterHost,masterPort);
	}

	@Bean(name = "jedis.master.pool.config")
	public JedisPoolConfig jedisMasterPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(masterMaxIdle);
		config.setMaxWaitMillis(masterMaxWaitMillis);
		return config;
	}
	
	
	
	@Bean
	public JedisPool jedisSlaverPool(@Qualifier("jedis.slaver.pool.config") JedisPoolConfig config) {
		return new JedisPool(config, slaverHost,slaverPort);
	}

	@Bean(name = "jedis.slaver.pool.config")
	public JedisPoolConfig jedisSlaverPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(slaverMaxIdle);
		config.setMaxWaitMillis(slaverMaxWaitMillis);
		return config;
	}

}

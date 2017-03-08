package com.zhuyang.redeem.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataBaseConfiguration {
	Logger logger = LoggerFactory.getLogger(DataBaseConfiguration.class);
	@Value("${spring.datasource.dbcp.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.dbcp.username}")
	private String userName;
	@Value("${spring.datasource.dbcp.password}")
	private String password;
	@Value("${spring.datasource.dbcp.url}")
	private String url;

	@Autowired
	private DataSource dataSource;

	@Bean
	public DataSource dataSource() {
		DataSourceBuilder builder = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
				.username(userName).password(password);
		return builder.build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
}

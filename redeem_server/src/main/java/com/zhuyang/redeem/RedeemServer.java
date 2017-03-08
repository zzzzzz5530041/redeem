package com.zhuyang.redeem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.zhuyang.redeem.cache.IProductCache;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.zhuyang.redeem", "com.zhuyang.redeem" }) 

public class RedeemServer implements CommandLineRunner {
	
	@Autowired
	private IProductCache iProductCache;

	public static void main(String[] args) {
		SpringApplication.run(RedeemServer.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		iProductCache.loadFromDB();//load db to redis
	}
}
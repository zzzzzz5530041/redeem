package com.zhuyang.redeem;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhuyang.redeem.entity.User;

@FeignClient(value = "redeem-server") //application name of service provider
public interface UserClientFeign {
	// send "/service/all" to provider when findAll() involk
	@RequestMapping(value = "/redeem/service/all", method = RequestMethod.GET)
	public List<User> findAll();

	@RequestMapping(value = "/redeem/service/add", method = RequestMethod.POST)
	public User addUser(@RequestBody User user);
}

package com.zhuyang.redeem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhuyang.redeem.UserClientFeign;
import com.zhuyang.redeem.entity.User;

@RestController
public class MovieController {
	@Autowired
	UserClientFeign userClientFeign;// annotation of Feign

	@RequestMapping(value = "/user/all", method = RequestMethod.GET) // handle
																		// request
	public List<User> findAll() {
		return userClientFeign.findAll();
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public User addUser(@RequestBody User user) {
		return userClientFeign.addUser(user);
	}
}
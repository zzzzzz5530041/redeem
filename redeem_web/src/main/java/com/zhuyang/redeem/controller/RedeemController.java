package com.zhuyang.redeem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhuyang.redeem.RedeemClientFeign;
import com.zhuyang.redeem.entity.Product;

@RestController
public class RedeemController {
	@Autowired
	RedeemClientFeign redeemClientFeign;// annotation of Feign

	@RequestMapping(value = "/products/{key}", method = RequestMethod.GET)
	public Product getProductByKey(@PathVariable String key) {
		Product product = redeemClientFeign.getProductByKey(key);
		return product;
	}

	@RequestMapping(value = "/products/", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		List<Product> products = redeemClientFeign.getAllProducts();
		return products;
	}
}
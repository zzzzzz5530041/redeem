package com.zhuyang.redeem.controller;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhuyang.redeem.entity.Product;
import com.zhuyang.redeem.service.IProductService;

@RestController
public class ProductController {
	@Autowired
	private IProductService iProductService;

	@RequestMapping(value = "/products/{key}", method = RequestMethod.GET)
	public Product getProductByKey(@PathVariable String key) {
		Product product = iProductService.getProductByKey(key);
		return product;
	}

	@RequestMapping(value = "/products/", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		List<Product> products = iProductService.getAllProducts();
		return products;
	}

	@RequestMapping(value = "/products/stock", method = RequestMethod.POST)
	public boolean updateProductStock(@RequestBody Map map) {
		System.out.println(map);
		String key = (String) map.get("key");
		Long buyCount = Long.parseLong((String) map.get("buyCount"));
		return iProductService.updateProductStock(key, buyCount);
	}
}

package com.zhuyang.redeem;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhuyang.redeem.entity.Product;

@FeignClient(value = "redeem-server") //application name of service provider
public interface RedeemClientFeign {
	@RequestMapping(value = "/redeem/products/{key}", method = RequestMethod.GET)
	public Product getProductByKey(@PathVariable("key") String key);

	@RequestMapping(value = "/redeem/products/", method = RequestMethod.GET)
	public List<Product> getAllProducts();
}

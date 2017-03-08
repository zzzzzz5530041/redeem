package com.zhuyang.redeem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhuyang.redeem.cache.IProductCache;
import com.zhuyang.redeem.entity.Product;

public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductCache iProductCache;

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return iProductCache.getAllProducts();
	}

	@Override
	public Product getProductByKey(String key) {
		// TODO Auto-generated method stub
		return iProductCache.getProductByKey(key);
	}

}

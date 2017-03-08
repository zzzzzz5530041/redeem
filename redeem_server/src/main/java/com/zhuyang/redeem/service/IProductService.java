package com.zhuyang.redeem.service;

import java.util.List;

import com.zhuyang.redeem.entity.Product;

public interface IProductService {
	public List<Product> getAllProducts();
	public Product getProductByKey(String key);// key is redis key. eg:product.1.1 (product.category.id)
}

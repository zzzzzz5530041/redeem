package com.zhuyang.redeem.cache;

import java.util.List;

import com.zhuyang.redeem.entity.Product;

public interface IProductCache {
	public void loadFromDB();
	public List<Product> getAllProducts();
	public Product getProductByKey(String key);
}

package com.zhuyang.redeem.cache;

import java.util.List;

import com.zhuyang.redeem.entity.Product;

public interface IProductCache {
	/** load product info from DB */
	public void loadFromDB();

	/** get all products */
	public List<Product> getAllProducts();

	/** get product by key */
	public Product getProductByKey(String key);

	/**
	 * update product stock
	 * 
	 * @param key
	 * @param buyCount
	 * @return success
	 */
	public boolean updateProductStock(String key, Long buyCount);
}

package com.zhuyang.redeem.dao;

import java.util.List;

import com.zhuyang.redeem.entity.Product;

public interface IProductDAO {
	public List<Product> getAllProducts();
	/**
	 * update product stock
	 * @param id
	 * @param stock new stock value
	 * @return
	 */
	public boolean updateProductStock(Long id, Long stock);
}

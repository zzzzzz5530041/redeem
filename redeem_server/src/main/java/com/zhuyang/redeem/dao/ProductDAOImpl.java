package com.zhuyang.redeem.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zhuyang.redeem.entity.Product;
@Repository
public class ProductDAOImpl implements IProductDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * get all products
	 */
	public List<Product> getAllProducts() {
		List<Map<String,Object>> rs = jdbcTemplate.queryForList("SELECT * FROM product");
		List<Product> products = new ArrayList<Product>();
		for(Map<String,Object> map : rs){
			Product p = new Product();
			p.setId(Long.parseLong(String.valueOf(map.get("id"))));
			p.setStock(Long.parseLong(String.valueOf(map.get("stock"))));
			p.setCategory(Long.parseLong(String.valueOf(map.get("category"))));
			p.setName((String)map.get("name"));
			p.setDescription((String)map.get("description"));
			p.setPrice(new BigDecimal(String.valueOf(map.get("price"))));
			products.add(p);
		}
		return products;
	}

}

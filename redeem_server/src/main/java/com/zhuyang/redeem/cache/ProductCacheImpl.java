package com.zhuyang.redeem.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuyang.redeem.dao.IProductDAO;
import com.zhuyang.redeem.entity.Product;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
@Component
public class ProductCacheImpl implements IProductCache {
	private static final String PRODUCT_LIST_KEY_NAME="product_keys";

	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private IProductDAO iProductDAO;
	
	public Jedis getJedis() { 
        return jedisPool.getResource();
    }
	public void loadFromDB() {
		List<Product> products = iProductDAO.getAllProducts();
		ObjectMapper mapper = new MappingJackson2HttpMessageConverter().getObjectMapper();
		Jedis jedis = getJedis() ;
		try {
			for(Product p : products){
				StringBuilder key = new StringBuilder("product_");
				key.append(p.getCategory()).append("_").append(p.getId());
				jedis.set(key.toString(), mapper.writeValueAsString(p));// put to string  eg:product.1.1
				jedis.lpush(PRODUCT_LIST_KEY_NAME, mapper.writeValueAsString(p));// push keys to list, key = product.keys
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}finally{
			jedis.close();
		}

	}
	@Override
	public List<Product> getAllProducts() {
		List<Product> products =new ArrayList();
		ObjectMapper mapper = new MappingJackson2HttpMessageConverter().getObjectMapper();
		Jedis jedis = getJedis() ;
		long length = jedis.llen(PRODUCT_LIST_KEY_NAME);
		List<String> jsons = jedis.lrange(PRODUCT_LIST_KEY_NAME, 0, length);
		try {
			for(String json :jsons){
				Product	product = mapper.readValue(json, Product.class);
				products.add(product);
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return products;
	}
	@Override
	public Product getProductByKey(String key) {
		ObjectMapper mapper = new MappingJackson2HttpMessageConverter().getObjectMapper();
		Jedis jedis = getJedis() ;
		String json = jedis.get(key);
		if(json==null||"".equals(json))return null;
		Product product = null;
		try {
			product = mapper.readValue(json, Product.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return product;
	}
	@Override
	public boolean updateProductStock(String key, Long buyCount) {
		ObjectMapper mapper = new MappingJackson2HttpMessageConverter().getObjectMapper();
		Jedis jedis = getJedis() ;
		String json = jedis.get(key);
		if(json==null||"".equals(json))return false;
		Product product = null;
		try {
			product = mapper.readValue(json, Product.class);
			//watch key
			jedis.watch(key);
			Transaction txn = jedis.multi();
			Long currentStock = product.getStock();
			System.out.println("current stock of "+key);
			if(currentStock<buyCount){
				txn.discard();
				jedis.unwatch();
				return false;
			}
			Long newStock = currentStock - buyCount;
			product.setStock(newStock);
			txn.set(key,mapper.writeValueAsString(product) );
			List <Object> exec = txn.exec();
			System.out.println("exec==="+exec);
			if(exec==null){
				return false;
			}
			jedis.unwatch();
			
			//update database
			boolean updateDB = iProductDAO.updateProductStock(product.getId(), newStock);
			if(!updateDB)return false;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return true;
	}
}

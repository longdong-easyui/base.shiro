package com.longdong.dao;

import com.longdong.entity.Product;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


public interface ProductDao {

    public Product createProduct(Product product);
    public Product updateProduct(Product product);
   
    Product findOne(Long productId);
    
	public int findCount(Product product);
	public List<Product> findAllProduct(Product product);
	public Map<String, InputStream> findImgById(Long id);
}

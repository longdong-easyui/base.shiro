package com.longdong.service;

import com.longdong.dao.ProductDao;
import com.longdong.entity.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product createProduct(Product product) {
        return productDao.createProduct(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productDao.updateProduct(product);
    }


    @Override
    public Product findOne(Long productId) {
        return productDao.findOne(productId);
    }

   
	@Override
	public int findCount(Product product) {
		
		return productDao.findCount(product);
	}

	@Override
	public List<Product> findAllProduct(Product product) {
		
		return productDao.findAllProduct(product);
	}

	@Override
	public Map<String, InputStream> findImgById(Long id) {
		
		return productDao.findImgById(id);
	}
}

package com.longdong.dao;

import com.longdong.entity.Brand;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>Brand: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface BrandDao {

    public Brand createBrand(Brand brand);
    public Brand updateBrand(Brand brand);
   
    Brand findOne(Long brandId);
    
	public int findCount(Brand brand);
	public List<Brand> findAllBrand(Brand brand);
	public Map<String, InputStream> findImgById(Long id);
}

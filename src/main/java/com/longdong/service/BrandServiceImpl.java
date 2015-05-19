package com.longdong.service;

import com.longdong.dao.BrandDao;
import com.longdong.entity.Brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;

    @Override
    public Brand createBrand(Brand brand) {
        return brandDao.createBrand(brand);
    }

    @Override
    public Brand updateBrand(Brand brand) {
        return brandDao.updateBrand(brand);
    }


    @Override
    public Brand findOne(Long brandId) {
        return brandDao.findOne(brandId);
    }

   
	@Override
	public int findCount(Brand brand) {
		
		return brandDao.findCount(brand);
	}

	@Override
	public List<Brand> findAllBrand(Brand brand) {
		
		return brandDao.findAllBrand(brand);
	}

	@Override
	public Map<String, InputStream> findImgById(Long id) {
		
		return brandDao.findImgById(id);
	}
}

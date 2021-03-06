package com.longdong.service;

import com.longdong.dao.SpecDao;
import com.longdong.entity.Spec;
import com.longdong.entity.SpecDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecDao specDao;

    @Override
    public Spec createSpec(Spec spec) {
        return specDao.createSpec(spec);
    }

    @Override
    public Spec updateSpec(Spec spec) {
        return specDao.updateSpec(spec);
    }


    @Override
    public Spec findOne(Long specId) {
        return specDao.findOne(specId);
    }

   
	@Override
	public int findCount(Spec spec) {
		
		return specDao.findCount(spec);
	}

	@Override
	public List<Spec> findAllSpec(Spec spec) {
		
		return specDao.findAllSpec(spec);
	}

	@Override
	public Map<String, InputStream> findImgById(Long id) {
		
		return specDao.findImgById(id);
	}

	@Override
	public List<SpecDetail> findAllSpecDetail(SpecDetail detail) {
		
		return specDao.findAllSpecDetail(detail);
	}

	@Override
	public SpecDetail createSpecDetail(SpecDetail specDetail) {
		
		return specDao.createSpecDetail(specDetail);
	}

	@Override
	public boolean deleteSpecDetailById(Long id) {
		
		return specDao.deleteSpecDetailById(id);
	}

	@Override
	public int updateSpecDetail(SpecDetail specDetail) {
		return specDao.updateSpecDetail(specDetail);
		
	}

	@Override
	public int updateImageByDetailId(SpecDetail specDetail) {
		
		return specDao.updateImageByDetailId(specDetail);
	}

	@Override
	public SpecDetail findSpecDetailOne(Long id) {
		
		return specDao.findSpecDetailOne(id);
	}
}

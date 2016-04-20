package com.longdong.service;

import com.longdong.entity.Spec;
import com.longdong.entity.SpecDetail;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface SpecService {


    public Spec createSpec(Spec spec);
    public Spec updateSpec(Spec spec);
    Spec findOne(Long specId);
	public int findCount(Spec spec);
	public List<Spec> findAllSpec(Spec spec);
	public Map<String, InputStream> findImgById(Long id);
	public List<SpecDetail> findAllSpecDetail(SpecDetail detail);
	public SpecDetail createSpecDetail(SpecDetail specDetail);
	public boolean deleteSpecDetailById(Long id);
	public SpecDetail updateSpecDetail(SpecDetail specDetail);
	public SpecDetail updateImageByDetailId(SpecDetail specDetail);
	public SpecDetail findSpecDetailOne(Long id);
	
	
	
	
}

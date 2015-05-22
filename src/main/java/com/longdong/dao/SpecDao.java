package com.longdong.dao;

import com.longdong.entity.Spec;
import com.longdong.entity.SpecDetail;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>Spec: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface SpecDao {

    public Spec createSpec(Spec Spec);
    public Spec updateSpec(Spec Spec);
   
    Spec findOne(Long SpecId);
    
	public int findCount(Spec Spec);
	public List<Spec> findAllSpec(Spec Spec);
	public Map<String, InputStream> findImgById(Long id);
	public List<SpecDetail> findAllSpecDetail(SpecDetail detail);
	public SpecDetail createSpecDetail(SpecDetail specDetail);
}

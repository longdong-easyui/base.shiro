package com.longdong.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.longdong.entity.Brand;


@Repository
public class BrandDaoImpl extends BaseDaoImpl implements BrandDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LobHandler lobHandler;
    
    public Brand createBrand(final Brand brand) {
        final String sql = "insert into brand(name,type,logo,url,sortNo,content,available,createdDate,updatedDate) values(?,?,?,?,?,?,?,now(),now())";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, brand.getName());
                psst.setInt(count++, brand.getType());
                psst.setBytes(count++,brand.getLogo());
                psst.setString(count++,brand.getUrl());
                psst.setInt(count++, brand.getSortNo());
                psst.setString(count++,brand.getContent());
                psst.setInt(count++, brand.getAvailable());
                
                return psst;
            }
        }, keyHolder);
        brand.setId(keyHolder.getKey().longValue());
        brand.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return brand;
    }

    @Override
    public Brand updateBrand(Brand brand) {
        final String sql = "update brand set name=?,type=?,url=?,sortNo=?,content=?, available=?,updatedDate=now() where id=?";
        jdbcTemplate.update(
                sql,
                brand.getName(),brand.getType(),brand.getUrl(),
                brand.getSortNo(),brand.getContent(),brand.getAvailable(), brand.getId());
        return brand;
    }

    @Override
    public Brand findOne(Long brandId) {
        final String sql = "select id,name,type,url,logo,sortNo,content,available from brand where id=?";
       
        @SuppressWarnings("unchecked")
		List<Brand> brandList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Brand.class),brandId);
        if(brandList.size() == 0) {
            return null;
        }
        
        return brandList.get(0);
    }

	@Override
	public int findCount(Brand brand) {
		
		String wqc = getWhereQueryConditon(brand);
		
		String sql = "select count(*) from brand "+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	@Override
	public List<Brand> findAllBrand(Brand brand) {
		String wqc = getWhereQueryConditon(brand);
		
		String sort = brand.getSort();
		String order = brand.getOrder();
		int skipResults = brand.getSkipResults();
		int rows = brand.getRows();
		
		String sql = " select id,name,type,url,sortNo,available,createdDate from brand "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		
		List<Brand> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Brand.class));
		
        return list;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, InputStream> findImgById(Long id) {
		 final String sql = "select logo from brand where id=?";
	       
	        @SuppressWarnings("unchecked")
	        List<Object> list=jdbcTemplate.query(sql, new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Map results = new HashMap();  
					InputStream ins = lobHandler.getBlobAsBinaryStream(rs, "logo");
					results.put("logo",ins);
					return results;
				}
				
			},id);
	        Object obj = list.get(0);
		return (Map<String, InputStream>) obj;
	}
}

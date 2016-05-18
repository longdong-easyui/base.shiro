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

import com.longdong.entity.Product;


@Repository
public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LobHandler lobHandler;
    
    public Product createProduct(final Product product) {
        final String sql = "insert into Product(name,type,logo,url,sortNo,content,available,createdDate,updatedDate) values(?,?,?,?,?,?,?,now(),now())";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, product.getName());
                psst.setInt(count++, product.getType());
                psst.setBytes(count++,product.getLogo());
                psst.setString(count++,product.getUrl());
                psst.setInt(count++, product.getSortNo());
                psst.setString(count++,product.getContent());
                psst.setInt(count++, product.getAvailable());
                
                return psst;
            }
        }, keyHolder);
        product.setId(keyHolder.getKey().longValue());
        product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        final String sql = "update Product set name=?,type=?,url=?,sortNo=?,content=?, available=?,updatedDate=now() where id=?";
        jdbcTemplate.update(
                sql,
                product.getName(),product.getType(),product.getUrl(),
                product.getSortNo(),product.getContent(),product.getAvailable(), product.getId());
        return product;
    }

    @Override
    public Product findOne(Long productId) {
        final String sql = "select id,name,type,url,logo,sortNo,content,available from Product where id=?";
       
        @SuppressWarnings("unchecked")
		List<Product> ProductList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Product.class),productId);
        if(ProductList.size() == 0) {
            return null;
        }
        
        return ProductList.get(0);
    }

	@Override
	public int findCount(Product product) {
		
		String wqc = getWhereQueryConditon(product);
		
		String sql = "select count(*) from Product "+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	@Override
	public List<Product> findAllProduct(Product product) {
		String wqc = getWhereQueryConditon(product);
		
		String sort = product.getSort();
		String order = product.getOrder();
		int skipResults = product.getSkipResults();
		int rows = product.getRows();
		
		String sql = " select id,name,type,url,sortNo,available,createdDate from Product "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		
		List<Product> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Product.class));
		
        return list;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, InputStream> findImgById(Long id) {
		 final String sql = "select logo from Product where id=?";
	       
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

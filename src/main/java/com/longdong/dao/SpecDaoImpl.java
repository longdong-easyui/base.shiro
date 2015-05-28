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

import com.longdong.entity.Spec;
import com.longdong.entity.SpecDetail;

/**
 * <p>Spec: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Repository
public class SpecDaoImpl extends BaseDaoImpl implements SpecDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LobHandler lobHandler;
    
    public Spec createSpec(final Spec spec) {
        final String sql = "insert into Spec(name,type,remark,sortNo,createdDate,updatedDate) values(?,?,?,?,now(),now())";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, spec.getName());
                psst.setInt(count++, spec.getType());
                psst.setString(count++, spec.getRemark());
                psst.setInt(count++, spec.getSortNo());
                return psst;
            }
        }, keyHolder);
        spec.setId(keyHolder.getKey().longValue());
        spec.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return spec;
    }

    @Override
    public Spec updateSpec(Spec spec) {
        final String sql = "update Spec set name=?,type=?,remark=?,sortNo=?,updatedDate=now() where id=?";
        jdbcTemplate.update(
                sql,
                spec.getName(),spec.getType(),spec.getRemark(),
                spec.getSortNo(), spec.getId());
        return spec;
    }

    @Override
    public Spec findOne(Long specId) {
        final String sql = "select id,name,type,remark,sortNo from Spec where id=?";
       
        @SuppressWarnings("unchecked")
		List<Spec> SpecList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Spec.class),specId);
        if(SpecList.size() == 0) {
            return null;
        }
        
        return SpecList.get(0);
    }

	@Override
	public int findCount(Spec spec) {
		
		String wqc = getWhereQueryConditon(spec);
		
		String sql = "select count(*) from Spec "+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	@Override
	public List<Spec> findAllSpec(Spec spec) {
		String wqc = getWhereQueryConditon(spec);
		
		String sort = spec.getSort();
		String order = spec.getOrder();
		int skipResults = spec.getSkipResults();
		int rows = spec.getRows();
		
		String sql = " select id,name,type,remark,sortNo,createdDate from Spec "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		
		List<Spec> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Spec.class));
		
        return list;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, InputStream> findImgById(Long id) {
		 final String sql = "select specValue from SpecDetail where id=?";
	       
	        @SuppressWarnings("unchecked")
	        List<Object> list=jdbcTemplate.query(sql, new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Map results = new HashMap();  
					InputStream ins = lobHandler.getBlobAsBinaryStream(rs, "specValue");
					results.put("specImg",ins);
					return results;
				}
				
			},id);
	        Object obj = list.get(0);
		return (Map<String, InputStream>) obj;
	}

	@Override
	public List<SpecDetail> findAllSpecDetail(SpecDetail detail) {
		String sql = " select id,specId,specName,sortNo,available from specDetail ";
			
		List<SpecDetail> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SpecDetail.class));
		
		return list;
	}

	@Override
	public SpecDetail createSpecDetail(final SpecDetail specDetail) {
		
		 final String sql = "insert into SpecDetail(specId,specName,specValue,sortNo) values(?,?,?,?)";

	        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	        jdbcTemplate.update(new PreparedStatementCreator() {
	            @Override
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
	                int count = 1;
	                psst.setLong(count++, specDetail.getSpecId());
	                psst.setString(count++, specDetail.getSpecName());
	                psst.setBytes(count++,specDetail.getSpecValue());
	                psst.setInt(count++, specDetail.getSortNo());
	                return psst;
	            }
	        }, keyHolder);
	        specDetail.setId(keyHolder.getKey().longValue());
	        return specDetail;
	}

	@Override
	public boolean deleteSpecDetailById(Long id) {
		  final String deleteSelfSql = "delete from SpecDetail where id=?";
	      int row = jdbcTemplate.update(deleteSelfSql, id);
		return row>0;
	}

	@Override
	public SpecDetail updateSpecDetail(SpecDetail specDetail) {
		
		  final String sql = "update specDetail set specName=?,sortNo=?,updatedDate=now() where id=?";
	        jdbcTemplate.update(
	                sql,
	                specDetail.getSpecName(),specDetail.getSortNo(),specDetail.getId());
	                
	        return specDetail;
	}
}

package com.longdong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.longdong.entity.Column;


@Repository
public class ColumnDaoImpl extends BaseDaoImpl implements ColumnDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LobHandler lobHandler;
    
    public Column createColumn(final Column column) {
        final String sql = "insert into column(parentId,name,url,sortNo,available,createdDate,updatedDate) values(?,?,?,?,?,now(),now())";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setInt(count++, column.getParentId());
                psst.setString(count++, column.getName());
                psst.setString(count++,column.getUrl());
                psst.setInt(count++, column.getSortNo());
                psst.setInt(count++, column.getAvailable());
                
                return psst;
            }
        }, keyHolder);
        column.setId(keyHolder.getKey().intValue());
        //column.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return column;
    }

    @Override
    public Column updateColumn(Column column) {
        final String sql = "update column set name=?,url=?,sortNo=?,available=?,updatedDate=now() where id=?";
        jdbcTemplate.update(
                sql,
                column.getName(),column.getUrl(),
                column.getSortNo(),column.getAvailable(), column.getId());
        return column;
    }

    @Override
    public Column findOne(Long columnId) {
        final String sql = "select id,parentId,name,url,sortNo,available,createdDate from column where id=?";
       
        @SuppressWarnings("unchecked")
		List<Column> columnList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Column.class),columnId);
        if(columnList.size() == 0) {
            return null;
        }
        
        return columnList.get(0);
    }

	@Override
	public int findCount(Column column) {
		
		String wqc = getWhereQueryConditon(column);
		
		String sql = "select count(*) from column "+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	@Override
	public List<Column> findAllColumn(Column column) {
		String wqc = getWhereQueryConditon(column);
		
		String sort = column.getSort();
		String order = column.getOrder();
		int skipResults = column.getSkipResults();
		int rows = column.getRows();
		
		String sql = " select id,parentId,name,url,sortNo,available,createdDate from column "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		
		List<Column> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Column.class));
		
        return list;
		
	}
	
}

package com.longdong.service;

import java.util.List;

import com.longdong.entity.Column;


public interface ColumnService {


    public Column createColumn(Column column);
    public Column updateColumn(Column column);
    Column findOne(Long columnId);
	public int findCount(Column column);
	public List<Column> findAllColumn(Column column);
	
	
}

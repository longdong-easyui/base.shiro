package com.longdong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longdong.dao.ColumnDao;
import com.longdong.entity.Column;


@Service
public class ColumnServiceImpl implements ColumnService {
    @Autowired
    private ColumnDao columnDao;

    @Override
    public Column createColumn(Column column) {
        return columnDao.createColumn(column);
    }

    @Override
    public Column updateColumn(Column column) {
        return columnDao.updateColumn(column);
    }


    @Override
    public Column findOne(Long columnId) {
        return columnDao.findOne(columnId);
    }

   
	@Override
	public int findCount(Column column) {
		
		return columnDao.findCount(column);
	}

	@Override
	public List<Column> findAllColumn(Column column) {
		
		return columnDao.findAllColumn(column);
	}

	@Override
	public int deleteColumns(String ids) {
		
		return columnDao.deleteColumns(ids);
	}

}

package com.longdong.dao;

import com.longdong.entity.User;

import java.util.List;


public interface UserDao {

    public User createUser(User user);
    public User updateUser(User user);
    public void deleteUser(Long userId);

    User findOne(Long userId);

    List<User> findAll();

    User findByUsername(String username);
    
	public List<User> findAllUser(User user);
	public int findCount(User user);
	public int deleteUsers(String ids);

}

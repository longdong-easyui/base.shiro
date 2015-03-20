package com.longdong.service;

import com.longdong.entity.User;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    public User createUser(User user);

    public User updateUser(User user);

    public void deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);


    User findOne(Long userId);

    List<User> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
    /**
     * 分页和参数查询list
     * @param user
     * @return
     */
	public List<User> findAllUser(User user);
	/**
	 * 参数 查询数量
	 * @param user
	 * @return
	 */
	public int findCount(User user);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteUsers(String ids);
	

}

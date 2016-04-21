package com.longdong.entity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class User extends BaseEntity {
    private Long id; //编号
    private Long organizationId; //所属公司
    private String username; //用户名
    private String password; //密码
    private String salt; //加密密码的盐
    private String roleIds; //拥有的角色列表
    private Integer locked ;
    private String roleIdsName;
   
    private Integer age;
    private Integer sex;
    private String mobile;
    private String address;
    private String email;
    private Date birthday;
    private String lockedStr;
    
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

   


    public String getRoleIdsName() {
		return roleIdsName;
	}

	public void setRoleIdsName(String roleIdsName) {
		this.roleIdsName = roleIdsName;
	}

	
  
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
    	if(locked==0){
    		this.lockedStr="解锁";
    	}else{
    		this.lockedStr="锁定";
    	}
        this.locked = locked;
    }

    public String getLockedStr() {
		return lockedStr;
	}

	public void setLockedStr(String lockedStr) {
		this.lockedStr = lockedStr;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", roleIds=" + roleIds +
                ", locked=" + locked +
                ", age="+age+
                ",sex="+sex+
                ",address="+address+
                ",mobile="+mobile+
                ",birthday="+new SimpleDateFormat("yyyy-MM-dd").format(birthday)+
                '}';
    }
}

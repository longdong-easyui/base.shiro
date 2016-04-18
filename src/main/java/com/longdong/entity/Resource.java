package com.longdong.entity;

import java.util.List;




/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class Resource extends BaseEntity{
    private Long id; //编号
    private String name; //资源名称
    private String type ; //资源类型
    private String url; //资源路径
    private String permission; //权限字符串
    private Long parentId; //父编号
    private String parentIds; //父编号列表
    private Integer available ;
    
    private String availableStr;
    private List<Resource> children;
    private String text;  //easyui tree 需要的 属性
    public static enum ResourceType {
        menu("菜单"), button("按钮");

        private final String info;
        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	this.text = name;
        this.name = name;
    }

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
    	if(available==0){
    		this.availableStr ="可用";
    	}else{
    		this.availableStr ="禁用";
    	}
        this.available = available;
    }

    public String getAvailableStr() {
		return availableStr;
	}

	public void setAvailableStr(String availableStr) {
		this.availableStr = availableStr;
	}

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public boolean isRootNode() {
        return parentId == 0;
    }

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (id != null ? !id.equals(resource.id) : resource.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", permission='" + permission + '\'' +
                ", parentId=" + parentId +
                ", parentIds='" + parentIds + '\'' +
                ", available=" + available +
                '}';
    }
    
}

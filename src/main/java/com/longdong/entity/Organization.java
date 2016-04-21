package com.longdong.entity;

import java.util.List;


public class Organization extends BaseEntity{
    private Long id; //编号
    private String name; //组织机构名称
    private Long parentId; //父编号
    private String parentIds; //父编号列表，如1/2/
    private Integer available ;

    private String availableStr;
    private List<Organization> children;
    private String text;  //easyui tree 需要的 属性
    
    private Long _parentId;
    
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
    	this._parentId=parentId;
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

	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long get_parentId() {
		return _parentId;
	}

	public void set_parentId(Long _parentId) {
		this._parentId = _parentId;
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

        Organization that = (Organization) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", parentIds='" + parentIds + '\'' +
                ", available=" + available +
                '}';
    }
   
}

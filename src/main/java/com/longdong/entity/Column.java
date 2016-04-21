package com.longdong.entity;

import java.util.List;


public class Column extends BaseEntity{
    private Integer id; //编号
    private Integer parentId;	//父id
    private String name; //栏目名称   
    private String url;
    private Integer sortNo;
    private Integer available ;			//0:可用，1：禁用
    private String availableStr;
   
   private List<Column> children;
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
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


	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Column> getChildren() {
		return children;
	}

	public void setChildren(List<Column> children) {
		this.children = children;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column that = (Column) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}

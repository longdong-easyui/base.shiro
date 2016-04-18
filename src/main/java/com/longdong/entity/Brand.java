package com.longdong.entity;

/**
 * <p>User: 
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class Brand extends BaseEntity{
    private Long id; //编号
    private String name; //品牌名称
    private Integer type; //类型
    private byte[] logo;
    private String url;
    private Integer sortNo;
    private String content;	//内容
    
    private Integer available ;			//0:可用，1：禁用
    private String availableStr;
   
   
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
        this.name = name;
    }
    
    public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
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

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand that = (Brand) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}

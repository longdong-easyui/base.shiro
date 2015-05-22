package com.longdong.entity;


/**
 * <p>User: 
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class SpecDetail extends BaseEntity{
    private Long id; 
    private Long specId;
    private String specName; 
   
    private byte[] specValue;
    private Integer sortNo;
   
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	public Long getSpecId() {
		return specId;
	}

	public void setSpecId(Long specId) {
		this.specId = specId;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public byte[] getSpecValue() {
		return specValue;
	}

	public void setSpecValue(byte[] specValue) {
		this.specValue = specValue;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}


	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecDetail that = (SpecDetail) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

  
    
}

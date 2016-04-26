package com.longdong.entity;


public class Article extends BaseEntity{
    private Long id; //编号
    private String title; //标题
    private String subTitle; 	//副标题
    
    private String type; //文章类型
    private byte[] thumbnail;  //缩略图
    private byte[] content;
    
    private Integer sortNo;
    private Integer available ;			//0:可用，1：禁用
    private String availableStr;
   
    private Integer isTop ;			//0:置顶，1：不置顶
    private String isTopStr;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		if(isTop==0){
    		this.availableStr ="置顶";
    	}else{
    		this.availableStr ="不置顶";
    	}
		this.isTop = isTop;
	}
	public String getIsTopStr() {
		return isTopStr;
	}
	public void setIsTopStr(String isTopStr) {
		this.isTopStr = isTopStr;
	}
	public byte[] getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
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
   
  
}

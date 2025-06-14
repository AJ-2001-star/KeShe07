package com.example.mentalhealth.publish;

import java.util.Date;

public class TopicRequest {
    private long date = System.currentTimeMillis() / 1000;
    private String country = "China";
    private String images = "";
    private int isLike = 1;
    private int gender = 0;
    private String avatarUrl = "";
    private String city = "";
    private String openid = "default_openid";
    private String nickName = "匿名用户";
    private String language = "zh";
    private String updateTime = "2025-06-08 04:26:09";
    private String remark = "";
    private String params = "";
    private String content;
    private String createdAt = "2025-06-08 04:26:09";
    private String createBy = "default_user";
    private String province = "";
    private String createTime = "2025-06-08 04:26:09";
    private String updateBy = "";
    private int id ;
    private String searchValue = "";
    private String updatedAt = "2025-06-08 04:26:09";

    public TopicRequest() {
        this.content = "这是一个默认的发布内容";
        this.date = System.currentTimeMillis() / 1000;
        this.isLike = 1;
        this.gender = 0;
        this.openid = "default_openid";
        this.nickName = "匿名用户";
        this.language = "zh";
    }

    public TopicRequest(String content) {
        this.content = content;
        this.date = System.currentTimeMillis() / 1000;
        this.isLike = 1;
        this.gender = 0;
        this.openid = "default_openid";
        this.nickName = "匿名用户";
        this.language = "zh";
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

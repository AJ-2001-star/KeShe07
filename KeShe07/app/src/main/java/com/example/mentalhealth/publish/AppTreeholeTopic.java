package com.example.mentalhealth.publish;

import java.util.Date;

public class AppTreeholeTopic {
    private Long id;
    private String openid = "default_openid";
    private String content;
    private Long date = System.currentTimeMillis() / 1000;
    private String images = "";
    private Integer isLike = 1;
    private String avatarUrl = "";
    private String city = "";
    private String country = "China";
    private Long gender = 0L;
    private String language = "zh";
    private String nickName = "匿名用户";
    private String province = "";
    private Date createdAt ;
    private Date updatedAt ;


    public AppTreeholeTopic(String content) {
        this.date = System.currentTimeMillis() / 1000;
        this.isLike = 1;
        this.openid = "default_openid";
        this.nickName = "匿名用户";
        this.language = "zh";
        this.content = content;
        this.images = "[\"https://example.com/images/2.jpg\"]";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOpenid() { return openid; }
    public void setOpenid(String openid) { this.openid = openid; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getDate() { return date; }
    public void setDate(Long date) { this.date = date; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public Integer getIsLike() { return isLike; }
    public void setIsLike(Integer isLike) { this.isLike = isLike; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Long getGender() { return gender; }
    public void setGender(Long gender) { this.gender = gender; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
package com.example.mentalhealth.domain;

public class Comment {
    private Integer id;
    private String openid;
    private String content;
    private String date;
    private String rId; // 关联的帖子 ID
    private String uId;

    public Comment(Integer id, String openid, String content, String date, String rId, String uId) {
        this.id = id;
        this.openid = openid;
        this.content = content;
        this.date = date;
        this.rId = rId;
        this.uId = uId;
    }

    public Integer getId() {
        return id;
    }

    public String getOpenid() {
        return openid;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getrId() {
        return rId;
    }

    public String getuId() {
        return uId;
    }

    public String getUsername() {
        return "default_openid";
    }
}

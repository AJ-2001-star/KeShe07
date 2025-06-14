package com.example.mentalhealth.vo;

import com.example.mentalhealth.domain.Article;

/**
 * 表示文章详情的响应对象。
 * 包含响应状态码、消息和文章数据。
 */
public class ArticleDetailResponse {
    private int code; // 响应状态码
    private String msg; // 响应消息
    private Article data; // 文章数据

    // 获取响应状态码
    public int getCode() {
        return code;
    }

    // 获取响应消息
    public String getMsg() {
        return msg;
    }

    // 获取文章数据
    public Article getData() {
        return data;
    }
}

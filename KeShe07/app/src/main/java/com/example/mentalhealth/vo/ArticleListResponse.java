package com.example.mentalhealth.vo;

import com.example.mentalhealth.domain.Article;

import java.util.List;

/**
 * 表示文章列表的响应对象。
 * 包含响应状态码、消息、总数和文章列表。
 */
public class ArticleListResponse {
    private int code; // 响应状态码
    private String msg; // 响应消息
    private int total; // 文章总数
    private List<Article> rows; // 文章列表

    // 获取响应状态码
    public int getCode() {
        return code;
    }

    // 获取响应消息
    public String getMsg() {
        return msg;
    }

    // 获取文章总数
    public int getTotal() {
        return total;
    }

    // 获取文章列表
    public List<Article> getRows() {
        return rows;
    }
}

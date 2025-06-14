package com.example.mentalhealth.vo;

import com.example.mentalhealth.domain.AppTreeholeReply;
import com.example.mentalhealth.domain.Comment;

import java.util.List;

/**
 * 表示帖子详情的响应对象。
 * 包含总数、回复列表、响应状态码和消息。
 */
public class PostDetailResponse {
    private int total; // 回复总数
    private List<AppTreeholeReply> rows; // 回复列表
    private int code; // 响应状态码
    private String msg; // 响应消息

    // 构造函数
    public PostDetailResponse(int total, List<AppTreeholeReply> rows, int code, String msg) {
        this.total = total;
        this.rows = rows;
        this.code = code;
        this.msg = msg;
    }

    // 获取回复总数
    public int getTotal() {
        return total;
    }

    // 获取回复列表
    public List<AppTreeholeReply> getRows() {
        return rows;
    }

    // 获取响应状态码
    public int getCode() {
        return code;
    }

    // 设置回复总数
    public void setTotal(int total) {
        this.total = total;
    }

    // 设置回复列表
    public void setRows(List<AppTreeholeReply> rows) {
        this.rows = rows;
    }

    // 设置响应状态码
    public void setCode(int code) {
        this.code = code;
    }

    // 设置响应消息
    public void setMsg(String msg) {
        this.msg = msg;
    }

    // 获取响应消息
    public String getMsg() {
        return msg;
    }
}

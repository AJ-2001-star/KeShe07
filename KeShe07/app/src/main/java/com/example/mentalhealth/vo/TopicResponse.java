package com.example.mentalhealth.vo;

import com.example.mentalhealth.domain.PostItem;

import java.util.List;

/**
 * 表示主题的响应对象。
 * 包含总数、帖子列表、响应状态码和消息。
 */
public class TopicResponse {
    private int total; // 帖子总数
    private List<PostItem> rows; // 帖子列表
    private int code; // 响应状态码
    private String msg; // 响应消息

    /**
     * 判断响应是否成功。
     * @return 如果状态码为 200，则返回 true；否则返回 false。
     */
    public boolean isSuccess() {
        return code == 200;
    }

    /**
     * 获取帖子列表。
     * @return 帖子列表
     */
    public List<PostItem> getPosts() {
        return rows;
    }
}

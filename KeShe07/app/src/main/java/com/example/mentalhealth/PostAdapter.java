package com.example.mentalhealth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// PostAdapter.java
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    //添加点击监听
    private OnPostItemClickListener listener;

    public interface OnPostItemClickListener { // 修改接口名称
        void onItemClick(Post post);
    }

    public PostAdapter(List<Post> postList, OnPostItemClickListener listener) {
        this.postList = postList;
        this.listener = listener;
    }

    @NotNull
    @Override
    public PostViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        // 点击整个 Item 触发
        view.setOnClickListener(v -> {
            int position = ((RecyclerView.ViewHolder) v.getTag()).getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(postList.get(position));
            }
        });
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.tvUsername.setText(post.getUsername());
        holder.tvContent.setText(post.getContent());
        holder.tvTime.setText(formatTime(post.getTimestamp()));

        // 绑定时设置 tag，方便点击获取位置
        holder.itemView.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private String formatTime(long timestamp) {
        // 时间转换
        long now = System.currentTimeMillis() / 1000;
        long diff = now - timestamp;

        if (diff < 60) {
            return "刚刚";
        } else if (diff < 60 * 60) {
            return diff / 60 + "分钟前";
        } else if (diff < 60 * 60 * 24) {
            return diff / (60 * 60) + "小时前";
        } else if (diff < 60 * 60 * 24 * 7) {
            return diff / (60 * 60 * 24) + "天前";
        } else if (diff < 60 * 60 * 24 * 30) {
            return diff / (60 * 60 * 24 * 7) + "周前";
        } else if (diff < 60 * 60 * 24 * 365) {
            return diff / (60 * 60 * 24 * 30) + "月前";
        } else {
            return diff / (60 * 60 * 24 * 365) + "年前";
        }
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvContent, tvTime;

        PostViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}

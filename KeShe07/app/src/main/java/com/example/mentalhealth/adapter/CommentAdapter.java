
// 包声明，定义该类属于 com.example.mentalhealth.adapter 包
package com.example.mentalhealth.adapter;

// 导入所需的类
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.PostDetailActivity;
import com.example.mentalhealth.R;
import com.example.mentalhealth.domain.Comment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * CommentAdapter 是一个 RecyclerView 适配器，用于显示评论列表。
 * 它将 Comment 数据绑定到 RecyclerView 的视图项中。
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    // 保存评论数据的列表
    private List<Comment> mComments;

    /**
     * 构造函数，初始化评论列表。
     *
     * @param comments 评论数据列表
     */
    public CommentAdapter(List<Comment> comments) {
        mComments = comments;
    }

    /**
     * 创建 ViewHolder 对象并初始化视图。
     *
     * @param parent   父视图组
     * @param viewType 视图类型（此处未使用）
     * @return 新的 CommentViewHolder 对象
     */
    @NotNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        // 使用布局填充器加载 item_comment 布局
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    /**
     * 将数据绑定到 ViewHolder。
     *
     * @param holder   当前的 ViewHolder
     * @param position 数据在列表中的位置
     */
    @Override
    public void onBindViewHolder(@NotNull CommentViewHolder holder, int position) {
        // 获取当前位置的评论数据
        Comment comment = mComments.get(position);
        // 调用 ViewHolder 的 bind 方法绑定数据
        holder.bind(comment);
    }

    /**
     * 返回评论列表的大小。
     *
     * @return 评论列表的大小
     */
    @Override
    public int getItemCount() {
        return mComments.size();
    }

    /**
     * 内部类 CommentViewHolder，用于管理单个评论项的视图。
     */
    class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 定义视图组件
        private TextView tvUsername;
        private TextView tvContent;
        private TextView tvTime;

        /**
         * 构造函数，初始化视图组件。
         *
         * @param itemView 单个评论项的视图
         */
        public CommentViewHolder(@NotNull View itemView) {
            super(itemView);
            // 绑定视图组件
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);

            // 设置点击事件监听器
            itemView.setOnClickListener(this);
        }

        /**
         * 将评论数据绑定到视图组件。
         *
         * @param comment 当前评论数据
         */
        public void bind(Comment comment) {
            // 设置用户名、评论内容和时间
            tvUsername.setText(comment.getuId());
            tvContent.setText(comment.getContent());
            tvTime.setText(comment.getDate());
        }

        /**
         * 处理视图项的点击事件。
         *
         * @param v 被点击的视图
         */
        @Override
        public void onClick(View v) {
            // 获取当前点击项的位置
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // 获取对应位置的评论数据
                Comment comment = mComments.get(position);

                // 弹出 Toast 显示评论详情
                String message = "用户：" + comment.getuId() + "\n" +
                        "内容：" + comment.getContent() + "\n" +
                        "时间：" + comment.getDate();

                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}


// 包声明
package com.example.mentalhealth.adapter;

// 导入必要的类
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.R;
import com.example.mentalhealth.domain.Article;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 适配器类，用于在 RecyclerView 中显示文章列表。
 * 每个文章项包括标题和摘要，并支持点击事件。
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    // 文章列表数据源
    private List<Article> articleList;

    // 点击事件监听器
    private OnItemClickListener onItemClickListener;

    /**
     * 定义点击事件接口，用于处理文章项的点击事件。
     */
    public interface OnItemClickListener {
        void onItemClick(Article article); // 点击事件回调方法
    }

    /**
     * 构造方法，初始化适配器。
     *
     * @param articleList 文章列表数据
     * @param listener    点击事件监听器
     */
    public ArticleAdapter(List<Article> articleList, OnItemClickListener listener) {
        this.articleList = articleList;
        this.onItemClickListener = listener;
    }

    /**
     * 创建 ViewHolder，用于表示单个文章项的视图。
     *
     * @param parent   父视图组
     * @param viewType 视图类型（此处未使用）
     * @return 创建的 ArticleViewHolder
     */
    @NotNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        // 加载文章项布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    /**
     * 绑定数据到 ViewHolder。
     *
     * @param holder   当前文章项的 ViewHolder
     * @param position 当前文章项在列表中的位置
     */
    @Override
    public void onBindViewHolder(@NotNull ArticleViewHolder holder, int position) {
        // 获取当前文章
        Article article = articleList.get(position);

        // 设置标题，如果标题为空则显示“无标题”
        String title = article.getTitle() != null ? article.getTitle() : "无标题";
        holder.title.setText(title);

        // 设置摘要，截取内容的前50个字符，如果内容不足50字符则显示完整内容
        String content = article.getContent() != null ? article.getContent() : "";
        String summaryText = content.length() > 50 ? content.substring(0, 50) + "..." : content;
        holder.summary.setText(summaryText);

        // 设置点击事件，触发回调方法
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(article));
    }

    /**
     * 获取文章列表的总数。
     *
     * @return 文章列表的大小
     */
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    /**
     * 静态内部类，用于表示单个文章项的视图。
     */
    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        // 文章标题 TextView
        TextView title;

        // 文章摘要 TextView
        TextView summary;

        /**
         * 构造方法，初始化视图组件。
         *
         * @param itemView 当前文章项的根视图
         */
        public ArticleViewHolder(@NotNull View itemView) {
            super(itemView);
            // 绑定标题和摘要的视图
            title = itemView.findViewById(R.id.textViewTitle);
            summary = itemView.findViewById(R.id.textViewSummary);
        }
    }
}

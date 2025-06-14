package com.example.mentalhealth.yizhan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mentalhealth.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 显示文章详情的 Fragment。
 */
public class ArticleDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);

        // 获取传递的参数
        Bundle args = getArguments();
        if (args != null) {
            // 从参数中提取文章详情
            String title = args.getString("title");
            String content = args.getString("content");
            String author = args.getString("author");
            String publishTime = args.getString("publishTime");
            String coverImageUrl = args.getString("coverImageUrl");

            // 获取布局中的控件
            TextView titleView = view.findViewById(R.id.textViewArticleTitle);
            TextView authorView = view.findViewById(R.id.textViewArticleAuthor);
            TextView timeView = view.findViewById(R.id.textViewArticlePublishTime);
            TextView contentView = view.findViewById(R.id.textViewArticleContent);
            ImageView coverView = view.findViewById(R.id.imageViewCover);

            // 设置控件内容
            if (title != null) titleView.setText(title); // 设置标题
            if (author != null) authorView.setText("作者：" + author); // 设置作者
            if (publishTime != null) timeView.setText("发布时间：" + publishTime); // 设置发布时间
            if (content != null) contentView.setText(content); // 设置文章内容

            // 使用 Glide 加载封面图片
            if (coverImageUrl != null && !coverImageUrl.isEmpty()) {
                Glide.with(this)
                        .load(coverImageUrl)
                        .into(coverView);
            }
        }

        return view;
    }
}

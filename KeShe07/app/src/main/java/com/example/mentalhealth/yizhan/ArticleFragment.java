package com.example.mentalhealth.yizhan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.R;
import com.example.mentalhealth.adapter.ArticleAdapter;
import com.example.mentalhealth.domain.Article;
import com.example.mentalhealth.vo.ArticleDetailResponse;
import com.example.mentalhealth.vo.ArticleListResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArticleFragment extends Fragment {
    private RecyclerView recyclerView; // RecyclerView 用于显示文章列表
    private ArticleAdapter articleAdapter; // 适配器，用于绑定数据
    private List<Article> articleList; // 文章列表数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        // 初始化 RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewArticles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // 设置布局管理器
        articleList = new ArrayList<>(); // 初始化文章列表

        // 初始化适配器并绑定点击事件
        articleAdapter = new ArticleAdapter(articleList, this::navigateToArticleDetail);
        recyclerView.setAdapter(articleAdapter);

        // 获取文章数据
        fetchArticles();

        return view;
    }

    // 从后端接口获取文章列表
    private void fetchArticles() {
        OkHttpClient client = new OkHttpClient();

        // 构建请求
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/stage/articles/list")
                .build();

        // 异步请求数据
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败时显示错误信息
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "加载失败：" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // 解析 JSON 数据
                    Gson gson = new Gson();
                    ArticleListResponse articleListResponse = gson.fromJson(response.body().string(), ArticleListResponse.class);

                    if (articleListResponse.getCode() == 200) {
                        // 更新文章列表
                        articleList.clear();
                        articleList.addAll(articleListResponse.getRows());

                        // 通知适配器数据已更新
                        getActivity().runOnUiThread(() -> articleAdapter.notifyDataSetChanged());
                    }
                }
            }
        });
    }

    // 跳转到文章详情页面
    private void navigateToArticleDetail(Article article) {
        int articleId = article.getId(); // 获取文章 ID
        OkHttpClient client = new OkHttpClient();

        // 构建请求
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/stage/articles/" + articleId)
                .build();

        // 异步请求文章详情
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败时显示错误信息
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "获取文章详情失败：" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // 解析 JSON 数据
                    Gson gson = new Gson();
                    ArticleDetailResponse detailResponse = gson.fromJson(response.body().string(), ArticleDetailResponse.class);

                    if (detailResponse.getCode() == 200) {
                        // 获取文章详情
                        Article articleDetail = detailResponse.getData();

                        // 构建参数并跳转到详情页面
                        ArticleDetailFragment detailFragment = new ArticleDetailFragment();
                        Bundle args = new Bundle();
                        args.putString("title", articleDetail.getTitle());
                        args.putString("content", articleDetail.getContent());
                        args.putString("author", articleDetail.getAuthor());
                        args.putString("publishTime", articleDetail.getPublishTime());
                        args.putString("coverImageUrl", articleDetail.getCoverImageUrl());
                        detailFragment.setArguments(args);

                        // 替换当前 Fragment
                        getActivity().runOnUiThread(() -> {
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, detailFragment)
                                    .addToBackStack(null)
                                    .commit();
                        });
                    }
                }
            }
        });
    }
}

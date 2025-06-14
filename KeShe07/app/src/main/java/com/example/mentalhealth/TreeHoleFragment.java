package com.example.mentalhealth;

import android.content.Intent;
import android.os.Bundle;
import org.jetbrains.annotations.NotNull;




import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentalhealth.domain.PostItem;
import com.example.mentalhealth.vo.TopicResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TreeHoleFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<Post> postList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treehole, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new PostAdapter(postList, post -> {
            // 点击 Item 后跳转到详情页
            Intent intent = new Intent(getActivity(), PostDetailActivity.class);
            intent.putExtra("post", post);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        fetchData();

        return view;
    }

    private void fetchData() {
//        postList.clear();
//        postList.add(new Post("1", "微信用户", "今天有点小emo，有没有人能聊聊天", System.currentTimeMillis() / 1000 - 3600));
//        postList.add(new Post("2", "微信用户", "测试111", System.currentTimeMillis() / 1000 - 7200));
//        postList.add(new Post("3", "微信用户", "测试023", System.currentTimeMillis() / 1000 - 86400));
//        postList.add(new Post("4", "灿若夏花", "今天心情不好", System.currentTimeMillis() / 1000 - 604800));
//
//        adapter.notifyDataSetChanged();
        postList.clear();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/treehole/topic/list")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // 网络请求失败处理
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    Gson gson = new Gson();
                    TopicResponse topicResponse = gson.fromJson(response.body().charStream(), TopicResponse.class);

                    if (topicResponse.isSuccess()) {
                        getActivity().runOnUiThread(() -> {
                            for (PostItem item : topicResponse.getPosts()) {
                                // 将 PostItem 转换为 Post 类型并加入列表
                                postList.add(new Post(
                                        String.valueOf(item.getId()),
                                        item.getNickName(),
                                        item.getContent(),
                                        item.getDate()
                                ));
                            }
                            adapter.notifyDataSetChanged();
                        });
                    }
                }
            }
        });

    }
}

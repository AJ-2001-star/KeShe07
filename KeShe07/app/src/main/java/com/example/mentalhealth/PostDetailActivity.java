package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentalhealth.adapter.CommentAdapter;
import com.example.mentalhealth.domain.AppTreeholeReply;
import com.example.mentalhealth.domain.Comment;
import com.example.mentalhealth.util.DateTypeAdapter;
import com.example.mentalhealth.vo.PostDetailResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostDetailActivity extends AppCompatActivity {

    private static final String TAG = "PostDetailActivity";
    private EditText etInput;
    private Button btnSend;
    private RecyclerView recyclerView; // 👈 全局声明 recyclerView

    // 定义接口地址
    private static final String BASE_URL = "http://10.0.2.2:8080/treehole/reply/list";
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = createCustomGson();

    private Post post; // 👈 保存接收的 Post 对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        TextView tvPostTitle = findViewById(R.id.tv_post_title);
        TextView tvPostContent = findViewById(R.id.tv_post_content);
        recyclerView = findViewById(R.id.recycler_view_comments); // 👈 初始化

        etInput = findViewById(R.id.et_comment_input);
        btnSend = findViewById(R.id.btn_send);

        // 接收 Post 数据
        this.post = getIntent().getParcelableExtra("post");

        if (post != null) {
            tvPostTitle.setText("回复作者：" + post.getUsername());
            tvPostContent.setText(post.getContent());
            fetchCommentsFromServer(post.getId()); // 使用真实 ID 请求评论
        } else {
            Toast.makeText(this, "帖子数据异常", Toast.LENGTH_SHORT).show();
            finish(); // 没有数据则关闭页面
        }
    }

    /**
     * 从服务器获取评论数据
     */
    private void fetchCommentsFromServer(String rId) {
        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addQueryParameter("rId", rId)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(PostDetailActivity.this,
                        "网络请求失败：" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();

                    try {
                        // 👇 使用 PostDetailResponse 接收完整响应
                        PostDetailResponse postDetailResponse = gson.fromJson(responseBody, PostDetailResponse.class);

                        if (postDetailResponse.getRows() != null && !postDetailResponse.getRows().isEmpty()) {
                            runOnUiThread(() -> {
                                List<Comment> mComments = convertToCommentList(postDetailResponse.getRows());
                                CommentAdapter adapter = new CommentAdapter(mComments);
                                recyclerView.setLayoutManager(new LinearLayoutManager(PostDetailActivity.this));
                                recyclerView.setAdapter(adapter);

                                setupSendButtonListener(mComments, adapter);
                            });
                        } else {
                            runOnUiThread(() -> Toast.makeText(PostDetailActivity.this,
                                    "没有评论数据", Toast.LENGTH_SHORT).show());
                        }

                    } catch (JsonSyntaxException e) {
                        runOnUiThread(() -> Toast.makeText(PostDetailActivity.this,
                                "服务器返回数据格式错误", Toast.LENGTH_SHORT).show());
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(PostDetailActivity.this,
                            "无法加载评论", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }


    /**
     * 将 AppTreeholeReply 转换为 Comment 对象
     */
    private List<Comment> convertToCommentList(List<AppTreeholeReply> replies) {
        List<Comment> comments = new ArrayList<>();
        for (AppTreeholeReply reply : replies) {
            int commentId;
            try {
                commentId = Math.toIntExact(reply.getId());
            } catch (ArithmeticException e) {
                commentId = Integer.MAX_VALUE; // ID 过大时取最大值
            }

            Comment comment = new Comment(
                    commentId,
                    reply.getOpenid(),
                    reply.getContent(),
                    reply.getDate().toString(),
                    reply.getrId(),
                    reply.getuId()
            );
            comments.add(comment);
        }
        return comments;
    }

    /**
     * 设置发送按钮点击事件监听器
     */
    private void setupSendButtonListener(List<Comment> mComments, CommentAdapter adapter) {
        btnSend.setOnClickListener(v -> {
            String input = etInput.getText().toString().trim();
            if (!input.isEmpty() && post != null) {
                String currentUserId = "user_001"; // 示例用户 ID，可替换为真实登录用户

                Comment newComment = new Comment(
                        mComments.size() + 1,
                        "当前用户",
                        input,
                        "刚刚",
                        post.getId(),
                        currentUserId
                );

                // 👇 添加到列表顶部
                mComments.add(0, newComment);
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);

                // 👇 构建要发送给服务器的 AppTreeholeReply 对象
                AppTreeholeReply reply = new AppTreeholeReply();
                reply.setId((long) newComment.getId());
                reply.setOpenid(newComment.getUsername());
                reply.setContent(newComment.getContent());
                reply.setrId(newComment.getrId());
                reply.setuId(currentUserId);
                reply.setDate(new Date()); // 当前时间
                reply.setCreatedAt(new Date());
                reply.setUpdatedAt(new Date());

                // 👇 发送新评论到服务器
                sendCommentToServer(reply);
                etInput.setText("");
            }
        });
    }
    /**
     * 发送评论到服务器
     */
    private void sendCommentToServer(AppTreeholeReply reply) {
        String url = "http://10.0.2.2:8080/treehole/reply";

        // 使用 Gson 将对象转换为 JSON
        String jsonBody = gson.toJson(reply);

        // 创建请求体
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(jsonBody, mediaType);

        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // 异步发送请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(PostDetailActivity.this,
                        "评论提交失败：" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(PostDetailActivity.this,
                            "评论已提交", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(PostDetailActivity.this,
                            "提交失败，状态码：" + response.code(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }


    //创建支持 yyyy-MM-dd 格式的 Gson
    private Gson createCustomGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
    }
}

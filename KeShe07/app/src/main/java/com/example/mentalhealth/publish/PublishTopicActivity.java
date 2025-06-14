package com.example.mentalhealth.publish;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealth.R;
import com.example.mentalhealth.publish.AppTreeholeTopic;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishTopicActivity extends AppCompatActivity {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_topic);

        EditText editTextContent = findViewById(R.id.editTextContent);
        Button buttonPublish = findViewById(R.id.buttonPublish);

        buttonPublish.setOnClickListener(v -> {
            String content = editTextContent.getText().toString().trim();
            if (content.isEmpty()) return;

            AppTreeholeTopic topic = new AppTreeholeTopic(content);
            String jsonBody = new Gson().toJson(topic);

            postTopic(jsonBody);
        });
    }

    private void postTopic(String json) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/treehole/topic")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(PublishTopicActivity.this, "发布失败，请检查网络", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(PublishTopicActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                        finish(); // 返回上一页
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(PublishTopicActivity.this, "服务器错误：" + response.code(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}

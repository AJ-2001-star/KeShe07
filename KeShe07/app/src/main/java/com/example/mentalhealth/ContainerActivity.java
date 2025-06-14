package com.example.mentalhealth;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mentalhealth.yizhan.AiAssistantFragment;

import org.jetbrains.annotations.Nullable;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        // 默认加载首页（比如 PostFragment）
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new PostFragment())
                    .commit();
        }
    }

    // 处理语音识别返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 将结果转发给当前显示的 Fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment instanceof AiAssistantFragment) {
            ((AiAssistantFragment) currentFragment).onActivityResult(requestCode, resultCode, data);
        }
    }
}
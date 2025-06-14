package com.example.mentalhealth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mentalhealth.yizhan.AiAssistantFragment;
import com.example.mentalhealth.yizhan.ArticleFragment;
import com.example.mentalhealth.yizhan.MusicFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PostFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加载布局
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        // 获取卡片视图
        View cardArticle = view.findViewById(R.id.card_article);
        View cardMusic = view.findViewById(R.id.card_music);
        View cardAiAssistant = view.findViewById(R.id.card_ai_assistant);

        // 文章卡片点击事件
        cardArticle.setOnClickListener(v -> {
            // 跳转到文章页面
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ArticleFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 音乐卡片点击事件
        cardMusic.setOnClickListener(v -> {
            // 跳转到音乐页面
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new MusicFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // AI 对话助手卡片点击事件
        cardAiAssistant.setOnClickListener(v -> {
            // 跳转到 AI 对话助手页面
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AiAssistantFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}

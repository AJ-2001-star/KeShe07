package com.example.mentalhealth.yizhan;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mentalhealth.R;

public class MusicFragment extends Fragment {
    private MediaPlayer mediaPlayer; // 媒体播放器
    private boolean isPlaying = false; // 播放状态
    private Handler handler = new Handler(); // 用于更新 UI 的 Handler
    private SeekBar seekBar; // 进度条
    private TextView textViewCurrentTime, textViewTotalTime; // 当前时间和总时间
    private ImageButton buttonPlayPause; // 播放/暂停按钮

    // 引用本地资源
    private static final int AUDIO_RES_ID = R.raw.music_01;

    public MusicFragment() {
        // 默认构造函数
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        // 初始化控件
        textViewCurrentTime = view.findViewById(R.id.textViewCurrentTime);
        textViewTotalTime = view.findViewById(R.id.textViewTotalTime);
        seekBar = view.findViewById(R.id.seekBarProgress);
        buttonPlayPause = view.findViewById(R.id.buttonPlayPause);

        // 初始化 MediaPlayer 并加载本地资源
        mediaPlayer = MediaPlayer.create(requireContext(), AUDIO_RES_ID);

        if (mediaPlayer != null) {
            // 设置进度条最大值为音频总时长
            int duration = mediaPlayer.getDuration();
            seekBar.setMax(duration);
            textViewTotalTime.setText(formatTime(duration)); // 显示总时长
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play); // 设置初始图标

            // 设置播放/暂停按钮点击事件
            buttonPlayPause.setOnClickListener(v -> togglePlay());
        }

        return view;
    }

    // 切换播放状态
    private void togglePlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // 暂停播放
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play); // 更新图标
        } else {
            mediaPlayer.start(); // 开始播放
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause); // 更新图标
            updateSeekBarAndTime(); // 更新进度条和时间
        }
    }

    /**
     * 更新进度条和播放时间
     * 通过 Handler 定期调用此方法实现 UI 更新
     */
    private void updateSeekBarAndTime() {
        handler.postDelayed(updateRunnable, 1000); // 每秒更新一次
    }

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                // 更新进度条和当前时间
                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                textViewCurrentTime.setText(formatTime(currentPosition));
                updateSeekBarAndTime(); // 递归调用
            }
        }
    };

    // 格式化时间为 mm:ss
    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放资源
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            handler.removeCallbacks(updateRunnable); // 移除回调
        }
    }
}

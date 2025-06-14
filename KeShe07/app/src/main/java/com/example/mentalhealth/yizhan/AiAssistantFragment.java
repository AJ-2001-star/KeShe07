package com.example.mentalhealth.yizhan;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AiAssistantFragment extends Fragment {

    private EditText etInput;
    private Button btnSend, btnVoice;
    private RecyclerView rvChat;
    private ChatAdapter adapter;
    //private final OkHttpClient client = new OkHttpClient();
    private static final int REQUEST_CODE_SPEECH_INPUT = 1001;
    private TextToSpeech textToSpeech;

    private static final int REQUEST_TTS_CHECK = 888;

    private final OkHttpClient client;

    public AiAssistantFragment() {
        // 初始化 OkHttpClient 并设置超时
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS) // 连接超时
                .readTimeout(60, TimeUnit.SECONDS)    // 读取超时
                .writeTimeout(60, TimeUnit.SECONDS)   // 写入超时
                .build();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkTTSStatus();
    }

    private void checkTTSStatus() {
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, REQUEST_TTS_CHECK);
    }


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ai_assistant, container, false);

        // 初始化视图
        etInput = view.findViewById(R.id.et_input);
        btnSend = view.findViewById(R.id.btn_send);
        btnVoice = view.findViewById(R.id.btn_voice);
        rvChat = view.findViewById(R.id.rv_chat);

        // 初始化 TTS
        textToSpeech = new TextToSpeech(requireContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.CHINESE);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(requireContext(), "语言不支持", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "TTS 初始化失败", Toast.LENGTH_SHORT).show();
            }
        });

        // 设置 RecyclerView
        adapter = new ChatAdapter();
        rvChat.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvChat.setAdapter(adapter);

        // 发送按钮点击事件
        btnSend.setOnClickListener(v -> {
            String input = etInput.getText().toString().trim();
            if (!input.isEmpty()) {
                adapter.addMessage(new ChatMessage(input, true));
                getAiReplyFromServer(input, false); // 不需要朗读
                etInput.setText("");
            }
        });

        // 语音按钮点击事件
        btnVoice.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-CN");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请开始说话");

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(requireContext(), "设备不支持语音识别", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // 网络请求获取 AI 回复（新增 boolean 参数）
    private void getAiReplyFromServer(String input, boolean shouldSpeak) {
        String url = "http://10.0.2.2:8080/ai/chat?prompt=" + Uri.encode(input);

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        adapter.addMessage(new ChatMessage("网络请求失败，请检查连接", false))
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String reply = response.body().string();
                    requireActivity().runOnUiThread(() -> {
                        adapter.addMessage(new ChatMessage(reply, false));
                        if (shouldSpeak) {
                            speakOut(reply); // 只有在语音输入后才朗读
                        }
                    });
                } else {
                    requireActivity().runOnUiThread(() ->
                            adapter.addMessage(new ChatMessage("无法获取回复", false))
                    );
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TTS_CHECK) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // TTS 数据已存在，初始化 TTS
                initTextToSpeech();
            } else {
                // 提示用户安装 TTS 数据
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }

        // 原有的语音识别回调逻辑
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == getActivity().RESULT_OK && data != null) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String spokenText = results.get(0);
                handleVoiceInput(spokenText);
            }
        }
    }

    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(requireContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.CHINESE);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(requireContext(), "语言数据缺失或不支持", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "TTS 初始化失败，请检查设备支持情况", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void handleVoiceInput(String text) {
        etInput.setText(text);
        adapter.addMessage(new ChatMessage(text, true)); // 用户消息
        getAiReplyFromServer(text, true); // 需要朗读
    }

    // 语音播报方法
    private void speakOut(String text) {
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_message");
            } else {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    // 聊天消息类
    static class ChatMessage {
        String message;
        boolean isUser;

        ChatMessage(String message, boolean isUser) {
            this.message = message;
            this.isUser = isUser;
        }
    }

    // RecyclerView Adapter
    class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

        private List<ChatMessage> messages = new ArrayList<>();

        void addMessage(ChatMessage message) {
            messages.add(message);
            notifyDataSetChanged();
        }

        @NotNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_layout, parent, false);
            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NotNull ChatViewHolder holder, int position) {
            ChatMessage msg = messages.get(position);
            holder.bind(msg);

            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) params;

                if (msg.isUser) {
                    layoutParams.gravity = Gravity.END;
                } else {
                    layoutParams.gravity = Gravity.START;
                }

                holder.itemView.setLayoutParams(layoutParams);
            }
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        class ChatViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvMessage;

            ChatViewHolder(@NotNull View itemView) {
                super(itemView);
                tvMessage = itemView.findViewById(R.id.tv_message);
            }

            void bind(ChatMessage message) {
                tvMessage.setText(message.message);

                if (message.isUser) {
                    tvMessage.setBackgroundResource(R.drawable.message_bubble_user);
                } else {
                    tvMessage.setBackgroundResource(R.drawable.message_bubble_ai);
                }
            }
        }
    }
}

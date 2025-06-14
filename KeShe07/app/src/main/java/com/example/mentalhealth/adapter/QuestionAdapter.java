package com.example.mentalhealth.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.R;
import com.example.mentalhealth.test.data.Question;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * RecyclerView 适配器，用于显示问题列表。
 * 每个问题包含一个问题文本和多个选项。
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private final List<Question> questions; // 问题列表
    private final SparseArray<Integer> selectedAnswers = new SparseArray<>(); // 存储用户选择的答案

    /**
     * 构造函数，初始化问题列表。
     * @param questions 问题列表
     */
    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * 计算用户的总分数。
     * @return 总分数
     */
    public int getTotalScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Integer ans = selectedAnswers.get(i);
            if (ans != null) score += ans; // 累加用户选择的答案分数
        }
        return score;
    }

    @NotNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        // 加载问题项布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull QuestionViewHolder holder, int position) {
        // 绑定问题数据到 ViewHolder
        Question q = questions.get(position);
        holder.bind(q, position, selectedAnswers);
    }

    @Override
    public int getItemCount() {
        return questions.size(); // 返回问题数量
    }

    /**
     * 内部类，用于表示单个问题项的 ViewHolder。
     */
    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuestion; // 显示问题文本
        RadioGroup radioGroup; // 显示选项的单选按钮组

        public QuestionViewHolder(@NotNull View itemView) {
            super(itemView);
            // 初始化控件
            textViewQuestion = itemView.findViewById(R.id.textViewQuestion);
            radioGroup = itemView.findViewById(R.id.radioGroupOptions);
        }

        /**
         * 绑定问题数据到视图。
         * @param question 问题对象
         * @param position 当前问题的位置
         * @param answers 用户选择的答案集合
         */

        public void bind(Question question, int position, SparseArray<Integer> answers) {
            textViewQuestion.setText(question.getQuestionText()); // 设置问题文本
            radioGroup.removeAllViews(); // 清空之前的选项

            // 动态添加选项
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                RadioButton rb = new RadioButton(radioGroup.getContext());
                rb.setText(options[i]); // 设置选项文本
                rb.setTag(i); // 设置选项的标记
                radioGroup.addView(rb); // 添加到单选按钮组
            }

            // 设置选项选择监听器
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                int selected = group.indexOfChild(group.findViewById(checkedId)); // 获取选中的选项索引
                if (selected >= 0) {
                    answers.put(position, question.getScoreForOption(selected)); // 保存用户选择的答案分数
                }
            });
        }
    }
}

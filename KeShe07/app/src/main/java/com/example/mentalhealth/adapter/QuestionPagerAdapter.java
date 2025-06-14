package com.example.mentalhealth.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mentalhealth.R;
import com.example.mentalhealth.test.data.Question;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * RecyclerView 适配器，用于 ViewPager2 中显示问题列表。
 * 支持用户在回答问题后自动跳转到下一题。
 */
public class QuestionPagerAdapter extends RecyclerView.Adapter<QuestionPagerAdapter.QuestionViewHolder> {
    private final List<Question> questions; // 问题列表
    private final SparseArray<Integer> answers; // 用户选择的答案集合
    private final ViewPager2 viewPager; // ViewPager2 引用，用于切换页面

    /**
     * 构造函数，初始化问题列表、答案集合和 ViewPager。
     * @param questions 问题列表
     * @param answers 用户选择的答案集合
     * @param viewPager ViewPager2 引用
     */
    public QuestionPagerAdapter(List<Question> questions, SparseArray<Integer> answers, ViewPager2 viewPager) {
        this.questions = questions;
        this.answers = answers;
        this.viewPager = viewPager;
    }

    @NotNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        // 加载问题项布局
        return new QuestionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false), viewPager, questions);
    }

    @Override
    public void onBindViewHolder(@NotNull QuestionViewHolder holder, int position) {
        // 绑定问题数据到 ViewHolder
        holder.bind(questions.get(position), position, answers);
    }

    @Override
    public int getItemCount() {
        return questions.size(); // 返回问题数量
    }

    /**
     * 内部类，用于表示单个问题项的 ViewHolder。
     */
    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final RadioGroup radioGroup; // 显示选项的单选按钮组
        private ViewPager2 viewPager; // ViewPager2 引用
        private List<Question> questions; // 问题列表

        public QuestionViewHolder(@NotNull View itemView, ViewPager2 viewPager, List<Question> questions) {
            super(itemView);
            this.radioGroup = itemView.findViewById(R.id.radioGroupOptions);
            this.viewPager = viewPager;
            this.questions = questions;
        }

        /**
         * 绑定问题数据到视图。
         * @param question 问题对象
         * @param position 当前问题的位置
         * @param answers 用户选择的答案集合
         */
        public void bind(Question question, int position, SparseArray<Integer> answers) {
            TextView textViewQuestion = itemView.findViewById(R.id.textViewQuestion);
            textViewQuestion.setText(question.getQuestionText()); // 设置问题文本

            radioGroup.removeAllViews(); // 清空之前的选项

            // 动态添加选项
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                RadioButton rb = new RadioButton(radioGroup.getContext());
                rb.setText(options[i]); // 设置选项文本
                rb.setId(View.generateViewId()); // 动态生成唯一 ID
                radioGroup.addView(rb); // 添加到单选按钮组
            }

            // 设置已选中的答案（如果有）
            Integer selectedAnswer = answers.get(position);
            if (selectedAnswer != null && selectedAnswer >= 0 && selectedAnswer < options.length) {
                radioGroup.check(radioGroup.getChildAt(selectedAnswer).getId());
            }

            // 设置选项选择监听器
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                int selectedIndex = group.indexOfChild(group.findViewById(checkedId)); // 获取选中的选项索引
                if (selectedIndex >= 0) {
                    answers.put(position, selectedIndex); // 保存用户选择的答案

                    // 自动跳转到下一题
                    if (position < questions.size() - 1) {
                        viewPager.setCurrentItem(position + 1, true);
                    }
                }
            });
        }
    }
}

package com.example.mentalhealth.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.R;
import com.example.mentalhealth.test.data.Questionnaire;

import java.util.List;

/**
 * RecyclerView 适配器，用于显示测试列表。
 * 每个测试项包含标题、描述和问题数量。
 */
public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.TestViewHolder> {
    private List<Questionnaire> testList; // 测试列表
    private OnItemClickListener listener; // 点击事件监听器

    /**
     * 点击事件监听器接口。
     */
    public interface OnItemClickListener {
        void onItemClick(Questionnaire questionnaire); // 当测试项被点击时触发
    }

    /**
     * 构造函数，初始化测试列表和监听器。
     * @param testList 测试列表
     * @param listener 点击事件监听器
     */
    public TestListAdapter(List<Questionnaire> testList, OnItemClickListener listener) {
        this.testList = testList;
        this.listener = listener;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 加载测试项布局
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test_list, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        // 绑定测试数据到 ViewHolder
        Questionnaire test = testList.get(position);

        holder.title.setText(test.getTitle()); // 设置测试标题
        holder.description.setText("点击开始测试"); // 设置描述
        holder.questionCount.setText("共 " + test.getQuestionCount() + " 道题"); // 设置问题数量

        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(test); // 触发点击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size(); // 返回测试数量
    }

    /**
     * 内部类，用于表示单个测试项的 ViewHolder。
     */
    static class TestViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, questionCount; // 显示测试标题、描述和问题数量

        TestViewHolder(View itemView) {
            super(itemView);
            // 初始化控件
            title = itemView.findViewById(R.id.textViewTestTitle);
            description = itemView.findViewById(R.id.textViewTestDescription);
            questionCount = itemView.findViewById(R.id.textViewQuestionCount);
        }
    }
}

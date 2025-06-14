package com.example.mentalhealth.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mentalhealth.R;
import com.example.mentalhealth.adapter.QuestionPagerAdapter;
import com.example.mentalhealth.test.data.Conclusion;
import com.example.mentalhealth.test.data.ConclusionDao;
import com.example.mentalhealth.test.data.Question;
import com.example.mentalhealth.test.data.QuestionDao;
import com.example.mentalhealth.test.data.Questionnaire;
import com.example.mentalhealth.test.data.QuestionnaireDao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuestionnaireFragment extends Fragment {
    private final Questionnaire questionnaire;

    // 原来的 AppDatabase 改为三个 DAO
    private QuestionnaireDao questionnaireDao;
    private QuestionDao questionDao;
    private ConclusionDao conclusionDao;

    private List<Question> questions;
    private SparseArray<Integer> answers = new SparseArray<>();

    public QuestionnaireFragment(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questionnaire_pager, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.viewPagerQuestions);
        Button submitBtn = view.findViewById(R.id.buttonSubmit);

        // 初始化 DAO
        questionnaireDao = new QuestionnaireDao(requireContext());
        questionDao = new QuestionDao(requireContext());
        conclusionDao = new ConclusionDao(requireContext());

        new LoadQuestionsTask(viewPager, submitBtn).execute();

        submitBtn.setOnClickListener(v -> submitAnswers());
        return view;
    }


    // 将用户总分归一化为 1~4 的整数
    private int normalizeScore(int totalScore, int maxScore) {
        if (maxScore <= 0) return 1;
        double ratio = (double) totalScore / maxScore;
        return (int) Math.ceil(ratio * 4);
    }

    private void submitAnswers() {
        int totalScore = 0;

        for (Question question : questions) {
            Integer answerIndex = answers.get(questions.indexOf(question));
            if (answerIndex != null) {
                totalScore += question.getScoreForOption(answerIndex);
            }
        }

        // 获取结论
        Conclusion conclusion = conclusionDao.getConclusionByScoreRange(
                questionnaire.getQuestionnaireId(), totalScore);

        String result = "未找到匹配结论";
        if (conclusion != null) {
            result = conclusion.getConclusion();
        }

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new ConclusionFragment(result))
                .addToBackStack(null)
                .commit();
    }




    private class LoadQuestionsTask extends AsyncTask<Void, Void, List<Question>> {
        private final ViewPager2 viewPager;
        private final Button submitBtn;

        LoadQuestionsTask(ViewPager2 viewPager, Button submitBtn) {
            this.viewPager = viewPager;
            this.submitBtn = submitBtn;
        }

        @Override
        protected List<Question> doInBackground(Void... voids) {
            return questionDao.getQuestionsByQuestionnaireId(questionnaire.getQuestionnaireId());
        }

        @Override
        protected void onPostExecute(List<Question> result) {
            questions = result;
            QuestionPagerAdapter adapter = new QuestionPagerAdapter(result, answers, viewPager);
            viewPager.setAdapter(adapter);

            // 注册页面变化监听器
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    boolean isLastPage = position == result.size() - 1;
                    submitBtn.setVisibility(isLastPage ? View.VISIBLE : View.GONE);
                }
            });

            // 初始状态更新
            boolean isLastPage = viewPager.getCurrentItem() == result.size() - 1;
            submitBtn.setVisibility(isLastPage ? View.VISIBLE : View.GONE);
        }
    }
}
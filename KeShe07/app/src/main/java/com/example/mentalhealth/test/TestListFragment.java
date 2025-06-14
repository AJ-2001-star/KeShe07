package com.example.mentalhealth.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealth.R;
import com.example.mentalhealth.adapter.TestListAdapter;
import com.example.mentalhealth.test.data.Questionnaire;
import com.example.mentalhealth.test.data.QuestionnaireDao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestListFragment extends Fragment {
    private QuestionnaireDao questionnaireDao;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTests);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 初始化 DAO
        questionnaireDao = new QuestionnaireDao(requireContext());

        new LoadDataTask(recyclerView).execute();

        return view;
    }

    private class LoadDataTask extends android.os.AsyncTask<Void, Void, List<Questionnaire>> {
        private RecyclerView recyclerView;

        LoadDataTask(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        protected List<Questionnaire> doInBackground(Void... voids) {
            return questionnaireDao.getAllQuestionnaires();
        }

        @Override
        protected void onPostExecute(List<Questionnaire> list) {
            recyclerView.setAdapter(new TestListAdapter(list, questionnaire -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new QuestionnaireFragment(questionnaire))
                        .addToBackStack(null)
                        .commit();
            }));
        }
    }
}
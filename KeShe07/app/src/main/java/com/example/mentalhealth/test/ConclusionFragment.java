package com.example.mentalhealth.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.mentalhealth.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConclusionFragment extends Fragment {
    private final String conclusion;

    public ConclusionFragment(String conclusion) {
        this.conclusion = conclusion;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conclusion, container, false);
        ((TextView) view.findViewById(R.id.textViewConclusion)).setText(conclusion);
        return view;
    }
}

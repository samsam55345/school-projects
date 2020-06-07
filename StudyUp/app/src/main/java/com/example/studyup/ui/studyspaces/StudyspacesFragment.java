package com.example.studyup.ui.studyspaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.studyup.R;

public class StudyspacesFragment extends Fragment {

    private StudyspacesViewModel studyspacesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studyspacesViewModel =
                ViewModelProviders.of(this).get(StudyspacesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_studyspaces, container, false);
        final TextView textView = root.findViewById(R.id.text_studyspaces);
        studyspacesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
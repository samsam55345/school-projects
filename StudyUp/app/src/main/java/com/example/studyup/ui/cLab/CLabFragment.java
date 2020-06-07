package com.example.studyup.ui.cLab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyup.R;
import com.example.studyup.Space;

import java.util.ArrayList;
import java.util.List;

public class CLabFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.layout_space, container, false);
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) root.findViewById(R.id.clab_recycle);
        //creating recyclerview adapter
        List<Space> spaceList;
        spaceList = new ArrayList<>();


        return root;

    }
}

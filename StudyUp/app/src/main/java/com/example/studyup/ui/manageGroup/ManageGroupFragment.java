package com.example.studyup.ui.manageGroup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyup.Group;
import com.example.studyup.MainActivity;
import com.example.studyup.ManageGroupAdapter;
import com.example.studyup.R;

import java.util.ArrayList;
import java.util.List;

public class ManageGroupFragment extends Fragment {
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        View root = inflater.inflate(R.layout.fragment_manage_group, container, false);
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) root.findViewById(R.id.manage_group_admin_recycler_view);
        //creating recyclerview adapter
        List<Group> groupList;
        groupList =  MainActivity.getGroupsList();
        System.out.println(groupList.size());
        for (int i = 0; i<groupList.size(); i++) {
            groupList.get(i).setImage(R.drawable.avatar2);


        }


        //setting adapter to recyclerview
        ManageGroupAdapter adapter = new ManageGroupAdapter((AppCompatActivity)getContext(),groupList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(new ManageGroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onButtonClick(int position) {


            }
        });




        return root;
    }



}
package com.example.studyup.ui.friendViewer;
import com.example.studyup.ui.friendViewer.friendFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studyup.MainActivity;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyup.ui.friendViewer.ProductAdapter;


import com.example.studyup.R;

import java.util.ArrayList;
import java.util.List;


public class friendFragment extends Fragment {
    // private Toolbar toolbar;
    private friendViewModel friendViewModel;
    public Friends Friends;
    private TextView mTextView;

    public static class Friends {
        private int id;
        private String title;
        private String shortdesc;
        //private double rating;
        //private int image;

        public Friends(int id, String title, String shortdesc) {
            this.id = id;
            this.title = title;
            this.shortdesc = shortdesc;
            //this.rating = rating;
            //this.image = image;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getShortdesc() {
            return shortdesc;
        }


    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        toolbar = findViewById(R.id.mToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Friends");
//        RecyclerView recyclerView;
//

        friendViewModel =
                ViewModelProviders.of(this).get(friendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friends, container, false);
//         final TextView textView = root.findViewById(R.id.text_friend);
//         friendViewModel.getText().observe(this, new Observer<String>() {
//             @Override
//             public void onChanged(@Nullable String s) {
//                 textView.setText(s);
//             }
//         });

        List<friendFragment.Friends> productList;
        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(
                new friendFragment.Friends(
                        1,
                        "Johnny Appleseed",
                        "was a TA for CSCI 4061, looking for help with CSCI 5115"
                        //4.3
                        //600,
                        //R.drawable.macbook
                ));

        productList.add(
                new friendFragment.Friends(
                        1,
                        "Jane Doe",
                        "needs help with java, eager to learn and learns easily"
                        //4.3
                ));

        productList.add(
                new friendFragment.Friends(
                        1,
                        "Ben Franklin",
                        "Hard worker, is proficient in java, likes to help others"
                        //4.3
                        //600,
                        //R.drawable.surface
                ));

        //creating recyclerview adapter
        //ProductAdapter adapter = new ProductAdapter(this, productList);

        ProductAdapter adapter = new ProductAdapter(getActivity(),productList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView recyclerView;
        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




        return root;
    }
}

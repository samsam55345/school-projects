package com.example.studyup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageFriendDialogFragment extends DialogFragment  {
    ManageFriendAdapter adapter;
    List<Friend> friendList;
    //initializing the friendList
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        friendList = new ArrayList<>();



        //adding some items to our list
        friendList.add(
                new Friend(
                        1,
                        "Johnny Appleseed",
                        "was a TA for CSCI 4061, looking for help with CSCI 5115",
                        R.drawable.avatar2
                ));

        friendList.add(
                new Friend(
                        2,
                        "Jane Doe",
                        "needs help with java, eager to learn and learns easily",
                        R.drawable.avatar2
                ));

        friendList.add(
                new Friend(
                        3,
                        "Ben Franklin",
                        "Hard worker, is proficient in java, likes to help others",
                        R.drawable.avatar2
                ));

        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.select_friends, container, false);
        Button cancel = v.findViewById(R.id.button_cancel_friends);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

       Button submit = (Button) v.findViewById(R.id.button_select_friends);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean [] results = adapter.getFriends_selected();
                System.out.println(results[1]);
                System.out.println(results[0]);
                Bundle b=new Bundle();
                Intent i = new Intent();


                i.putExtra("manage_friend_results",results);


                getTargetFragment().onActivityResult(getTargetRequestCode(),24,i);


                dismiss();

            }
        });

        RecyclerView recycler_view = (RecyclerView) v.findViewById(R.id.view_friends_recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setadapter
         adapter = new ManageFriendAdapter((FragmentActivity) getContext(), friendList);
        recycler_view.setAdapter(adapter);
        //get your recycler view and populate it.
        return v;
    }


}
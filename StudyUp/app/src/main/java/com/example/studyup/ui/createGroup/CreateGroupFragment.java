package com.example.studyup.ui.createGroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.studyup.ConfirmCancelCreateGroup;
import com.example.studyup.Friend;
import com.example.studyup.FriendDialogFragment;
import com.example.studyup.MainActivity;
import com.example.studyup.R;
import com.example.studyup.Group;
import com.example.studyup.ui.friendViewer.friendFragment;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupFragment extends Fragment {
    private Button cancel;
    private Button submit;
    private Button add_friend;
    private ChipGroup chips;
    private ArrayList<Friend> friendList;
    private ArrayList<Friend> addedFriends = new ArrayList<>();


    private TextView mTextView;


    private void showDialog(){
        FriendDialogFragment f = new FriendDialogFragment();
        f.setTargetFragment(this,32);
        f.show(getFragmentManager(),"friends");

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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




        View root = inflater.inflate(R.layout.fragment_create_group, container, false);

        submit = (Button)root.findViewById(R.id.button_submit);
        cancel = (Button)root.findViewById(R.id.button_cancel);
        add_friend = (Button)root.findViewById(R.id.addFriendsButton);
        chips = (ChipGroup)root.findViewById(R.id.friendsChipGroup);
        /**

        */


        final EditText groupName = ((EditText)root.findViewById(R.id.editGroupName));
        final EditText className = ((EditText)root.findViewById(R.id.editClassName));
        final EditText description = (EditText)root.findViewById(R.id.editDescription);
        final Switch s = (Switch) root.findViewById(R.id.switch1);

        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();






            }


        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(s.isChecked()) {
                        int i = MainActivity.getGroupsList().size();

                    MainActivity.getGroupsList().add(new Group(i,groupName.getText().toString(),
                            className.getText().toString(),description.getText().toString(),"private",addedFriends,true));
                    }
                    else {
                        int i = MainActivity.getGroupsList().size();

                        MainActivity.getGroupsList().add(new Group(i,groupName.getText().toString(),
                                className.getText().toString(),description.getText().toString(),"public",addedFriends,true));
                    }
                Context context = getContext();
                CharSequence text = "Group has been saved.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                ((AppCompatActivity)context).getSupportFragmentManager().popBackStack();

            }


        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmCancelCreateGroup cg = new ConfirmCancelCreateGroup();
                cg.show(((AppCompatActivity)getContext()).getSupportFragmentManager( ),"Confirm");
                //getFragmentManager().popBackStack();


            }
        });




        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==32 && resultCode ==12) {
            addedFriends = new ArrayList<>();
            chips.removeAllViews();

            Bundle bundle = data.getExtras();
            boolean [] results = bundle.getBooleanArray("results");
            for(int j = 0; j<friendList.size();j++) {
                if (results[j] == true) {
                    Chip lChip = new Chip(getContext());
                    lChip.setText(friendList.get(j).getName());
                    chips.addView(lChip, chips.getChildCount() - 1);
                    addedFriends.add(friendList.get(j));

                }
            }


        }
    }
}
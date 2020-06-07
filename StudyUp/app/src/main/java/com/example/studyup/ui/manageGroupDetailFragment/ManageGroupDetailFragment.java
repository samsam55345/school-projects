package com.example.studyup.ui.manageGroupDetailFragment;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.studyup.Friend;
import com.example.studyup.ManageFriendDialogFragment;
import com.example.studyup.Group;
import com.example.studyup.MainActivity;
import com.example.studyup.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class ManageGroupDetailFragment extends Fragment {
    Button submitButton;
    Button cancelButton;
    Button add_friend;
    EditText manageGroup;
    EditText manageClass;
    EditText manageDescription;
    EditText managePrivacy;
    Button deleteButton;
    ChipGroup chips;
    TextView tg;

    private ArrayList<Group> listGroup = MainActivity.getGroupsList();
    private ArrayList<Friend> addedFriends;
    private ArrayList<Friend> globalFriendList;


    private void showDialog(){
        System.out.println("Lol");
        ManageFriendDialogFragment f = new ManageFriendDialogFragment();
        f.setTargetFragment(this,64);
        f.show(getFragmentManager(),"manage_friends");

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        final View root = inflater.inflate(R.layout.manage_group, container, false);

        globalFriendList = new ArrayList<>();



        //adding some items to our list
        globalFriendList.add(
                new Friend(
                        1,
                        "Johnny Appleseed",
                        "was a TA for CSCI 4061, looking for help with CSCI 5115",
                        R.drawable.avatar2
                ));

        globalFriendList.add(
                new Friend(
                        2,
                        "Jane Doe",
                        "needs help with java, eager to learn and learns easily",
                        R.drawable.avatar2
                ));

        globalFriendList.add(
                new Friend(
                        3,
                        "Ben Franklin",
                        "Hard worker, is proficient in java, likes to help others",
                        R.drawable.avatar2
                ));
        addedFriends = listGroup.get(getArguments().getInt("position")).getFriends();

         submitButton= (Button)root.findViewById(R.id.submit_change_button);
         cancelButton = (Button)root.findViewById(R.id.cancel_change_button);
        add_friend = (Button)root.findViewById(R.id.addFriendsButtonDetail);

        tg = (TextView)root.findViewById(R.id.text_groupName);
        chips = (ChipGroup)root.findViewById(R.id.friendsChipGroupDetail);


         tg.setText(listGroup.get(getArguments().getInt("position")).getGroupName());
        for(int j = 0; j<addedFriends.size();j++) {

                Chip lChip = new Chip(getContext());
                lChip.setText(addedFriends.get(j).getName());
                chips.addView(lChip, chips.getChildCount() - 1);



        }
        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();






            }


        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();

                CharSequence text = "Changes Submitted";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);

                listGroup.get(getArguments().getInt("position")).setFriends(addedFriends);
                listGroup.get(getArguments().getInt("position")).setGroupName(((EditText)root.findViewById(R.id.manageGroupName)).getText().toString());
                listGroup.get(getArguments().getInt("position")).setClassName(((EditText)root.findViewById(R.id.manageClassName)).getText().toString());
                listGroup.get(getArguments().getInt("position")).setDescription(((EditText)root.findViewById(R.id.manageDescription)).getText().toString());
                Switch s =  (Switch)root.findViewById(R.id.managePrivacy);
                if(s.isChecked()) {
                    listGroup.get(getArguments().getInt("position")).setGroupType("private");
                }
                else {
                    listGroup.get(getArguments().getInt("position")).setGroupType("public");
                }
                toast.show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                ((AppCompatActivity)context).getSupportFragmentManager( ).popBackStack();
            }
        });





        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==64 && resultCode ==24) {
            addedFriends = new ArrayList<>();
            chips.removeAllViews();

            Bundle bundle = data.getExtras();
            boolean [] results = bundle.getBooleanArray("manage_friend_results");
            System.out.println("Fuckin");
            System.out.println(results[1]);
            for(int j = 0; j<globalFriendList.size();j++) {
                if (results[j] == true) {
                    Chip lChip = new Chip(getContext());
                    lChip.setText(globalFriendList.get(j).getName());
                    chips.addView(lChip, chips.getChildCount() - 1);
                    addedFriends.add(globalFriendList.get(j));


                }
            }


        }
    }

}

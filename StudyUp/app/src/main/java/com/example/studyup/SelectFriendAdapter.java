package com.example.studyup;

/**
 * Created by karanjaswani on 1/12/18.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectFriendAdapter extends RecyclerView.Adapter<SelectFriendAdapter.GroupViewHolder> {
    private boolean [] friends_selected;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }




    //this context we will use to inflate the layout
    private FragmentActivity mCtx;

    //we are storing all the products in a list
    private List<Friend> friendList;

    //getting the context and product list with constructor
    public SelectFriendAdapter(FragmentActivity mCtx, List<Friend> friendList) {
        this.mCtx = mCtx;
        this.friendList = friendList;
        this.friends_selected = new boolean[friendList.size()];
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_friend_display,parent, false);
        return new GroupViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(final GroupViewHolder holder, int position) {
        ((GroupViewHolder)holder).bindView(position);

    }



    @Override
    public int getItemCount() {
        return friendList.size();
    }


    class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView friendName, friendDescription;
        ImageView imageView;
        CheckBox c;



        public GroupViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);


            friendName = (TextView) itemView.findViewById(R.id.textViewFriendName);
            friendDescription = (TextView) itemView.findViewById(R.id.textViewFriendDescription);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewFriend);
            c = (CheckBox)itemView.findViewById(R.id.checkBox);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(c.isChecked()) {
                        Context context = itemView.getContext();
                        CharSequence text = "Checked";
                        int duration = Toast.LENGTH_SHORT;


                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        friends_selected[position] = true;
                    }
                    else {

                        Context context = itemView.getContext();
                        CharSequence text = "Unchecked";
                        int duration = Toast.LENGTH_SHORT;


                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        friends_selected[position] = false;
                    }
                }
            });




        }




        public void bindView(int position) {
            //getting the product of the specified position
            Friend friend = friendList.get(position);

            //binding the data with the viewholder views

            friendName.setText(friend.getName());
            friendDescription.setText(friend.getDescription());


            imageView.setImageDrawable(mCtx.getResources().getDrawable(friend.getImage()));

        }

        }

    public boolean[] getFriends_selected() {
        return friends_selected;
    }
}


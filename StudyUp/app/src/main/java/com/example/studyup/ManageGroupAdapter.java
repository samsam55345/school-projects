package com.example.studyup;

/**
 * Created by karanjaswani on 1/12/18.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studyup.ui.manageGroupDetailFragment.ManageGroupDetailFragment;
import android.content.Intent;

import java.util.List;

public class ManageGroupAdapter extends RecyclerView.Adapter<ManageGroupAdapter.GroupViewHolder> {
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
    private List<Group> groupList;

    //getting the context and product list with constructor
    public ManageGroupAdapter(FragmentActivity mCtx, List<Group> groupList) {
        this.mCtx = mCtx;
        this.groupList = groupList;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_group_admin,parent, false);
        return new GroupViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(final GroupViewHolder holder, int position) {
        ((GroupViewHolder)holder).bindView(position);

    }



    @Override
    public int getItemCount() {
        return groupList.size();
    }


    class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView textViewGroupName, textViewClassName, textViewGroupType;
        ImageView imageView;
        Button b;



        public GroupViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);


            textViewGroupName = (TextView) itemView.findViewById(R.id.textViewGroupName);
            textViewClassName = (TextView) itemView.findViewById(R.id.textViewClassName);
            textViewGroupType = (TextView) itemView.findViewById(R.id.textViewGroupType);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            b = (Button)itemView.findViewById(R.id.button2);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listener!=null) {
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION) {
                            System.out.println("Yas");
                            if (b.getText() == "Manage") {

                                NavController navController = Navigation.findNavController((MainActivity) view.getContext(), R.id.nav_host_fragment);
                                Bundle bundle = new Bundle();
                                bundle.putInt("position", position);
                                navController.navigate(R.id.nav_manage_details,bundle);




                            }
                            else {
                                if (b.getText()=="Leave") {
                                    ConfirmLeaveGroup cg = new ConfirmLeaveGroup();

                                    cg.show(((FragmentActivity) view.getContext()).
                                            getSupportFragmentManager(),"Confirm");
                                }

                            }
                        }
                    }
                }
            });



        }




        public void bindView(int position) {
            //getting the product of the specified position
            Group group = groupList.get(position);

            //binding the data with the viewholder views

            textViewGroupName.setText(group.getGroupName());
            textViewGroupType.setText(String.valueOf(group.getGroupType()));
            textViewClassName.setText(group.getClassName());
            if (groupList.get(position).isYou_owned()) {
                b.setText("Manage");
            }
            else {
                b.setText("Leave");
            }


            imageView.setImageDrawable(mCtx.getResources().getDrawable(group.getImage()));

        }

        }

    }


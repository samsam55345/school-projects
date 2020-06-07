package com.example.studyup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpaceAdapter extends RecyclerView.Adapter<SpaceAdapter.SpaceViewHolder> {

        //this context we will use to inflate the layout
        private Context mCtx;

        //we are storing all the products in a list
        private List<Space> spaceList;

        //getting the context and product list with constructor
        public SpaceAdapter(Context mCtx, List<Space> spaceList) {
            this.mCtx = mCtx;
            this.spaceList = spaceList;
        }

        @Override
        public SpaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_space,parent, false);
            return new SpaceViewHolder(view);
        }

    @Override
    public void onBindViewHolder(final SpaceViewHolder holder, int position) {
        ((SpaceViewHolder)holder).bindView(position);

    }


    @Override
    public int getItemCount() {
        return spaceList.size();
    }

    class SpaceViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSpaceName, textViewSpaceBuilding, textViewSpaceRoom, textViewSpaceCapacity, textViewSpaceHours, textViewSpaceAmmenities;
        ImageView imageViewSpacePhoto;

        public SpaceViewHolder(View itemView) {
            super(itemView);
            textViewSpaceName = (TextView) itemView.findViewById(R.id.textViewSpaceName);
            textViewSpaceBuilding = (TextView) itemView.findViewById(R.id.textViewSpaceBuilding);
            textViewSpaceRoom = (TextView) itemView.findViewById(R.id.textViewSpaceRoom);
            textViewSpaceCapacity = (TextView) itemView.findViewById(R.id.textViewSpaceCapacity);
            textViewSpaceAmmenities = (TextView) itemView.findViewById(R.id.textViewSpaceAmmenities);
            textViewSpaceHours = (TextView) itemView.findViewById(R.id.textViewSpaceHours);
            imageViewSpacePhoto = (ImageView) itemView.findViewById(R.id.imageViewSpacePhoto);


        }

        public void bindView(int position) {
            //getting the product of the specified position
            Space space = spaceList.get(position);

            //binding the data with the viewholder views

            textViewSpaceName.setText(space.getSpaceName());
            textViewSpaceBuilding.setText(space.getSpaceBuilding());
            textViewSpaceRoom.setText(space.getSpaceRoom());
            textViewSpaceCapacity.setText(space.getSpaceCapacity());
            textViewSpaceHours.setText(space.getSpaceHours());
            textViewSpaceAmmenities.setText(space.getSpaceAmmenities());


            imageViewSpacePhoto.setImageDrawable(mCtx.getResources().getDrawable(space.getSpacePhoto()));

        }


    }

}

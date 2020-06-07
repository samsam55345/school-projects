package com.example.studyup.ui.home;

import com.example.studyup.Events;
import com.example.studyup.Group;
import com.example.studyup.MainActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.studyup.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<Group> listGroup = MainActivity.getGroupsList();
    private ArrayList<Events> listEvents = MainActivity.getEventList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        LinearLayout linearLayout = root.findViewById(R.id.buttonLayout);
        LinearLayout textLayout = root.findViewById(R.id.upnextLayout);


        int numGroup = listGroup.size();
        int numEvent = listEvents.size();

        for(int i = 0; i < numEvent; i++){
            final TextView addText = new TextView(getContext());
            addText.setId(i);
            addText.setText(listEvents.get(i).getDesc() + " @ " + listEvents.get(i).getTime());
            addText.setWidth(textLayout.getWidth());

            textLayout.addView(addText);
        }

        for(int i = 0; i < numGroup; i++){
            final Button addButton = new Button(getContext());
            addButton.setTextSize(20);
            addButton.setText(listGroup.get(i).getGroupName() + "\n" + listGroup.get(i).getClassName());
            addButton.setId(i);
            addButton.setWidth(linearLayout.getWidth());
            addButton.setHeight(360);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    //addButton.setTextSize(40);
                }
            });

            String typeG = listGroup.get(i).getGroupType();
            Drawable img;
            if (typeG.equals("private")) {
                img = getContext().getResources().getDrawable(R.drawable.lock);
                img.setBounds(0,0,28*2,35*2);
            } else {
                img = getContext().getResources().getDrawable(R.drawable.unlock);
                img.setBounds(0,0,28*3,25*3);
            }
            Drawable img2 = getContext().getResources().getDrawable(R.drawable.avatar2);
            img2.setBounds(0,0,320,320);
            addButton.setCompoundDrawables(img2, null, img, null);

            linearLayout.addView(addButton);
        }

        return root;
    }

}
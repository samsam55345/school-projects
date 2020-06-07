package com.example.studyup.ui.calendar;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.studyup.Events;
import com.example.studyup.MainActivity;
import com.example.studyup.R;

@TargetApi(26)
public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private ArrayList<Events> listEvents = MainActivity.getEventList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        //final TextView textView = root.findViewById(R.id.text_calendar);
        /*calendarViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        LinearLayout linearLayout = root.findViewById(R.id.calScroll);

        int numEvent = listEvents.size();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        for( int i = 0; i < numEvent; i++) {
            final TextView addDate = new TextView(getContext());

            addDate.setText(formatter.format(date));
            addDate.setTypeface(null, Typeface.BOLD);
            addDate.setTextSize(50);
            addDate.setBackgroundColor(Color.LTGRAY);

            final TextView addNot = new TextView(getContext());

            addNot.setText(listEvents.get(i).getDesc()+": "+listEvents.get(i).getTime()+"@"+listEvents.get(i).getLoc());
            addNot.setTextSize(15);

            linearLayout.addView(addDate);
            linearLayout.addView(addNot);

            c.add(Calendar.DATE, 1);
            date = c.getTime();
        }

        return root;
    }
}
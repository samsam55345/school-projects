package com.example.studyup;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static ArrayList<Group> groups = new ArrayList<>();
    private static ArrayList<Events> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calendar, R.id.nav_studyspaces,
                R.id.nav_manage, R.id.nav_create, R.id.nav_find, R.id.nav_settings, R.id.nav_friend)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        groups.add(new Group(1, "App Dev","CSCI5115","private"));
        groups.add(new Group(1, "Machine Arc.","CSCI2021","private"));
        groups.add(new Group(1, "Databases","CSCI4707","public"));

        events.add(new Events(1, groups.get(0),"Study Session", "11:30am-1:00pm", "Keller Hall"));
        events.add(new Events(2, groups.get(0),"Study Session2", "1:00pm-2:00pm", "Keller Hall"));
        events.add(new Events(3, groups.get(1),"Study Session3", "11:30am-1:00pm", "Keller Hall"));
        events.add(new Events(4, groups.get(1),"Study Session4", "1:00pm-2:00pm", "Keller Hall"));
        events.add(new Events(5, groups.get(2),"Study Session5", "11:30am-1:00pm", "Keller Hall"));
        events.add(new Events(6, groups.get(2),"Study Session6", "1:00pm-2:00pm", "Keller Hall"));

        groups.get(0).setYou_owned(true);
        groups.get(1).setYou_owned(false);
        groups.get(2).setYou_owned(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static ArrayList<Group> getGroupsList() {
        return groups;
    }

    public static ArrayList<Events> getEventList() { return events; }
}

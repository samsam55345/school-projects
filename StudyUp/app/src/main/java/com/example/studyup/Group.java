package com.example.studyup;

import java.util.ArrayList;

public class Group {
    private int id;
    private String groupName;
    private String className;
    private String groupType;
    private String description;
    private int image;
    private ArrayList<Events> events;
    private ArrayList<Friend> friends;
    private boolean you_owned = true;

    public Group(int id, String groupName, String className, String groupType, int image) {
        this.id = id;
        this.groupName = groupName;
        this.className = className;
        this.groupType = groupType; // for now keeping a string type
        this.image = image;
        this.events = new ArrayList<Events>();
        this.friends = new ArrayList<Friend>();
        this.you_owned = true;
    }

    public Group(int id, String groupName, String className, String groupType) {
        this.id = id;
        this.groupName = groupName;
        this.className = className;
        this.groupType = groupType; // for now keeping a string type
        this.events = new ArrayList<Events>();
        this.friends = new ArrayList<>();
        this.you_owned = true;
    }

    public Group(int id, String groupName, String className, String description, String groupType) {
        this.id = id;
        this.groupName = groupName;
        this.className = className;
        this.description = description;
        this.groupType = groupType; // for now keeping a string type
        this.events = new ArrayList<Events>();
        this.friends = new ArrayList<>();
        this.you_owned = true;
    }
    public Group(int id, String groupName, String className, String description, String groupType, ArrayList<Friend> f, boolean owned) {
        this.id = id;
        this.groupName = groupName;
        this.className = className;
        this.description = description;
        this.groupType = groupType; // for now keeping a string type
        this.events = new ArrayList<Events>();
        this.friends = f;
        this.you_owned = owned;

    }


    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getClassName() {
        return className;
    }

    public String getGroupType() {return groupType;}

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList<Events> getEvents() {
        return events;
    }

    public void addEvent(Events e) {
        events.add(e);
    }

    public boolean isYou_owned() {
        return you_owned;
    }

    public void setYou_owned(boolean you_owned) {
        this.you_owned = you_owned;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
}

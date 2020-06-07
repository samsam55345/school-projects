package com.example.studyup;

import java.util.ArrayList;

public class Friend {
    private int id;
    private String name;
    private String description;
    private ArrayList<Group> groups;
    private int image;

    public Friend(int id, String name, ArrayList<Group>  groups) {
        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    public Friend(int id, String name, String description, int image ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

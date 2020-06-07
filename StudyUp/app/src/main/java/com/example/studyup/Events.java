package com.example.studyup;

public class Events {
    private int id;
    private Group group;
    private String desc;
    private String time;
    private String loc;

    public Events(int id, Group group, String desc, String time, String loc) {
        this.id = id;
        this.group = group;
        this.desc = desc;
        this.time = time;
        this.loc = loc;
    }

    public int getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public String getDesc() {
        return desc;
    }

    public String getTime() {
        return time;
    }

    public String getLoc() {
        return loc;
    }
}
package com.example.studyup;

import android.media.Image;

import java.util.List;

public class Space {
    private int id;
    private String spaceName;
    private String spaceBuilding;
    private String spaceRoom;
    private String spaceCapacity;
    private String spaceHours;
    private String spaceAmmenities;
    private int spacePhoto;

    public Space(int id, String spaceName, String spaceBuilding, String spaceRoom,
                    String spaceCapacity, String spaceHours, String spaceAmmenities, int spacePhoto) {
        this.id = id;
        this.spaceName = spaceName;
        this.spaceBuilding = spaceBuilding;
        this.spaceRoom = spaceRoom;
        this.spaceCapacity = spaceCapacity;
        this.spaceHours = spaceHours;
        this.spaceAmmenities = spaceAmmenities;
        this.spacePhoto = spacePhoto;
    }

    public int getId() {
        return id;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public String getSpaceBuilding() {
        return spaceBuilding;
    }

    public String getSpaceRoom() {
        return spaceRoom;
    }

    public String getSpaceCapacity() {
        return spaceCapacity;
    }

    public String getSpaceHours() {
        return spaceHours;
    }

    public String getSpaceAmmenities() {
        return spaceAmmenities;
    }

    public int getSpacePhoto() {
        return spacePhoto;
    }
}

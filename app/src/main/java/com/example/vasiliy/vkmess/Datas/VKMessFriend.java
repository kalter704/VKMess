package com.example.vasiliy.vkmess.Datas;

import android.util.StringBuilderPrinter;

/**
 * Created by vasiliy on 02.01.16.
 */
public class VKMessFriend {

    private String id;
    private String firstName;
    private String lastName;
    private String photo;
    private int online;

    public VKMessFriend() {
    }

    public VKMessFriend(String id, String firstName, String lastName, String photo, int online) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
}

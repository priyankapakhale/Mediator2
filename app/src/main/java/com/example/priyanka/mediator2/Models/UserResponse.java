package com.example.priyanka.mediator2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by priyanka on 1/24/18.
 */

public class UserResponse {

    @SerializedName("User")
    private User user;
    private final static long serialVersionUID = 5166597326015072679L;

    @SerializedName("id")
    private int id;

    public int getId() {return id;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

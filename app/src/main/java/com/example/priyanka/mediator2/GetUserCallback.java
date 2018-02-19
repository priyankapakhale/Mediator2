package com.example.priyanka.mediator2;

import com.example.priyanka.mediator2.Models.User;

/**
 * Created by priyanka on 1/23/18.
 */

public interface GetUserCallback {
    public abstract void done(User returnedUser);
}

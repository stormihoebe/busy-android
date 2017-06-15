package com.stormhoebe.busy;

import java.lang.reflect.Array;

/**
 * Created by Guest on 6/15/17.
 */

public class User {
    String name;
    String email;
    String industry;
    String[] needs;
    String[] offers;
    String image;
    String location;
    String id;

    public User(String name, String email, String industry) {
        this.name = name;
        this.email = email;
        this.industry = industry;
    }

}

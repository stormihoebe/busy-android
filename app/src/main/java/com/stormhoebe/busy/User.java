package com.stormhoebe.busy;

import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 6/15/17.
 */

@Parcel
public class User {
    String name;
    String email;
    String industry;
    List<String> needs = new ArrayList<>();
    List<String> offers = new ArrayList<>();
    String image;
    String location;
    String id;

    public User() {}

    public User(String name, String email, String industry) {
        this.name = name;
        this.email = email;
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIndustry() {
        return industry;
    }

    public String getId() {
        return id;
    }

    public List<String> getNeeds(){
        return this.needs;
    }

    public List<String> getOffers() {
        return offers;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addNeed(String need) {
        this.needs.add(need);
    }

    public void addOffer(String offer) {
        this.offers.add(offer);
    }
}

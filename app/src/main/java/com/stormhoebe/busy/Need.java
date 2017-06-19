package com.stormhoebe.busy;

import java.util.List;

/**
 * Created by Guest on 6/19/17.
 */

public class Need {
    private String need;
    private String id;
    private List<String> usersInNeed;
    private boolean isSelected;


    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getNeedId() {
        return id;
    }

    public List<String> getUsersInNeed() {
        return usersInNeed;
    }
    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

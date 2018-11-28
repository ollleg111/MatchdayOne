
package com.matchday.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("logo")
    @Expose
    private Logo logo;
    @SerializedName("title")
    @Expose
    private Title title;
    public Logo getLogo() {
        return logo;
    }
    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

}
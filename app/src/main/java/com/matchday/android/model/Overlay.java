
package com.matchday.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Overlay {

    @SerializedName("original")
    @Expose
    private Object original;
    @SerializedName("medium")
    @Expose
    private Object medium;
    @SerializedName("small")
    @Expose
    private Object small;

    public Object getOriginal() {
        return original;
    }

    public void setOriginal(Object original) {
        this.original = original;
    }

    public Object getMedium() {
        return medium;
    }

    public void setMedium(Object medium) {
        this.medium = medium;
    }

    public Object getSmall() {
        return small;
    }

    public void setSmall(Object small) {
        this.small = small;
    }
}

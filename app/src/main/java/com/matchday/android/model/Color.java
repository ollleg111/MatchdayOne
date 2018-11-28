
package com.matchday.android.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Color {

    @SerializedName("red")
    @Expose
    private String red;
    @SerializedName("green")
    @Expose
    private String green;
    @SerializedName("blue")
    @Expose
    private String blue;
    @SerializedName("alpha")
    @Expose
    private String alpha;

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getBlue() {
        return blue;
    }


    public void setBlue(String blue) {
        this.blue = blue;
    }

    public String getAlpha() {
        return alpha;
    }


    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

}

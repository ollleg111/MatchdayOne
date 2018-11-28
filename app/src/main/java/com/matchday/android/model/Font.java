
package com.matchday.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Font {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private Float size;
    @SerializedName("color")
    @Expose
    private Color color;
    @SerializedName("shadow")
    @Expose
    private Shadow shadow;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shadow getShadow() {
        return shadow;
    }

    public void setShadow(Shadow shadow) {
        this.shadow = shadow;
    }

}

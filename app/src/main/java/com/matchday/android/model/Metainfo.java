
package com.matchday.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metainfo {

    @SerializedName("areIconsShown")
    @Expose
    private Boolean areIconsShown;
    @SerializedName("team1")
    @Expose
    private Team team;
    @SerializedName("team2")
    @Expose
    private Team team2;
    @SerializedName("separator_title")
    @Expose
    private SeparatorTitle separatorTitle;
    @SerializedName("separator_line")
    @Expose
    private SeparatorLine separatorLine;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("location")
    @Expose
    private Place place;
    @SerializedName("headline")
    @Expose
    private Headline headline;
    @SerializedName("time")
    @Expose
    private Time time;

    public Boolean getAreIconsShown() {
        return areIconsShown;
    }

    public void setAreIconsShown(Boolean areIconsShown) {
        this.areIconsShown = areIconsShown;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public SeparatorTitle getSeparatorTitle() {
        return separatorTitle;
    }

    public void setSeparatorTitle(SeparatorTitle separatorTitle) {
        this.separatorTitle = separatorTitle;
    }

    public SeparatorLine getSeparatorLine() {
        return separatorLine;
    }

    public void setSeparatorLine(SeparatorLine separatorLine) {
        this.separatorLine = separatorLine;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Place getPlace() {
        return place;
    }


    public void setPlace(Place place) {
        this.place = place;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

}

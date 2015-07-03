package com.github.app.model;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;


public class Repository extends RealmObject{

    @PrimaryKey
    private long id;
    private String name;

    private Owner owner;
    private String description;
    private String htmlUrl; // https://github.com/JakeWharton/abs.io
    private int forksCount;
    private int watchersCount;
    private String homepage;


    @SerializedName("private")
    @Ignore
    private Boolean isPrivate;

    public Repository() {}

    public Repository(long id, String name, Owner owner, String description, Boolean isPrivate, String htmlUrl,
                      int forksCount, int watchersCount, String homepage) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.isPrivate = isPrivate;
        this.htmlUrl = htmlUrl;
        this.forksCount = forksCount;
        this.watchersCount = watchersCount;
        this.homepage = homepage;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}

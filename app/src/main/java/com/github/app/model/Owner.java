package com.github.app.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Owner extends RealmObject{

    @PrimaryKey
    private long id;
    private String login;
    private String avatarUrl;
    private String htmlUrl; // "https://github.com/JakeWharton"


    public Owner() {
    }

    public Owner(String login, long id, String avatarUrl, String htmlUrl) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
    }


    // get methods
    public String getLogin() {
        return login;
    }

    public long getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}

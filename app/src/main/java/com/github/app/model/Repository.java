package com.github.app.model;

import com.google.gson.annotations.SerializedName;

public class Repository {

    private Long id;
    private String name;
    private Owner owner;
    private String description;

    @SerializedName("private")
    private Boolean isPrivate;
    private String htmlUrl; // https://github.com/JakeWharton/abs.io
    private String url; // https://api.github.com/repos/JakeWharton/abs.io

    private String commitsUrl; //  "https://api.github.com/repos/JakeWharton/abs.io/commits{/sha}"
    private Integer forksCount;
    private Integer openIssuesCount;
    private Integer watchers;
    private Integer watchersCount;

    private String homepage;

    public Repository(Long id, String name, Owner owner, String description, Boolean isPrivate, String htmlUrl, String url,
                      String commitsUrl, Integer forksCount, Integer openIssuesCount, Integer watchers, Integer watchersCount, String homepage) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.isPrivate = isPrivate;
        this.htmlUrl = htmlUrl;
        this.url = url;
        this.commitsUrl = commitsUrl;
        this.forksCount = forksCount;
        this.openIssuesCount = openIssuesCount;
        this.watchers = watchers;
        this.watchersCount = watchersCount;
        this.homepage = homepage;
    }
}

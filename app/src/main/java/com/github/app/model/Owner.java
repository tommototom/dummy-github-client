package com.github.app.model;



public class Owner {

    private final String login;
    private final Long id;
    private final String avatarUrl;
    private final String gravatarId;
    private final String url; // "https://api.github.com/users/JakeWharton"
    private final String htmlUrl; // "https://github.com/JakeWharton"

    private final String subscriptionsUrl; // "https://api.github.com/users/JakeWharton/subscriptions"
    private final String reposUrl; // "https://api.github.com/users/JakeWharton/repos"
    private final String type;
    private final Boolean siteAdmin;


    public Owner(String login, Long id, String avatarUrl, String gravatarId, String url, String htmlUrl, String subscriptionsUrl,
                 String reposUrl, String type, Boolean siteAdmin) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.gravatarId = gravatarId;
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.subscriptionsUrl = subscriptionsUrl;
        this.reposUrl = reposUrl;
        this.type = type;
        this.siteAdmin = siteAdmin;
    }


    // get methods
    public String getLogin() {
        return login;
    }

    public Long getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getSubscriptionsUrl() {
        return subscriptionsUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public Boolean getSiteAdmin() {
        return siteAdmin;
    }

    public String getType() {
        return type;
    }
}

package com.github.app.util;

public interface Constants {
    String GITHUB_BASE_URL = "https://api.github.com";
    String REPOS_PER_PAGE = "20";
    String APP_PREFS = "application";
    String GITHUB_CLIENT_ID = "19b1f956723b63d0bf84";
    String GITHUB_CLIENT_SECRET = "c356414b05e8420e66ee4764f21a57f0902afbeb";
    String AUTH_REDIRECT_URI = "gh-client://oauth_success";
    String AUTH_REDIRECT_SCHEME = "gh-client";

    String GITHUB_AUTH_URL = "https://github.com/login/oauth/authorize";
    String ACCESS_TOKEN_KEY = "access_token"; //used in url parsing
}

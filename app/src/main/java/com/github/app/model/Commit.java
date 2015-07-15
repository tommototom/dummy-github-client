package com.github.app.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.Date;

public class Commit extends RealmObject{

    @PrimaryKey
    private String sha;
    private String author;
    private String message;
    private long date;
    private String htmlUrl; // site link
    private int pageNum;

    public Commit() {
    }

    public Commit(String sha, String author, String htmlUrl, String message, Long date) {
        this.sha = sha;
        this.author = author;
        this.htmlUrl = htmlUrl;
        this.message = message;
        if (date == null) date = new Date().getTime();
        this.date = date;
    }


    public static class Builder {
        private String sha;
        private String htmlUrl; // site link
        private String author;
        private String message;
        private long date;

        private Builder() {
        }

        public static Builder aCommit() {
            return new Builder();
        }

        public Builder withSha(String sha) {
            this.sha = sha;
            return this;
        }

        public Builder withHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
            return this;
        }

        public Builder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withDate(Long date) {
            if (date == null) date = new Date().getTime();
            this.date = date;
            return this;
        }

        public Builder but() {
            return aCommit().withSha(sha).withHtmlUrl(htmlUrl).withAuthor(author).withMessage(message).withDate(date);
        }

        public Commit build() {
            Commit commit = new Commit(sha, htmlUrl, author, message, date);
            return commit;
        }
    }


    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}

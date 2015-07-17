package com.github.app.model;

import com.github.app.util.Utils;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.app.util.Utils.*;

public class Commit extends RealmObject implements CommitAdapterItem {

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

    /**
     * returns the adapter representation
     * @param commits
     * @return
     */
    public static List<CommitAdapterItem> toAdapterItems(List<Commit> commits) {
        List<CommitAdapterItem> items = new ArrayList<>();
        items.add(new CommitsTitle(days(commits.get(0).getDate())));

        for (int i = 1, size = commits.size(); i < size; i++) {
            Commit commit = commits.get(i);
            if (days(commit.getDate()) != days(commits.get(i - 1).getDate())) {
                items.add(CommitsTitle.fromCommit(commit));
            }
            items.add(commit);
        }

        return items;
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
            Commit commit = new Commit(sha, author, htmlUrl, message, date);
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

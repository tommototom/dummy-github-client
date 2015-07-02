package com.github.app.model;

public class Commit {

    private String sha;
    private String htmlUrl; // site link
    private String author;
    private String message;
    private Long date;

    public Commit(String sha, String author, String htmlUrl, String message, Long date) {
        this.sha = sha;
        this.author = author;
        this.htmlUrl = htmlUrl;
        this.message = message;
        this.date = date;
    }


    public static class Builder {
        private String sha;
        private String htmlUrl; // site link
        private String author;
        private String message;
        private Long date;

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
}

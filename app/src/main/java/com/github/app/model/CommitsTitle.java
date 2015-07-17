package com.github.app.model;

import static com.github.app.util.Utils.days;

public class CommitsTitle implements CommitAdapterItem{
    private Long dateTitle;

    public CommitsTitle(Long dateTitle) {
        this.dateTitle = dateTitle;
    }

    public static CommitsTitle fromCommit(Commit commit) {
        return new CommitsTitle(days(commit.getDate()));
    }

    public Long getDateTitle() {
        return dateTitle;
    }

    public void setDateTitle(Long dateTitle) {
        this.dateTitle = dateTitle;
    }
}

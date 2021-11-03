package com.minimaldev.android.instareels;

import androidx.annotation.NonNull;

import java.util.Date;

public class Comments {
    String profileName;
    String comment;
    Date createTime;
    boolean isLiked;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @NonNull
    @Override
    public String toString() {
        return "Comments{" +
                "profileName='" + profileName + '\'' +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                ", isLiked=" + isLiked +
                '}';
    }
}

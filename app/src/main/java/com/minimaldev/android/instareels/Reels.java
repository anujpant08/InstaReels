package com.minimaldev.android.instareels;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

public class Reels {
    String profileName;
    String postDescription;
    String audioTrackArtist;
    String audioTrackName;
    Uri reelsVideoUri;
    List<Comments> commentsList;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getAudioTrackArtist() {
        return audioTrackArtist;
    }

    public void setAudioTrackArtist(String audioTrackArtist) {
        this.audioTrackArtist = audioTrackArtist;
    }

    public String getAudioTrackName() {
        return audioTrackName;
    }

    public void setAudioTrackName(String audioTrackName) {
        this.audioTrackName = audioTrackName;
    }

    public Uri getReelsVideoUri() {
        return reelsVideoUri;
    }

    public void setReelsVideoUri(Uri reelsVideoUri) {
        this.reelsVideoUri = reelsVideoUri;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Reels{" +
                "profileName='" + profileName + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", audioTrackArtist='" + audioTrackArtist + '\'' +
                ", audioTrackName='" + audioTrackName + '\'' +
                ", reelsVideoUri=" + reelsVideoUri +
                '}';
    }
}

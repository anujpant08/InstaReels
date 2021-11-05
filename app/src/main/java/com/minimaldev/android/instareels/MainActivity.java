package com.minimaldev.android.instareels;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Reels> reelsList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createReelsList();
        RecyclerView reelsRecyclerView = findViewById(R.id.reels_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        SnapHelper snapHelper = new PagerSnapHelper();
        reelsRecyclerView.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(reelsRecyclerView);
        ReelsRecyclerViewAdapter reelsRecyclerViewAdapter = new ReelsRecyclerViewAdapter(reelsList, this);
        reelsRecyclerView.setAdapter(reelsRecyclerViewAdapter);
        //reelsRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void createReelsList(){
        Reels reels1 = new Reels();
        reels1.setProfileName("instagram_first_user");
        reels1.setPostDescription("New nike shoes - remade! Follow for more");
        reels1.setAudioTrackName("Original audio");
        String path = "android.resource://" + getPackageName() + "/";
        reels1.setReelsVideoUri(Uri.parse(path + R.raw.nike));
        reels1.setAudioTrackArtist(reels1.getProfileName());
        reels1.setCommentsList(createCommentsList());
        reelsList.add(reels1);
        Reels reels2 = new Reels();
        reels2.setProfileName("instagram_first_user");
        reels2.setPostDescription("Spotify reimagined! Follow for more");
        reels2.setAudioTrackName("Original audio");
        reels2.setReelsVideoUri(Uri.parse(path + R.raw.spotify));
        reels2.setAudioTrackArtist(reels2.getProfileName());
        reels2.setCommentsList(createCommentsList());
        reelsList.add(reels2);
        Reels reels3 = new Reels();
        reels3.setProfileName("instagram_first_user");
        reels3.setPostDescription("For Call of Duty fans! Follow for more");
        reels3.setAudioTrackName("Original audio");
        reels3.setReelsVideoUri(Uri.parse(path + R.raw.cod));
        reels3.setAudioTrackArtist(reels3.getProfileName());
        reels3.setCommentsList(createCommentsList());
        reelsList.add(reels3);
        Reels reels4 = new Reels();
        reels4.setProfileName("instagram_first_user");
        reels4.setPostDescription("I have created this logo using iPad. Hope you guys love it! ⭐️");
        reels4.setAudioTrackName("Original audio");
        reels4.setReelsVideoUri(Uri.parse(path + R.raw.starbucks));
        reels4.setAudioTrackArtist(reels4.getProfileName());
        reels4.setCommentsList(createCommentsList());
        reelsList.add(reels4);
        Reels reels5 = new Reels();
        reels5.setProfileName("instagram_first_user");
        reels5.setPostDescription("Twitch logo design. Follow for more");
        reels5.setAudioTrackName("Original audio");
        reels5.setReelsVideoUri(Uri.parse(path + R.raw.twitch));
        reels5.setAudioTrackArtist(reels5.getProfileName());
        reels5.setCommentsList(createCommentsList());
        reelsList.add(reels5);
    }
    private List<Comments> createCommentsList(){
        List<Comments> commentsList = new LinkedList<>();
        Comments comment1 = new Comments();
        comment1.setProfileName("hello_world");
        comment1.setComment("This is amazing work. 😀😀");
        addReplies(new LinkedList<Comments>(), comment1);
        commentsList.add(comment1);
        Comments comment2 = new Comments();
        comment2.setProfileName("__football_player");
        comment2.setComment("congrats, love it!");
        addReplies(new LinkedList<Comments>(), comment2);
        commentsList.add(comment2);
        Comments comment3 = new Comments();
        comment3.setProfileName("sepsong12");
        comment3.setComment("All the best. Big fan of your work :)");
        addReplies(new LinkedList<Comments>(), comment3);
        commentsList.add(comment3);
        return commentsList;
    }

    private void addReplies(List<Comments> repliesList, Comments comment) {
        Comments reply1 = new Comments();
        reply1.setProfileName("reply_comment");
        reply1.setComment("Yes! amazing.");
        repliesList.add(reply1);
        Comments reply2 = new Comments();
        reply2.setProfileName("reply_comment2");
        reply2.setComment("Trueeeee.");
        repliesList.add(reply2);
        comment.setReplies(repliesList);
    }
}
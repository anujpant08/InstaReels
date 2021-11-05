package com.minimaldev.android.instareels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.w3c.dom.Comment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static boolean musicOn = true;
    public static float playerCurrentVolume = 0f;
    private boolean isFilled = false;
    private List<Comments> commentsList = new LinkedList<>();
    private SimpleExoPlayer player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView profileImageView = findViewById(R.id.profile_image);
        ImageView audioTrackImageView = findViewById(R.id.audio);
        ImageView muteUnmuteImageView = findViewById(R.id.mute_unmute);
        LinearLayout darkGradientLayout = findViewById(R.id.dark_gradient);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1500);
        Glide.with(this)
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(profileImageView);
        Glide.with(this)
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(32,32)
                .into(audioTrackImageView);

        StyledPlayerView styledPlayerView = findViewById(R.id.video_player);
        player = new SimpleExoPlayer.Builder(this).build();
        Objects.requireNonNull(styledPlayerView.getVideoSurfaceView()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicOn){
                    playerCurrentVolume = player.getVolume();
                    muteUnmuteImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_music_off_24));
                    player.setVolume(0f);
                    musicOn = false;
                }else{
                    muteUnmuteImageView.setImageDrawable(getResources().getDrawable(R.drawable.music_on));
                    player.setVolume(playerCurrentVolume);
                    musicOn = true;
                }
                muteUnmuteImageView.setVisibility(View.VISIBLE);
                darkGradientLayout.setVisibility(View.VISIBLE);
                darkGradientLayout.startAnimation(alphaAnimation);
                muteUnmuteImageView.startAnimation(alphaAnimation);
            }
        });
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                muteUnmuteImageView.setVisibility(View.GONE);
                darkGradientLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        styledPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        styledPlayerView.hideController();
        styledPlayerView.setPlayer(player);
        player.setMediaItem(createMediaItem());
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        player.prepare();
        player.play();
        ImageView likeButton = findViewById(R.id.like);
        Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(!isFilled){
                            likeButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_favorite_24));
                            isFilled = true;
                        }else{
                            likeButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_favorite_border_24));
                            isFilled = false;
                        }
                        view.startAnimation(scaleUp);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(scaleDown);
            }
        });
        View includedCommentsLayout = findViewById(R.id.included_layout_comments);
        RecyclerView commentsRecyclerView = includedCommentsLayout.findViewById(R.id.comments_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentsRecyclerView.setLayoutManager(layoutManager);
        createDummyComments();
        CommentsArrayAdapter commentsArrayAdapter = new CommentsArrayAdapter(this, commentsList);
        commentsRecyclerView.setAdapter(commentsArrayAdapter);
        commentsArrayAdapter.notifyDataSetChanged();
        ImageView comment = findViewById(R.id.comment);
        RelativeLayout commentsRelativeLayout = findViewById(R.id.relative_layout_comments);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsRelativeLayout.startAnimation(slideUp);
                commentsRelativeLayout.setVisibility(View.VISIBLE);
            }
        });
        ImageView backComments = includedCommentsLayout.findViewById(R.id.back_exit_comments);
        backComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsRelativeLayout.startAnimation(slideDown);
            }
        });
        ImageView profileComments = includedCommentsLayout.findViewById(R.id.profile_image_comments);
        ImageView profileNewComment = includedCommentsLayout.findViewById(R.id.profile_image_new_comments);
        Glide.with(this)
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(profileComments);
        Glide.with(this)
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(profileNewComment);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(commentsRelativeLayout.getVisibility() == View.VISIBLE){
                    commentsRelativeLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        EditText newCommentText = includedCommentsLayout.findViewById(R.id.new_comment_text);
        TextView postComment = includedCommentsLayout.findViewById(R.id.post_comment);
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comments newComments = new Comments();
                newComments.setProfileName("new_profile");
                newComments.setComment(newCommentText.getText().toString());
                newComments.setCreateTime(new Date());
                newComments.setLiked(false);
                commentsList.add(newComments);
                commentsArrayAdapter.notifyDataSetChanged();
                newCommentText.setText("");
            }
        });
    }

    private MediaItem createMediaItem(){
        String path = "android.resource://" + getPackageName() + "/" +  R.raw.reels;
        return MediaItem.fromUri(Uri.parse(path));
    }
    private void createDummyComments(){
        Comments comment1 = new Comments();
        comment1.setProfileName("hello_world");
        comment1.setComment("This is amazing work. 😀😀");
        commentsList.add(comment1);
        Comments comment2 = new Comments();
        comment2.setProfileName("__football_player");
        comment2.setComment("congrats, love it!");
        commentsList.add(comment2);
        Comments comment3 = new Comments();
        comment3.setProfileName("sepsong12");
        comment3.setComment("All the best. Big fan of your work :)");
        commentsList.add(comment3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
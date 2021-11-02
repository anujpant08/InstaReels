package com.minimaldev.android.instareels;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static boolean musicOn = true;
    public static float playerCurrentVolume = 0f;
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
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
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
    }

    private MediaItem createMediaItem(){
        String path = "android.resource://" + getPackageName() + "/" +  R.raw.reels;
        return MediaItem.fromUri(Uri.parse(path));
    }
}
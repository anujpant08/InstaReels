package com.minimaldev.android.instareels;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.List;
import java.util.Objects;

public class CustomVideoPlayerRecyclerView extends RecyclerView {
    private final String TAG = "CustomRecyclerView";
    private SimpleExoPlayer player;
    private static List<Reels> reelsList;
    public static boolean musicOn = true;
    public static float playerCurrentVolume = 0f;
    private static int currentPosition = 0;
    private static int scrollDownFirstVisiblePosition = -1;
    private Context context;
    private boolean isPlaying = false;
    private final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);;
    ReelsRecyclerViewAdapter.ReelsViewHolder holder;

    public void setReelsList(List<Reels> reelsList) {
        CustomVideoPlayerRecyclerView.reelsList = reelsList;
        ((LinearLayoutManager) Objects.requireNonNull(getLayoutManager())).smoothScrollToPosition(this, null ,0);
    }

    public CustomVideoPlayerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        this.context = context;
        //Setting up player
        player = new SimpleExoPlayer.Builder(getContext()).build();
        //Adding manual listeners for recyclerView
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (!isPlaying) {
                        if(holder.thumbnail.getVisibility() == GONE){
                            holder.thumbnail.setVisibility(VISIBLE);
                        }
                        playVideo();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!isPlaying && currentPosition == 0){
                    Log.e(TAG, "onScrolled method invoked for position: " + currentPosition);
                    playVideo();
                }
            }
        });
        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                //stop playing video
                pauseVideo();
            }
        });
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                holder.muteUnmuteImageView.setVisibility(View.GONE);
                holder.darkGradientLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnimation.setDuration(1500);
    }
    private void playVideo(){
        isPlaying = true;
        //Getting currentPosition and last visible position of recyclerView items.
        currentPosition = ((LinearLayoutManager) Objects.requireNonNull(getLayoutManager())).findFirstVisibleItemPosition();
        int scrollUpLastVisiblePosition = ((LinearLayoutManager) Objects.requireNonNull(getLayoutManager())).findLastVisibleItemPosition();
        Log.e(TAG, "current position: " + currentPosition + " previous position: " + scrollDownFirstVisiblePosition);
        holder = (ReelsRecyclerViewAdapter.ReelsViewHolder) findViewHolderForAdapterPosition(currentPosition);
        if(currentPosition == scrollDownFirstVisiblePosition || scrollDownFirstVisiblePosition == scrollUpLastVisiblePosition){
            //Not doing anything, 1st condition --> Either you tried to scroll further down, but scrolled back up to current view.
            //2nd condition --> Or if not the above, then it means you are navigating from above and tried to scroll back up, but ended up back in current view.
            assert holder != null;
            if(holder.thumbnail.getVisibility() == VISIBLE){
                holder.thumbnail.setVisibility(GONE);
            }
            Log.e(TAG, "Not playing anything.");
            return;
        }
        if(holder != null){
            //Setting up player and playerView
            if(player != null){
                player.release();
            }
            player = new SimpleExoPlayer.Builder(getContext()).build();
            StyledPlayerView styledPlayerView = holder.styledPlayerView;
            styledPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            styledPlayerView.hideController();
            styledPlayerView.setPlayer(player);
            player.setMediaItem(MediaItem.fromUri(reelsList.get(currentPosition).getReelsVideoUri()));
            player.setRepeatMode(Player.REPEAT_MODE_ALL);
            player.prepare();
            player.setPlayWhenReady(true);
            player.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    Log.e(TAG, "state changed to: " + playbackState);
                    switch (playbackState) {
                        case Player.STATE_READY:
                            holder.thumbnail.setVisibility(GONE);
                            break;
                        case Player.STATE_ENDED:
                            player.seekTo(0);
                            break;
                        case Player.STATE_BUFFERING:
                            if(holder.thumbnail.getVisibility() == GONE){
                                holder.thumbnail.setVisibility(VISIBLE);
                            }
                            Toast.makeText(getContext(), "Buffering...", Toast.LENGTH_SHORT).show();
                            break;
                        case Player.STATE_IDLE:
                        default:
                            break;
                    }
                }
            });
            Objects.requireNonNull(holder.styledPlayerView.getVideoSurfaceView()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (musicOn) {
                        Log.e(TAG, "Inside custom recyclerView -- Music on");
                        playerCurrentVolume = player.getVolume();
                        player.setVolume(0f);
                        holder.muteUnmuteImageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_round_music_off_24));
                        musicOn = false;
                    } else {
                        Log.e(TAG, "Inside custom recyclerView -- Music Off");
                        player.setVolume(playerCurrentVolume);
                        holder.muteUnmuteImageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.music_on));
                        musicOn = true;
                    }
                    holder.muteUnmuteImageView.setVisibility(View.VISIBLE);
                    holder.darkGradientLayout.setVisibility(View.VISIBLE);
                    holder.darkGradientLayout.startAnimation(alphaAnimation);
                    holder.muteUnmuteImageView.startAnimation(alphaAnimation);
                }
            });
        }
        scrollDownFirstVisiblePosition = currentPosition;
    }
    private void pauseVideo(){
        isPlaying = false;
        if(player.isPlaying()){
            player.setPlayWhenReady(true);
        }
    }
    public void release(){
        isPlaying = false;
        if(player != null){
            player.release();
        }
    }
}

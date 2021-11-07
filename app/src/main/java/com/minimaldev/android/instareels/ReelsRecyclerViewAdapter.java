package com.minimaldev.android.instareels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ReelsRecyclerViewAdapter extends RecyclerView.Adapter<ReelsRecyclerViewAdapter.ReelsViewHolder>{
    String TAG = "ReelsRecyclerViewAdapter";
    List<Reels> reelsList;
    Context context;
    public static boolean musicOn = true;
    public static float playerCurrentVolume = 0f;
    private boolean isFilled = false;
    BottomSheetFragmentComments bottomSheetFragmentComments = BottomSheetFragmentComments.newInstance();
    public ReelsRecyclerViewAdapter(List<Reels> reelsList, Context context) {
        this.reelsList = reelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReelsRecyclerViewAdapter.ReelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reels_custom_view, parent, false);
        return new ReelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReelsRecyclerViewAdapter.ReelsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        Animation scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        alphaAnimation.setDuration(1500);
        Glide.with(context)
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(holder.profileImageView);
        Glide.with(context)
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(32,32)
                .into(holder.audioTrackImageView);
        Objects.requireNonNull(holder.styledPlayerView.getVideoSurfaceView()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicOn){
                    playerCurrentVolume = holder.player.getVolume();
                    holder.muteUnmuteImageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_round_music_off_24));
                    holder.player.setVolume(0f);
                    musicOn = false;
                }else{
                    holder.muteUnmuteImageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.music_on));
                    holder.player.setVolume(playerCurrentVolume);
                    musicOn = true;
                }
                holder.muteUnmuteImageView.setVisibility(View.VISIBLE);
                holder.darkGradientLayout.setVisibility(View.VISIBLE);
                holder.darkGradientLayout.startAnimation(alphaAnimation);
                holder.muteUnmuteImageView.startAnimation(alphaAnimation);
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
        holder.postDescription.setText(reelsList.get(position).getPostDescription());
        holder.musicDescription.setText(reelsList.get(position).getAudioTrackArtist() + " . " + reelsList.get(position).getAudioTrackName());
        holder.styledPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        holder.styledPlayerView.hideController();
        holder.styledPlayerView.setPlayer(holder.player);
        holder.player.setMediaItem(MediaItem.fromUri(reelsList.get(holder.getBindingAdapterPosition()).getReelsVideoUri()));
        holder.player.setRepeatMode(Player.REPEAT_MODE_ALL);
        holder.player.prepare();
        if(!holder.player.isPlaying()){
            holder.player.play();
        }
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(!isFilled){
                            holder.likeButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_round_favorite_24));
                            isFilled = true;
                        }else{
                            holder.likeButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_round_favorite_border_24));
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
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetFragmentComments.setCommentData(context, reelsList.get(holder.getBindingAdapterPosition()).getCommentsList(), reelsList.get(holder.getBindingAdapterPosition()));
                bottomSheetFragmentComments.show(((AppCompatActivity)context).getSupportFragmentManager(), "BottomSheetFragment");
            }
        });
        holder.sendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetSendPostFragment bottomSheetSendPostFragment = new BottomSheetSendPostFragment();
                bottomSheetSendPostFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "BottomSheetSendPostFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return reelsList.size();
    }
    public class ReelsViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImageView;
        ImageView audioTrackImageView;
        ImageView muteUnmuteImageView;
        LinearLayout darkGradientLayout;
        StyledPlayerView styledPlayerView;
        SimpleExoPlayer player;
        ImageView likeButton;
        ImageView comment;
        TextView postCommentDescription;
        TextView profileNameCommentDescription;
        TextView postDescription;
        TextView musicDescription;
        ImageView sendPost;
        public ReelsViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profile_image);
            audioTrackImageView = itemView.findViewById(R.id.audio);
            muteUnmuteImageView = itemView.findViewById(R.id.mute_unmute);
            darkGradientLayout = itemView.findViewById(R.id.dark_gradient);
            styledPlayerView = itemView.findViewById(R.id.video_player);
            likeButton = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            sendPost = itemView.findViewById(R.id.send);
            postCommentDescription = itemView.findViewById(R.id.post_description_comments);
            profileNameCommentDescription = itemView.findViewById(R.id.profile_name_comments);
            postDescription = itemView.findViewById(R.id.post_description);
            musicDescription = itemView.findViewById(R.id.music_description);
            player = new SimpleExoPlayer.Builder(context).build();
        }
    }
}

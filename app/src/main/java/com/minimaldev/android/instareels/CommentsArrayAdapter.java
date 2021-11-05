package com.minimaldev.android.instareels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.LinkedList;
import java.util.List;

public class CommentsArrayAdapter extends RecyclerView.Adapter<CommentsArrayAdapter.CommentsViewHolder>{
    private List<Comments> commentsList = new LinkedList<>();
    private final Context context;
    public CommentsArrayAdapter(Context context, List<Comments> commentsList) {
        this.context = context;
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentsArrayAdapter.CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_custom_view, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsArrayAdapter.CommentsViewHolder holder, int position) {
        Glide.with(context)
                .load(R.drawable.profile_image_commenter)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(holder.profileImageViewCommenter);
        String profileName = commentsList.get(position).getProfileName();
        String comment = commentsList.get(position).getComment();
        holder.profileName.setText(profileName);
        holder.commentText.setText(comment);
    }

    @Override
    public int getItemCount() {
        if(this.commentsList != null){
            return this.commentsList.size();
        }
        return 0;
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImageViewCommenter;
        ImageView likeButtonImageView;
        TextView profileName;
        TextView commentText;
        Animation scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        private boolean isFilled = false;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageViewCommenter = itemView.findViewById(R.id.profile_commenter);
            likeButtonImageView = itemView.findViewById(R.id.comment_like_dislike);
            profileName = itemView.findViewById(R.id.profile_name_commenter);
            commentText = itemView.findViewById(R.id.comment_text);
            likeButtonImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scaleDown.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if(!isFilled){
                                likeButtonImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_round_favorite_24));
                                isFilled = true;
                            }else{
                                likeButtonImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_round_favorite_border_24_black));
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
        }
    }
    public void updateCommentsList(List<Comments> commentsList){
        this.commentsList = commentsList;
        notifyDataSetChanged();
    }
}

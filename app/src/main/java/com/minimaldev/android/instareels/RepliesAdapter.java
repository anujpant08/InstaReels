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

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.RepliesViewHolder>{
    private List<Comments> repliesList = new LinkedList<>();
    private final Context context;
    public RepliesAdapter(Context context, List<Comments> repliesList) {
        this.context = context;
        this.repliesList = repliesList;
    }

    @NonNull
    @Override
    public RepliesAdapter.RepliesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replies_custom_view, parent, false);
        return new RepliesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepliesAdapter.RepliesViewHolder holder, int position) {
        Glide.with(context)
                .load(R.drawable.profile_image_commenter)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(30,30)
                .into(holder.profileImageViewCommenter);
        String profileName = repliesList.get(position).getProfileName();
        String comment = repliesList.get(position).getComment();
        holder.profileName.setText(profileName);
        holder.commentText.setText(comment);

    }

    @Override
    public int getItemCount() {
        if(this.repliesList != null){
            return this.repliesList.size();
        }
        return 0;
    }

    public class RepliesViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImageViewCommenter;
        ImageView likeButtonImageView;
        TextView profileName;
        TextView commentText;
        Animation scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        private boolean isFilled = false;
        public RepliesViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageViewCommenter = itemView.findViewById(R.id.profile_commenter_replies);
            likeButtonImageView = itemView.findViewById(R.id.comment_like_dislike_replies);
            profileName = itemView.findViewById(R.id.profile_name_commenter_replies);
            commentText = itemView.findViewById(R.id.comment_text_replies);
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
        this.repliesList = commentsList;
        notifyDataSetChanged();
    }
}

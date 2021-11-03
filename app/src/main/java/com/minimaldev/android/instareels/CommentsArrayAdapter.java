package com.minimaldev.android.instareels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return this.commentsList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImageViewCommenter;
        ImageView likeButtonImageView;
        TextView profileName;
        TextView commentText;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageViewCommenter = itemView.findViewById(R.id.profile_commenter);
            likeButtonImageView = itemView.findViewById(R.id.comment_like_dislike);
            profileName = itemView.findViewById(R.id.profile_name_commenter);
            commentText = itemView.findViewById(R.id.comment_text);
        }
    }
}

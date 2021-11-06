package com.minimaldev.android.instareels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        Comments comments = commentsList.get(holder.getBindingAdapterPosition());
        String profileName = comments.getProfileName();
        String comment = comments.getComment();
        holder.profileName.setText(profileName);
        holder.commentText.setText(comment);
//        RecyclerView.OnItemTouchListener scrollTouchListener = new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, MotionEvent e) {
//                int action = e.getAction();
//                if (action == MotionEvent.ACTION_MOVE) {
//                    rv.getParent().requestDisallowInterceptTouchEvent(true);
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        };
        if(comments.getReplies() != null && !comments.getReplies().isEmpty()){
            holder.viewRepliesRelativeLayout.setVisibility(View.VISIBLE);
            holder.viewRepliesTextView.setText("View " + comments.getReplies().size() + " replies");
            holder.viewRepliesTextView.setVisibility(View.VISIBLE);
        }
        holder.viewRepliesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.repliesRecyclerView.getVisibility() == View.VISIBLE){
                    holder.repliesRecyclerView.setVisibility(View.GONE);
                }else{
                    holder.repliesRecyclerView.setVisibility(View.VISIBLE);
                    List<Comments> repliesList = comments.getReplies();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    holder.repliesRecyclerView.setLayoutManager(layoutManager);
                    holder.repliesAdapter = new RepliesAdapter(context, repliesList);
                    holder.repliesRecyclerView.setAdapter(holder.repliesAdapter);
                }
            }
        });
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
        RelativeLayout viewRepliesRelativeLayout;
        RecyclerView repliesRecyclerView;
        RepliesAdapter repliesAdapter;
        TextView viewRepliesTextView;
        private boolean isFilled = false;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageViewCommenter = itemView.findViewById(R.id.profile_commenter);
            likeButtonImageView = itemView.findViewById(R.id.comment_like_dislike);
            profileName = itemView.findViewById(R.id.profile_name_commenter);
            commentText = itemView.findViewById(R.id.comment_text);
            viewRepliesRelativeLayout = itemView.findViewById(R.id.replies_relative_layout);
            repliesRecyclerView = itemView.findViewById(R.id.replies_recycler_view);
            viewRepliesTextView  =itemView.findViewById(R.id.view_replies_title);
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

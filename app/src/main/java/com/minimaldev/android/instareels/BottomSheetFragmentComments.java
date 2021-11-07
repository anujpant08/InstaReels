package com.minimaldev.android.instareels;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BottomSheetFragmentComments extends BottomSheetDialogFragment {
    private List<Comments> commentsList;
    public static Reels reels;
    Context context;
    String TAG = "BottomSheetFragmentComments";

    public static BottomSheetFragmentComments newInstance(){
        return new BottomSheetFragmentComments();
    }

    public void setCommentData(Context context, List<Comments> commentsList, Reels reels){
        this.context = context;
        this.commentsList = commentsList;
        BottomSheetFragmentComments.reels = reels;
        Log.e(TAG, "comments received: " + this.commentsList);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_comments_fragment, null);
        dialog.setContentView(contentView);
        ImageView profileComments = contentView.findViewById(R.id.profile_image_comments);
        ImageView profileNewComment = contentView.findViewById(R.id.profile_image_new_comments);
        Glide.with(getContext())
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(profileComments);
        Glide.with(getContext())
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(profileNewComment);
        ImageView backExit = contentView.findViewById(R.id.back_exit_comments);
        backExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        TextView postCommentDescription = contentView.findViewById(R.id.post_description_comments);
        TextView profileNameCommentDescription = contentView.findViewById(R.id.profile_name_comments);
        profileNameCommentDescription.setText(reels.getProfileName());
        postCommentDescription.setText(reels.getPostDescription());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView commentsRecyclerView = contentView.findViewById(R.id.comments_recycler_view);
        CommentsArrayAdapter commentsArrayAdapter = new CommentsArrayAdapter(getContext(), commentsList);
        RelativeLayout commentsRelativeLayout = contentView.findViewById(R.id.new_comment_user);
        commentsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    commentsRelativeLayout.setVisibility(View.GONE);
                else if (dy < 0)
                    commentsRelativeLayout.setVisibility(View.VISIBLE);
            }
        });
        commentsRecyclerView.setAdapter(commentsArrayAdapter);
        commentsRecyclerView.setLayoutManager(layoutManager);
        TextView postComment = contentView.findViewById(R.id.post_comment);
        EditText newCommentText = contentView.findViewById(R.id.new_comment_text);
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comments newComments = new Comments();
                newComments.setProfileName("new_profile");
                newComments.setComment(newCommentText.getText().toString());
                newComments.setCreateTime(new Date());
                newComments.setLiked(false);
                List<Comments> newCommentsList = new LinkedList<>(commentsList);
                newCommentsList.add(newComments);
                reels.setCommentsList(newCommentsList);
                commentsArrayAdapter.updateCommentsList(newCommentsList);
                Log.e(TAG, "Reels currently working on: " + reels);
                Log.e(TAG, "New comments on above reels: " + newCommentsList);
                newCommentText.setText("");
            }
        });
        ImageView sendPostComments = contentView.findViewById(R.id.send_post_comments);
        sendPostComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetSendPostFragment bottomSheetSendPostFragment = new BottomSheetSendPostFragment();
                bottomSheetSendPostFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "BottomSheetSendPostFragment");
            }
        });
    }
}

package com.minimaldev.android.instareels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsListViewHolder>{
    Context context;
    List<String> friendsList;

    public FriendsListAdapter(Context context, List<String> friendsList) {
        this.context = context;
        this.friendsList = friendsList;
    }

    @NonNull
    @Override
    public FriendsListAdapter.FriendsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_user_custom_view, parent, false);
        return new FriendsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListAdapter.FriendsListViewHolder holder, int position) {
        String profileName = friendsList.get(holder.getBindingAdapterPosition());
        Glide.with(context)
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(holder.profileImage);
        holder.profileName.setText(profileName);
        holder.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sending post to " + profileName + "...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
    public class FriendsListViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView profileName;
        Button sendButton;
        public FriendsListViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.friends_profile_image);
            profileName = itemView.findViewById(R.id.profile_name_friends);
            sendButton = itemView.findViewById(R.id.send_button);
        }
    }
    public void updateList(List<String> updatedFriendsList){
        this.friendsList = updatedFriendsList;
        notifyDataSetChanged();
    }
}

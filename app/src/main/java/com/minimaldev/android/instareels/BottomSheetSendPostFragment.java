package com.minimaldev.android.instareels;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetSendPostFragment extends BottomSheetDialogFragment {
    String TAG = "BottomSheetSendPostFragment";
    RecyclerView sendPostRecyclerView;
    FriendsListAdapter friendsAdapter;
    List<String> friendsList = new ArrayList<>();

    public static BottomSheetSendPostFragment newInstance(){
        return new BottomSheetSendPostFragment();
    }
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_send_post, null);
        dialog.setContentView(contentView);
        ImageView profileImageSendPost = contentView.findViewById(R.id.profile_image_send_post);
        Glide.with(requireContext())
                .load(R.drawable.profile_pic)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .override(40,40)
                .into(profileImageSendPost);

        EditText writeMessage = contentView.findViewById(R.id.write_message);
        AutoCompleteTextView searchAutoComplete = contentView.findViewById(R.id.auto_complete_search);
        searchAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String updatedText = editable.toString();
                filterList(updatedText);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        sendPostRecyclerView = contentView.findViewById(R.id.send_post_recycler_view);
        createFriendsList();
        friendsAdapter = new FriendsListAdapter(requireContext(), friendsList);
        sendPostRecyclerView.setAdapter(friendsAdapter);
        sendPostRecyclerView.setLayoutManager(layoutManager);
    }
    private void filterList(String text){
        List<String> searchResults = new ArrayList<>();
        for(String friend : friendsList){
            if(friend.contains(text)){
                searchResults.add(friend);
            }
        }
        friendsAdapter.updateList(searchResults);
    }
    private void createFriendsList(){
        for(int friend = 1; friend <= 20; friend++){
            friendsList.add("friends_" + friend);
        }
    }
}

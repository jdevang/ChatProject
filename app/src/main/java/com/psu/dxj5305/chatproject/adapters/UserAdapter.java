package com.psu.dxj5305.chatproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.psu.dxj5305.chatproject.R;
import com.psu.dxj5305.chatproject.activities.MessageListActivity;
import com.psu.dxj5305.chatproject.models.User;

import java.util.List;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    Context context;
    List<User> userArrayList;

    public UserAdapter(Context context, List<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_recycler, parent, false);
        return new UserHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.username.setText(user.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(v.getContext(), MessageListActivity.class);
                messageIntent.putExtra("oppositeUser", user.getUsername());
                v.getContext().startActivity(messageIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class UserHolder extends RecyclerView.ViewHolder {

        TextView username;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_view);
        }
    }
}

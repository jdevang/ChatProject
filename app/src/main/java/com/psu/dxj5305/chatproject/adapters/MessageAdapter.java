package com.psu.dxj5305.chatproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.psu.dxj5305.chatproject.R;
import com.psu.dxj5305.chatproject.models.Message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    Context context;
    ArrayList<Message> messageArrayList;

    public MessageAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageHolder holder, int position) {
        Message message = messageArrayList.get(position);

        holder.message.setText(message.getMessage());
        holder.messenger.setText(message.getSentBy());

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {

        TextView message, messenger;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.messageTextView);
            messenger = itemView.findViewById(R.id.messengerTextView);
        }
    }
}

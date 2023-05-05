package com.example.urldemo.adapter;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urldemo.R;
import com.example.urldemo.dto.MyMessage;
import com.example.urldemo.holder.MessageViewHolder;
import com.example.urldemo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<MyMessage> messageList = new ArrayList<>();
    private RecyclerView recyclerView;

    public MessageAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setMessage(MyMessage message) {
        messageList.add(message);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MessageViewHolder(view);
    }

    @SuppressLint({"RtlHardcoded", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MyMessage message = messageList.get(position);
        TextView textView;
        if (message.getType().equals(MyMessage.MY)){
            textView = (TextView) holder.itemView.findViewById(R.id.my_text_view);
        }else {
            textView = (TextView) holder.itemView.findViewById(R.id.ai_text_view);
        }
        textView.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
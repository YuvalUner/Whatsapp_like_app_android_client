package com.example.advancedprojectandroidclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.Message;

import java.util.List;

/**
 * This adapter is used to display the list of messages.
 * Basically a boilerplate class, hence low on documentation.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    public Message getMessages(int position) {
        return messages.get(position);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView messageContentTv;
        private final ConstraintLayout messageLayout;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.messageContentTv = itemView.findViewById(R.id.chat_bubble_tv);
            this.messageLayout = itemView.findViewById(R.id.chat_bubble_constraint_layout);
        }
    }

    private final LayoutInflater layoutInflater;
    private List<Message> messages;

    public MessageListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.chat_bubble_left, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            holder.messageContentTv.setText(current.getContent());
            // The constarint set the message will have
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.messageLayout);
            // If the message is from the user, the message will be green and on the right
            if (current.isSent()) {
                holder.messageContentTv.setBackgroundResource(R.color.green);
                constraintSet.connect(R.id.chat_bubble_tv, ConstraintSet.END, R.id.chat_bubble_constraint_layout, ConstraintSet.END);
                constraintSet.clear(R.id.chat_bubble_tv, ConstraintSet.START);
            }
            // If the message is not from the user, the message will be grey and on the left.
            else {
                holder.messageContentTv.setBackgroundResource(R.color.grey);
                constraintSet.connect(R.id.chat_bubble_tv, ConstraintSet.START, R.id.chat_bubble_constraint_layout, ConstraintSet.START);
                constraintSet.clear(R.id.chat_bubble_tv, ConstraintSet.END);
            }
            constraintSet.applyTo(holder.messageLayout);
        }
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    /**
     * This method is used to set the list of messages.
     *
     * @param messages the list of messages to be displayed.
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
}
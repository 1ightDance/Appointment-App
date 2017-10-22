package com.example.lightdance.appointment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lightdance.appointment.Model.MessageBean;
import com.example.lightdance.appointment.R;

import java.util.List;

/**
 * Created by pope on 2017/10/21.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageBean> mMessage;

    public MessageAdapter(List<MessageBean> mMessage) {
        this.mMessage = mMessage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_message,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageBean messageBean = mMessage.get(position);
        holder.senderAvatar.setImageResource(messageBean.getSenderAvatarImgId());
        holder.senderName.setText(messageBean.getSenderName());
        holder.sendTime.setText(messageBean.getSendTime());
        holder.messageContent.setText(messageBean.getMessageContent());
    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView senderAvatar;
        TextView senderName;
        TextView sendTime;
        TextView messageContent;

        public ViewHolder(View itemView) {
            super(itemView);
            senderAvatar = (ImageView) itemView.findViewById(R.id.img_sender_avatar);
            senderName = (TextView) itemView.findViewById(R.id.tv_sender_name);
            sendTime = (TextView) itemView.findViewById(R.id.tv_send_time);
            messageContent = (TextView) itemView.findViewById(R.id.tv_message_content);
        }
    }
}

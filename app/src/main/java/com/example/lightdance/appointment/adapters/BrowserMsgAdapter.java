package com.example.lightdance.appointment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by LightDance on 2017/10/5.
 */

public class BrowserMsgAdapter
        extends RecyclerView.Adapter<BrowserMsgAdapter.ViewHolder> {

    private Context mContext;

    private List<BrowserMsgBean> BrowseMsgList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView publishTime;
        private TextView beginTime;
        private TextView endTime;
        private TextView place;
        private TextView inviter;
        private TextView personNumber;
        private ImageView type;
        private ImageView inviterIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.message_appointment_title);
            publishTime = (TextView)itemView.findViewById(R.id.message_appointment_publishtime);
            beginTime = (TextView)itemView.findViewById(R.id.message_appointment_begintime);
            endTime = (TextView)itemView.findViewById(R.id.message_appointment_endtime);
            place = (TextView)itemView.findViewById(R.id.message_appointment_place);
            inviter = (TextView)itemView.findViewById(R.id.message_appointment_inviter);
            personNumber = (TextView)itemView.findViewById(R.id.message_appointment_personnumber);
            type = (ImageView)itemView.findViewById(R.id.message_appointment_type);
            inviterIcon = (ImageView)itemView.findViewById(R.id.message_appointment_inviterIcon);
        }
    }

    public BrowserMsgAdapter(List<BrowserMsgBean> messageAppointmentList){
        this.BrowseMsgList = messageAppointmentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_appointment_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageAppointment msgAppointment = messageAppointmentList.get(position);
        holder.title.setText(msgAppointment.getTitle());
        holder.publishTime.setText(msgAppointment.getPublishTime());
        holder.beginTime.setText(msgAppointment.getBeginTime());
        holder.endTime.setText(msgAppointment.getEndTime());
        holder.inviter.setText(msgAppointment.getInviter());
        holder.personNumber.setText(msgAppointment.getPersonNumber());
        holder.place.setText(msgAppointment.getPlace());
        Glide.with(mContext).load(msgAppointment.getInviterIconId()).into(holder.inviterIcon);
        Glide.with(mContext).load(msgAppointment.getType()).into(holder.type);
    }

    @Override
    public int getItemCount() {
        return messageAppointmentList.size();
    }
}

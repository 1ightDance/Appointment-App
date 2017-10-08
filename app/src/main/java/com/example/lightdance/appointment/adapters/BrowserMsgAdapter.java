package com.example.lightdance.appointment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.R;

import java.util.List;

/**
 * Created by LightDance on 2017/10/5.
 */

public class BrowserMsgAdapter
        extends RecyclerView.Adapter<BrowserMsgAdapter.ViewHolder> {

    private Context mContext;

    //存放每一条信息的数组
    private List<BrowseMsgBean> msgBeanList;

    //内部类ViewHolder与视图进行连接
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

        //构造方法，将成员变量与界面组件一一对应
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

    public BrowserMsgAdapter(List<BrowseMsgBean> messageAppointmentList){
        this.msgBeanList = messageAppointmentList;
    }

    //重写从RecyclerView ADapter中继承来的三个方法
    //传入布局文件 并膨胀为视图暂存在holder中
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_browse_msg, parent, false);
        return new ViewHolder(view);
    }

    //数据与视图绑定
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BrowseMsgBean msgAppointment = msgBeanList.get(position);
        holder.title.setText(msgAppointment.getTitle());
        holder.publishTime.setText(msgAppointment.getPublishTime());
        holder.beginTime.setText(msgAppointment.getBeginTime());
        holder.endTime.setText(msgAppointment.getEndTime());
        holder.inviter.setText(msgAppointment.getInviter());
        holder.personNumber.setText(msgAppointment.getPersonNumber());
        holder.place.setText(msgAppointment.getPlace());
        Glide.with(mContext).load(msgAppointment.getInviterIconId()).into(holder.inviterIcon);
        Glide.with(mContext).load(msgAppointment.getTypeIconId()).into(holder.type);
    }

    //传入数据源长度
    @Override
    public int getItemCount() {
        return msgBeanList.size();
    }
}

package com.example.lightdance.appointment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author pope
 * Created by pope on 2017/11/8.
 */

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {

    private ParticipantAdapter.OnItemClickListener msgOnclickListener = null;
    private List<String> member;
    Context mContext;

    public ParticipantAdapter(Context mContext,List<String> member){
        this.member = member;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_participant,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String member = this.member.get(position);
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(member, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                Glide.with(mContext).load(userBean.getUserIconId()).into(holder.participantAvatar);
                holder.participantNickname.setText(userBean.getUserNickName());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msgOnclickListener != null) {
                    msgOnclickListener.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return member.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView participantAvatar;
        TextView participantNickname;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            participantAvatar = (ImageView) itemView.findViewById(R.id.img_participant_avatar);
            participantNickname = (TextView) itemView.findViewById(R.id.tv_participant_nickname);

        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setItemOnclickListener(OnItemClickListener msgOnclickListener) {
        this.msgOnclickListener = msgOnclickListener;
    }

}

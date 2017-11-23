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
 * Created by pope on 2017/11/8.
 */

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {

    private List<String> memberBean;
    Context mContext;

    public ParticipantAdapter(Context mContext,List<String> memberBean){
        this.memberBean = memberBean;
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
        final String memberBean = this.memberBean.get(position);
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(memberBean, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                Glide.with(mContext).load(userBean.getUserIconId()).into(holder.participantAvatar);
                holder.participantNickname.setText(userBean.getUserNickName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberBean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView participantAvatar;
        TextView participantNickname;

        public ViewHolder(View itemView) {
            super(itemView);

            participantAvatar = (ImageView) itemView.findViewById(R.id.img_participant_avatar);
            participantNickname = (TextView) itemView.findViewById(R.id.tv_participant_nickname);

        }
    }
}

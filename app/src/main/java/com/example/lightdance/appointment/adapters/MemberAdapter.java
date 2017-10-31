package com.example.lightdance.appointment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lightdance.appointment.Model.MemberBean;
import com.example.lightdance.appointment.R;

import java.util.List;

/**
 * Created by pope on 2017/10/31.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<MemberBean> memberBeanList;
    Context mContext;

    public MemberAdapter(Context mContext,List<MemberBean> memberBeanList){
        this.memberBeanList = memberBeanList;
        this.mContext = mContext;
    }

    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_members,parent,false);
        MemberAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MemberAdapter.ViewHolder holder, int position) {
        MemberBean memberBean = memberBeanList.get(position);
        holder.memberNickname.setText(memberBean.getMemberNickname());
        Glide.with(mContext).load(memberBean.getMemberAvatar()).into(holder.memberAvatar);
    }

    @Override
    public int getItemCount() {
        return memberBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView memberAvatar;
        TextView memberNickname;

        public ViewHolder(View itemView) {
            super(itemView);
            memberAvatar = (ImageView) itemView.findViewById(R.id.img_members_avatar);
            memberNickname = (TextView) itemView.findViewById(R.id.tv_members_nickname);
        }
    }
}

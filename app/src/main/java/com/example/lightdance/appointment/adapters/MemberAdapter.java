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
 * Created by pope on 2017/10/31.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<String> memberList;
    private OnItemClickListener msgOnclickListener = null;
    Context mContext;

    public MemberAdapter(Context mContext,List<String> memberList){
        this.memberList = memberList;
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
    public void onBindViewHolder(final MemberAdapter.ViewHolder holder, int position) {
        String member = memberList.get(position);
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(member, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                holder.memberNickname.setText(userBean.getUserNickName());
                Glide.with(mContext).load(userBean.getUserIconId()).into(holder.memberAvatar);
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
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView memberAvatar;
        TextView memberNickname;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            memberAvatar = (ImageView) itemView.findViewById(R.id.img_members_avatar);
            memberNickname = (TextView) itemView.findViewById(R.id.tv_members_nickname);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setItemOnclickListener(OnItemClickListener msgOnclickListener) {
        this.msgOnclickListener = msgOnclickListener;
    }

}

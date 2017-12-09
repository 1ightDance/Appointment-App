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

/**
 * @author pope
 *         Created by pope on 2017/12/9.
 */

public class NoCommentDetailAdapter extends RecyclerView.Adapter<NoCommentDetailAdapter.ViewHolder> {

    private List<UserBean> membersList;
    private Context mContext;

    public NoCommentDetailAdapter(List<UserBean> membersList, Context mContext) {
        this.membersList = membersList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comment_detail, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserBean userBean = membersList.get(position);
        holder.userNickName.setText(userBean.getUserNickName());
        Glide.with(mContext).load(userBean.getUserIconId()).into(holder.userAvatar);
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userAvatar;
        TextView userNickName;

        public ViewHolder(View itemView) {
            super(itemView);
            userAvatar = (ImageView) itemView.findViewById(R.id.imageView_user_avatar);
            userNickName = (TextView) itemView.findViewById(R.id.textView_userNickName);
        }
    }
}

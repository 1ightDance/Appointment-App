package com.example.lightdance.appointment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
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

    private SwitchChangeListener switchChangeListener = null;
    private List<UserBean> membersList;

    public NoCommentDetailAdapter(List<UserBean> membersList) {
        this.membersList = membersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comment_detail, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UserBean userBean = membersList.get(position);
        holder.userNickName.setText(userBean.getUserNickName());
        Glide.with(holder.itemView.getContext()).load(userBean.getUserIconId()).into(holder.userAvatar);
        holder.isShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchChangeListener.onCheckedChanged(holder.getAdapterPosition(), isChecked);
            }
        });
    }

    public interface SwitchChangeListener {
        void onCheckedChanged(int position, boolean isChecked);
    }

    public void setSwitchChangeListener(SwitchChangeListener switchChangeListener) {
        this.switchChangeListener = switchChangeListener;
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userAvatar;
        TextView userNickName;
        Switch isShow;

        public ViewHolder(View itemView) {
            super(itemView);
            userAvatar = (ImageView) itemView.findViewById(R.id.imageview_useravatar);
            userNickName = (TextView) itemView.findViewById(R.id.textview_usernickName);
            isShow = (Switch) itemView.findViewById(R.id.switch_isshow);
        }
    }
}

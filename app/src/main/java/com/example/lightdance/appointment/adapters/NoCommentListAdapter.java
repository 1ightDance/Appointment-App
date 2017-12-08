package com.example.lightdance.appointment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author pope
 *         Created by pope on 2017/12/8.
 */

public class NoCommentListAdapter extends RecyclerView.Adapter<NoCommentListAdapter.ViewHolder> {

    private List<BrowserMsgBean> msgBeanList;

    private OnItemClickListener msgOnclickListener = null;
    private Context mContext;

    public NoCommentListAdapter(List<BrowserMsgBean> msgBeanList, Context mContext) {
        this.msgBeanList = msgBeanList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setItemOnclickListener(NoCommentListAdapter.OnItemClickListener msgOnclickListener) {
        this.msgOnclickListener = msgOnclickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comment_list, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BrowserMsgBean msgAppointment = msgBeanList.get(position);
        holder.title.setText(msgAppointment.getTitle());
        holder.content.setText(msgAppointment.getContent());
        holder.type.setText(getType(msgAppointment.getTypeCode()));
        /**
         *通过查询发起人的ObjectId获取发起人的个人信息
         */
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(msgAppointment.getInviter(), new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    holder.inviter.setText(userBean.getUserNickName());
                    Glide.with(mContext).load(userBean.getUserIconId()).into(holder.inviterIcon);
                } else {
                    Toast.makeText(mContext, "适配器查询出错" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        /**
         *给RecyclerView的Item设置点击监听
         */
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
        return msgBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        TextView inviter;
        TextView type;
        ImageView inviterIcon;

        View browserMsgView;

        public ViewHolder(View itemView) {
            super(itemView);
            browserMsgView = itemView;
            title = (TextView) itemView.findViewById(R.id.tv_comment_title);
            content = (TextView) itemView.findViewById(R.id.tv_comment_content);
            inviter = (TextView) itemView.findViewById(R.id.tv_comment_inviter);
            inviterIcon = (ImageView) itemView.findViewById(R.id.img_comment_inviterIcon);
            type = (TextView) itemView.findViewById(R.id.tv_comment_type);
        }
    }

    private String getType(int typeCode) {
        switch (typeCode) {
            case 1:
                return "#自习#";
            case 2:
                return "#电影#";
            case 3:
                return "#桌游#";
            case 4:
                return "#电竞#";
            case 5:
                return "#唱歌#";
            case 6:
                return "#运动#";
            case 7:
                return "#吃饭#";
            case 8:
                return "#旅行#";
            case 9:
                return "#其他#";
            default:
                return "#错误#";
        }
    }

}

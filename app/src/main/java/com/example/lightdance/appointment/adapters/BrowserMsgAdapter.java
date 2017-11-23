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
 * Created by LightDance on 2017/10/5.
 */

public class BrowserMsgAdapter extends RecyclerView.Adapter<BrowserMsgAdapter.ViewHolder> {

    /**
     * 存放每一条信息的数组
     */
    private List<BrowserMsgBean> msgBeanList;

    private OnItemClickListener msgOnclickListener = null;
    private OnInviterClickListener inviterOnClickListener = null;
    Context mContext;


    /**
     * 可以传监听事件的接口,一个对应整条item,另一个对应用户名
     */
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public interface OnInviterClickListener {
        void onClick(int position);
    }

    public void setInviterOnClickListener(OnInviterClickListener inviterOnClickListener) {
        this.inviterOnClickListener = inviterOnClickListener;
    }

    // TODO 设置点击发起人头像弹出该用户信息

    public void setItemOnclickListener(OnItemClickListener msgOnclickListener) {
        this.msgOnclickListener = msgOnclickListener;
    }

    /**
     * 内部类ViewHolder与视图进行连接, @param browserMsgView用来保存最外层item实例
     */
    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView title;
        TextView content;
        TextView inviter;
        TextView personNumberNeed;
        TextView personNumberHave;
        ImageView inviterIcon;

        View browserMsgView;

        /**
         * 构造方法，将成员变量与界面组件一一对应
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            browserMsgView = itemView;
            title = (TextView) itemView.findViewById(R.id.tv_appointment_title);
            content = (TextView) itemView.findViewById(R.id.tv_appointment_content);
            inviter = (TextView) itemView.findViewById(R.id.tv_appointment_inviter);
            personNumberNeed = (TextView) itemView.findViewById(R.id.tv_appointment_personnumber_need);
            personNumberHave = (TextView) itemView.findViewById(R.id.tv_appointment_personnumber_have);
            inviterIcon = (ImageView) itemView.findViewById(R.id.img_appointment_inviterIcon);
        }
    }

    public BrowserMsgAdapter(Context mContext, List<BrowserMsgBean> messageAppointmentList) {
        msgBeanList = messageAppointmentList;
        this.mContext = mContext;
    }


    /**
     * 重写从RecyclerView ADapter中继承来的三个方法
     * 传入布局文件 并膨胀为视图暂存在holder中
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_browse_msg, parent, false);

        //@param holder书上这个地方加了final字段，但是没懂为什么
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //数据与视图绑定


    /**
     * 关于为item的子控件设置监听（回调思想）：
     * 不推荐在这里获取fragment然后调用FragmentManager方法从而弹出DialogFragment，类之间的耦合度应该越低越好，
     * 不要随意持有对方实例
     * 在这里不可以直接对item中控件设置监听，而应使用回调方法，OnclickListener为什么是一个接口原因就在这里
     * 我们定义两个点击监听器的接口，并在这里创建（实现）该接口的私有成员变量，分别用来监听对item和item里发布者昵
     * 称的点击事件
     * 看一下运行app时的过程：在BrowseFragment的实例中，会创建adapter，
     * 然后就可以通过设置OnClickListener接口，即setOnclickListener方法为两个监听器设置具体的监听逻辑（实现
     * onClick()方法）
     * 这样，在这个adapter里，我们就可以把onClick的逻辑实现交给设置onItemClickListener的类（即BrowseFragment）
     * 让这个类去实现接口里的抽象方法onClick()，即是说具体的弹Dialog逻辑应该由BrowseFragment实现
     * 然后等BrowserFragment为adapter设置好监听器之后，我们在这里把监听器绑定ViewHolder中对应的控件
     * 捋一捋逻辑：adapter想为viewholder里对应控件加监听-->adapter不知道点击后会发生什么-->
     * adapter把自己的接口给BrowserFragment-->BrowserFragment帮adapter把接口实现好还给adapter-->adapter
     * 知道了被点击会发生什么
     * 很多情况下我们为Button设监听都是直接new一个匿名类实现onClickListener的东西，其中道理是一样的
     */
    @Override
    public void onBindViewHolder(final BrowserMsgAdapter.ViewHolder holder, int position) {
        BrowserMsgBean msgAppointment = msgBeanList.get(position);
        holder.title.setText(msgAppointment.getTitle());
        holder.content.setText(msgAppointment.getContent());
        //通过查询发起人的ObjectId获取发起人的个人信息
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(msgAppointment.getInviter(), new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null){
                    holder.inviter.setText(userBean.getUserNickName());
                    Glide.with(mContext).load(userBean.getUserIconId()).into(holder.inviterIcon);
                }else{
                    Toast.makeText(mContext,"适配器查询出错" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.personNumberNeed.setText(msgAppointment.getPersonNumberNeed());
        holder.personNumberHave.setText(String.valueOf(msgAppointment.getPersonNumberHave()));
        //给RecyclerView的Item设置点击监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msgOnclickListener != null) {
                    msgOnclickListener.onClick(holder.getAdapterPosition());
                }
            }
        });
        //给RecyclerView的发起人昵称设置点击监听
        holder.inviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inviterOnClickListener != null) {
                    inviterOnClickListener.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    //传入数据源长度
    @Override
    public int getItemCount() {
        return msgBeanList.size();
    }


}

package com.example.lightdance.appointment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lightdance.appointment.Model.NewsBean;
import com.example.lightdance.appointment.R;

import java.util.List;

/**
 * @author pope
 * Created by pope on 2017/10/7.
 */

/**
 *资讯适配器 继承自RecyclerView.Adapter 将泛型指定为NewsAdapter.ViewHolder
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    //创建一个数组全局变量用以存放资讯的数据
    private List<NewsBean> mNewsMsg;
    Context mContext;

    /**
     *定义内部类ViewHolder并继承RecyclerView.ViewHolder 需创建结构体
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView newsImg;
        TextView newsTitle;
        TextView newsContent;

        /**
         *结构体
         */
        public ViewHolder(View itemView) {
            super(itemView);
            newsImg     = (ImageView) itemView.findViewById(R.id.img_news_pic);
            newsTitle   = (TextView) itemView.findViewById(R.id.tv_news_title);
            newsContent = (TextView) itemView.findViewById(R.id.tv_news_content);
        }
    }

    public NewsAdapter(Context mContext,List<NewsBean> newsMsg) {
        mNewsMsg = newsMsg;
        this.mContext = mContext;
    }

    /**
     *重写从RecyclerView ADapter中继承来的三个方法
     *传入布局文件 并膨胀为视图暂存在holder中
     */
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_news,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     *将各项数据进行绑定（内容，标题等）
     */
    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        NewsBean newsBean = mNewsMsg.get(position);
        Glide.with(mContext).load(newsBean.getImgId()).into(holder.newsImg);
        holder.newsTitle.setText(newsBean.getNewsTitle());
        holder.newsContent.setText(newsBean.getNewsContent());
    }

    /**
     *传入数据源长度
     */
    @Override
    public int getItemCount() {
        return mNewsMsg.size();
    }
}

package com.example.lightdance.appointment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lightdance.appointment.Model.NewsBean;
import com.example.lightdance.appointment.R;

import java.util.List;

/**
 * Created by pope on 2017/10/7.
 */
//咨询适配器 继承自RecyclerView.Adapter 将泛型指定为NewsAdapter.ViewHolder
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    //创建一个数组全局变量用以存放资讯的数据
    private List<NewsBean> mNewsMsg;

    //定义内部类ViewHolder并继承RecyclerView.ViewHolder 需创建结构体
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView newsImg;
        TextView newsTitle;
        TextView newsContent;

        //结构体
        public ViewHolder(View itemView) {
            super(itemView);
            newsImg     = (ImageView) itemView.findViewById(R.id.img_news_pic);
            newsTitle   = (TextView) itemView.findViewById(R.id.tv_news_title);
            newsContent = (TextView) itemView.findViewById(R.id.tv_news_content);
        }
    }

    public NewsAdapter(List<NewsBean> newsMsg) {
        mNewsMsg = newsMsg;
    }

    //重写从RecyclerView ADapter中继承来的三个方法
    //传入布局文件 并膨胀为视图暂存在holder中
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_news,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //将各项数据进行绑定
    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        NewsBean newsBean = mNewsMsg.get(position);
        // TODO 更改图片加载方式 Glide
        holder.newsImg.setImageResource(newsBean.getImgId());
        holder.newsTitle.setText(newsBean.getNewsTitle());
        holder.newsContent.setText(newsBean.getNewsContent());
    }

    //传入数据源长度
    @Override
    public int getItemCount() {
        return mNewsMsg.size();
    }
}

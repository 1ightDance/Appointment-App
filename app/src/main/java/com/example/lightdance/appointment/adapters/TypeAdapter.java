package com.example.lightdance.appointment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lightdance.appointment.Model.TypeBean;
import com.example.lightdance.appointment.R;

import java.util.List;

/**
 * Created by pope on 2017/10/28.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    private List<TypeBean> mTypeList;
    private Context mContext;
    private OnTypeItemClickListener typeListener = null;

    public TypeAdapter(Context context,List<TypeBean> mTypeList) {
        this.mTypeList = mTypeList;
        mContext = context;
    }


    @Override
    public TypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_type,parent,false);
        TypeAdapter.ViewHolder holder = new TypeAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TypeAdapter.ViewHolder holder, int position) {
        TypeBean typeBean = mTypeList.get(position);
        Glide.with(mContext).load(typeBean.getTypeImg()).into(holder.typeImg);
        holder.typeTxt.setText(typeBean.getTypeName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typeListener != null){
                    typeListener.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView typeImg;
        TextView typeTxt;

        View typeItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            typeItemView = itemView;
            typeImg = (ImageView) itemView.findViewById(R.id.img_type_pic);
            typeTxt = (TextView) itemView.findViewById(R.id.tv_type_txt);
        }
    }

    public interface OnTypeItemClickListener {
        void onClick(int position);
    }

    public void setTypeItemOnclickListener(OnTypeItemClickListener typeListener) {
        this.typeListener = typeListener;
    }

}

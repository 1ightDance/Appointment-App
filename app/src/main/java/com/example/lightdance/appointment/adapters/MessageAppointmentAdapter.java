//package com.example.lightdance.appointment.adapters;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.lightdance.appointment.fragments.BrowseFragment;
//import com.example.lightdance.appointment.R;
//
//import java.util.List;
//
///**
// * Created by LightDance on 2017/10/5.
// */
//
//public class MessageAppointmentAdapter
//        extends RecyclerView.Adapter<MessageAppointmentAdapter.ViewHolder> {
//
//    private Context mContext;
//
//    private List<BrowseFragment> appointmentMsgFragmentList;
//
//    static class ViewHolder extends RecyclerView.ViewHolder{
//        private TextView title;
//        private TextView publishTime;
//        private TextView beginTime;
//        private TextView endTime;
//        private TextView place;
//        private TextView inviter;
//        private TextView personNumber;
//        private ImageView type;
//        private ImageView inviterIcon;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            title = (TextView)itemView.findViewById(R.id.message_appointment_title);
//            publishTime = (TextView)itemView.findViewById(R.id.message_appointment_publishtime);
//            beginTime = (TextView)itemView.findViewById(R.id.message_appointment_begintime);
//            endTime = (TextView)itemView.findViewById(R.id.message_appointment_endtime);
//            place = (TextView)itemView.findViewById(R.id.message_appointment_place);
//            inviter = (TextView)itemView.findViewById(R.id.message_appointment_inviter);
//            personNumber = (TextView)itemView.findViewById(R.id.message_appointment_personnumber);
//            type = (ImageView)itemView.findViewById(R.id.message_appointment_type);
//            inviterIcon = (ImageView)itemView.findViewById(R.id.message_appointment_inviterIcon);
//        }
//    }
//
//    public MessageAppointmentAdapter(List<BrowseFragment> appointmentMsgFragmentList){
//        this.appointmentMsgFragmentList = appointmentMsgFragmentList;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (null == mContext){
//            mContext = parent.getContext();
//        }
//        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_browse)
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}

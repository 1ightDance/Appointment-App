package com.example.lightdance.appointment.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.R;

import org.litepal.tablemanager.Connector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by LightDance on 2017/10/4.
 */

public class NewAppointmentFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.new_appointment_done)
    FloatingActionButton newAppointmentDone;
    @BindView(R.id.editText_activity_place)
    EditText editTextActivityPlace;
    @BindView(R.id.editText_activity_title)
    EditText editTextActivityTitle;
    @BindView(R.id.tv_activity_end_time)
    TextView tvActivityEndTime;
    @BindView(R.id.tv_activity_start_time)
    TextView tvActivityStartTime;
    @BindView(R.id.tv_activity_start_date)
    TextView tvActivityStartDate;
    @BindView(R.id.tv_activity_end_date)
    TextView tvActivityEndDate;
    @BindView(R.id.editText_activity_content)
    EditText editTextActivityContent;
    @BindView(R.id.editText_activity_contact_way)
    EditText editTextActivityContactWay;

    //该变量用来存储调用日期选择器的View是哪一个 1代表start date/2代表end date
    private int dateChange = 0;
    private int timeChange = 0;

    //获取碎片实例
    private TimePickerFragment timePickerFragment = new TimePickerFragment();

    public NewAppointmentFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        unbinder = ButterKnife.bind(this, view);

        //创建数据库
        Connector.getDatabase();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_activity_end_time, R.id.tv_activity_start_time, R.id.tv_activity_start_date,
            R.id.tv_activity_end_date, R.id.new_appointment_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_start_time:
                //展示碎片会话窗口（dialog）
                timePickerFragment.show(getFragmentManager(), "TimePicker");
                dateChange = 1;
                timeChange = 1;
                break;
            case R.id.tv_activity_end_time:
                timePickerFragment.show(getFragmentManager(), "TimePicker");
                dateChange = 2;
                timeChange = 2;
                break;
            case R.id.tv_activity_start_date:
                timePickerFragment.show(getFragmentManager(), "DatePicker");
                dateChange = 1;
                timeChange = 1;
                break;
            case R.id.tv_activity_end_date:
                timePickerFragment.show(getFragmentManager(), "DatePicker");
                dateChange = 2;
                timeChange = 2;
                break;
            case R.id.new_appointment_done:
                //点击完成后 获取数据储存到数据库
                BrowseMsgBean browseMsgBean = new BrowseMsgBean();
                browseMsgBean.setTitle(editTextActivityTitle.getText().toString());
                browseMsgBean.setStartTime(tvActivityStartDate.getText().toString()
                        + "  " + tvActivityStartTime.getText().toString());
                browseMsgBean.setEndTime(tvActivityEndDate.getText().toString()
                        + "  " + tvActivityEndTime.getText().toString());
                browseMsgBean.setPlace(editTextActivityPlace.getText().toString());
                browseMsgBean.setContent(editTextActivityContent.getText().toString());
                browseMsgBean.setContactWay(editTextActivityContactWay.getText().toString());
                Toast.makeText(getActivity(), "约人信息发布成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //判断更改哪个TextView显示的日期文本
    public void setDate(int yearSelect, int monthSelect, int daySelect) {
        if (dateChange == 1) {
            setStartDate(yearSelect, monthSelect, daySelect);
        }
        if (dateChange == 2) {
            setEndDate(yearSelect, monthSelect, daySelect);
        }
        dateChange = 0;
    }

    //判断更改哪个TextView显示的时间文本
    public void setTime(int hour, int minute) {
        if (timeChange == 1) {
            setStartTime(hour, minute);
        }
        if (timeChange == 2) {
            setEndTime(hour, minute);
        }
        timeChange = 0;
    }

    //更改方法
    public void setStartDate(int year, int month, int day) {
        if (month < 10 && day >= 10) {
            tvActivityStartDate
                    .setText(year + "年0" + month + "月" + day + "日");
        }
        if (day < 10 && month >= 10) {
            tvActivityStartDate
                    .setText(year + "年" + month + "月0" + day + "日");
        }
        if (day < 10 && month < 10) {
            tvActivityStartDate
                    .setText(year + "年0" + month + "月0" + day + "日");
        }
        if (day >= 10 && month >= 10) {
            tvActivityStartDate
                    .setText(year + "年" + month + "月" + day + "日");
        }
    }

    public void setEndDate(int year, int month, int day) {
        if (month < 10 && day >= 10) {
            tvActivityEndDate
                    .setText(year + "年0" + month + "月" + day + "日");
        }
        if (day < 10 && month >= 10) {
            tvActivityEndDate
                    .setText(year + "年" + month + "月0" + day + "日");
        }
        if (day < 10 && month < 10) {
            tvActivityEndDate
                    .setText(year + "年0" + month + "月0" + day + "日");
        }
        if (day >= 10 && month >= 10) {
            tvActivityEndDate
                    .setText(year + "年" + month + "月" + day + "日");
        }
    }

    public void setStartTime(int hour, int min) {
        if (hour < 10 && min < 10) {
            tvActivityStartTime.setText("0" + hour + ":0" + min);
        }
        if (hour < 10 && min >= 10) {
            tvActivityStartTime.setText("0" + hour + ":" + min);
        }
        if (hour >= 10 && min < 10) {
            tvActivityStartTime.setText(hour + ":0" + min);
        }
        if (hour >= 10 && min >= 10) {
            tvActivityStartTime.setText(hour + ":" + min);
        }
    }

    public void setEndTime(int hour, int min) {
        if (hour < 10 && min < 10) {
            tvActivityEndTime.setText("0" + hour + ":0" + min);
        }
        if (hour < 10 && min >= 10) {
            tvActivityEndTime.setText("0" + hour + ":" + min);
        }
        if (hour >= 10 && min < 10) {
            tvActivityEndTime.setText(hour + ":0" + min);
        }
        if (hour >= 10 && min >= 10) {
            tvActivityEndTime.setText(hour + ":" + min);
        }
    }
}

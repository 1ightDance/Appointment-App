package com.example.lightdance.appointment.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.MainActivity;

import org.litepal.tablemanager.Connector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by LightDance on 2017/10/4.
 */

public class NewAppointmentFragment extends Fragment {

    Unbinder unbinder;
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
    @BindView(R.id.tv_activity_type_select)
    TextView tvActivityTypeSelect;
    @BindView(R.id.scrollView_newappointment)
    ScrollView scrollViewNewappointment;
    @BindView(R.id.toolbar_newappointment)
    Toolbar toolbar;
    @BindView(R.id.numberPicker)
    NumberPickerView numberPickerView;

    //该变量用来存储调用日期选择器的View是哪一个 1代表start date/2代表end date
    int timeChange = 0;
    int typeData = 0;
    private String personNumb = null;

    private TimePickerFragment timePickerFragment = new TimePickerFragment();
    private AppointmentTypeFragment appointmentTypeFragment = new AppointmentTypeFragment();

    public NewAppointmentFragment() {

    }

    public static NewAppointmentFragment newInstance() {
        NewAppointmentFragment fragment = new NewAppointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        unbinder = ButterKnife.bind(this, view);

        //创造一个存放1到100和无限制的String类型数组用以给数字选择器提供数据
        final String[] numb = new String[101];
        for (int i = 0;i<100;i++){
            numb[i] = "" + (i+1);
        }
        numb[100] = "无限制";
        numberPickerView.setDisplayedValues(numb);//传入数组
        numberPickerView.setMinValue(0);
        numberPickerView.setMaxValue(numb.length - 1);
        numberPickerView.setValue(0);//设置第一次显示的位置
        //给数字选择器设置监听
        numberPickerView.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                personNumb = numb[newVal];
            }
        });

        //toolbar
        toolbar.setTitle("发起约人");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.changeFragment(1);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_activity_end_time, R.id.tv_activity_start_time, R.id.tv_activity_start_date,
            R.id.tv_activity_end_date, R.id.tv_activity_type_select,
            R.id.imageView_activity_type_next,R.id.btn_newappointment_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_start_time:
                //展示碎片会话窗口（dialog）
                timeChange = 1;
                timePickerFragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.tv_activity_end_time:
                timeChange = 2;
                timePickerFragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.tv_activity_start_date:
                timeChange = 1;
                timePickerFragment.show(getFragmentManager(), "DatePicker");
                break;
            case R.id.tv_activity_end_date:
                timeChange = 2;
                timePickerFragment.show(getFragmentManager(), "DatePicker");
                break;
            case R.id.tv_activity_type_select:
                appointmentTypeFragment
                        .show(getFragmentManager(), "AppointmentTypeFragment");
                appointmentTypeFragment.setOnTypeSelectListener(new AppointmentTypeFragment.OnTypeSelectListener() {
                    @Override
                    public void onSelect(String text) {
                        tvActivityTypeSelect.setText(text);
                    }

                    @Override
                    public void sendId(int checkId) {
                        sendTypeData(checkId);
                    }
                });
                break;
            case R.id.imageView_activity_type_next:
                break;
            case R.id.btn_newappointment_done:
                if (!isDataIllegal()) {
                    saveData();
                    Toast.makeText(getActivity(), "约人信息发布成功", Toast.LENGTH_SHORT).show();
                    //发布成功后跳转至广场碎片
                    MainActivity activity = (MainActivity) getActivity();
                    activity.changeFragment(1);
                    clearData();
                }
                break;
        }
    }

    //清空数据方法
    private void clearData() {
        editTextActivityTitle.setText("");
        tvActivityStartDate.setText("2017年01月01日");
        tvActivityStartTime.setText("12:00");
        tvActivityEndDate.setText("2017年12月31日");
        tvActivityEndTime.setText("12:00");
        editTextActivityPlace.setText("");
        editTextActivityContent.setText("");
        editTextActivityContactWay.setText("");
        tvActivityTypeSelect.setText("未选择");
    }

    //传送选择的活动类型的数据
    private void sendTypeData(int checkId) {
        switch (checkId) {
            case R.id.radioButton_brpg:
                typeData = R.drawable.ic_brpg;
                break;
            case R.id.radioButton_dining:
                typeData = R.drawable.ic_dining;
                break;
            case R.id.radioButton_game:
                typeData = R.drawable.ic_game;
                break;
            case R.id.radioButton_movies:
                typeData = R.drawable.ic_movies;
                break;
            case R.id.radioButton_others:
                typeData = R.drawable.ic_others;
                break;
            case R.id.radioButton_singing:
                typeData = R.drawable.ic_singing;
                break;
            case R.id.radioButton_sports:
                typeData = R.drawable.ic_sports;
                break;
            case R.id.radioButton_study:
                typeData = R.drawable.ic_study;
                break;
            case R.id.radioButton_travel:
                typeData = R.drawable.ic_travel;
                break;
        }
    }

    //判断发布信息内容是否合法
    private boolean isDataIllegal() {
        //缺少一个时间合法性检测
        if (editTextActivityTitle.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "标题不可为空！", Toast.LENGTH_SHORT).show();
            return true;//返回不合法
        } else if (tvActivityTypeSelect.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "请选择活动类型！", Toast.LENGTH_SHORT).show();
            return true;
        } else if (editTextActivityPlace.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "请指定活动地点！", Toast.LENGTH_SHORT).show();
            return true;
        } else if (editTextActivityTitle.getText().toString().contains("约炮")) {
            Toast.makeText(getContext(), "禁止约炮！再约打死你", Toast.LENGTH_SHORT).show();
            return true;
        } else if (editTextActivityContent.getText().toString().isEmpty()) {
            //或许加一个警告弹窗提醒他没写描述？
            editTextActivityContent.setText("这家伙什么描述也没有留下");
        } else if (editTextActivityContactWay.getText().toString().isEmpty()) {
            editTextActivityContactWay.setText("休想get我的联系方式");
        }

        return false;
    }

    //数据存储方法
    private void saveData() {
        //创建数据库
        Connector.getDatabase();
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
        browseMsgBean.setTypeIconId(typeData);
        browseMsgBean.setPersonNumberNeed(personNumb);
        browseMsgBean.setPersonNumberHave("1");
        browseMsgBean.setInviterIconId(R.mipmap.headshot_2);
        browseMsgBean.setInviter("教皇");
        browseMsgBean.save();
    }

    //判断更改哪个TextView显示的日期文本
    public void setDate(int yearSelect, int monthSelect, int daySelect) {
        if (timeChange == 1) {
            setStartDate(yearSelect, monthSelect, daySelect);
        }
        if (timeChange == 2) {
            setEndDate(yearSelect, monthSelect, daySelect);
        }
        ;
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


package com.example.lightdance.appointment.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.AppointmentDetailActivity;
import com.example.lightdance.appointment.activities.BrowserActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by LightDance on 2017/10/4.
 * <p>
 * TODO 完善时间判断逻辑 包括通过“编辑”按钮启动本Activity的时间加载的数据载入
 *
 * @author LightDance
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
    @BindView(R.id.btn_newappointment_done)
    Button btnNewappointmentDone;

    /**
     * 该变量用来存储调用日期选择器的View是哪一个
     */
    private int timeChange = 0;
    private int typeCode = 0;
    private int from;
    private int startDateYear;
    private int endDateYear;
    private int startDateMonth;
    private int endDateMonth;
    private int startDateDay;
    private int endDateDay;
    private int startTimeHour;
    private int endTimeHour;
    private int startTimeMin;
    private int endTimeMin;
    final private int TIME_START = 666;
    final private int TIME_END = 999;
    final String[] numb = new String[101];

    private Calendar cal;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private String personNumb = "1";
    private int personNumberHave = 1;
    private String editObjectId;

    private TimePickerFragment timePickerFragment = new TimePickerFragment();
    private AppointmentTypeFragment appointmentTypeFragment = new AppointmentTypeFragment();
    private ProgressDialog progressDialog;

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

        //初始化起止时间
        initDateData();

        //初始化时间选择器
        initNumberPicker();

        //toolbar
        toolbar.setTitle("发起约人");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity activity = (BrowserActivity) getActivity();
                if (from == 1) {
                    activity.changeFragment(1);
                }
                if (from == 2) {
                    activity.finish();
                }
                if (from == 3) {
                    Intent intent = new Intent(getActivity(), AppointmentDetailActivity.class);
                    intent.putExtra("objectId", editObjectId);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        //从activity中获取到FromCode
        BrowserActivity a = (BrowserActivity) getActivity();
        from = a.getFromCode();
        editObjectId = a.getEditObjectId();
        if (from == 3) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("请稍等");
            progressDialog.setMessage("加载中...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            toolbar.setTitle("编辑活动");
            btnNewappointmentDone.setText("保存更改");
            //加载被编辑的BrowserBean数据
            loadAppointmentData();
        }

        return view;
    }

    /**
     * 加载被编辑的帖子的数据
     */
    private void loadAppointmentData() {
        BmobQuery<BrowserMsgBean> query = new BmobQuery<>();
        query.getObject(editObjectId, new QueryListener<BrowserMsgBean>() {
            @Override
            public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                if (e == null) {
                    typeCode = browserMsgBean.getTypeCode();
                    personNumberHave = browserMsgBean.getPersonNumberHave();
                    editTextActivityTitle.setText(browserMsgBean.getTitle());
                    tvActivityTypeSelect.setText(getTypeString(browserMsgBean.getTypeCode()));
                    editTextActivityPlace.setText(browserMsgBean.getPlace());
                    editTextActivityContent.setText(browserMsgBean.getContent());
                    editTextActivityContactWay.setText(browserMsgBean.getContactWay());
                    loadPersonNumberNeed(browserMsgBean.getPersonNumberNeed());
                    loadTimeData(browserMsgBean.getStartTime(), TIME_START);
                    loadTimeData(browserMsgBean.getEndTime(), TIME_END);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "获取被编辑帖子信息失败 " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 加载被编辑的帖子的时间数据并显示
     *
     * @param time     时间数据
     * @param timeCode 时间类型（开始/结束）
     */
    private void loadTimeData(String time, int timeCode) {
        int year;
        int month;
        int day;
        int hour;
        int min;
        year = Integer.valueOf(time.substring(0, 4));
        month = Integer.valueOf(time.substring(5, 7));
        day = Integer.valueOf(time.substring(8, 10));
        hour = Integer.valueOf(time.substring(12, 14));
        min = Integer.valueOf(time.substring(15, 17));
        switch (timeCode) {
            case TIME_START:
                startDateYear = year;
                startDateMonth = month;
                startDateDay = day;
                startTimeHour = hour;
                startTimeMin = min;
                setSelectTime(year, month, day, hour, min, TIME_START);
                break;
            case TIME_END:
                endDateYear = year;
                endDateMonth = month;
                endDateDay = day;
                endTimeHour = hour;
                endTimeMin = min;
                setSelectTime(year, month, day, hour, min, TIME_END);
                break;
            default:
                break;
        }
    }

    /**
     * 加载被编辑的帖子的需要人数并显示在数字选择器上
     *
     * @param personNumberNeed 需要人数
     */
    private void loadPersonNumberNeed(String personNumberNeed) {
        for (int i = 0; i < 101; i++) {
            if (personNumberNeed.equals(numb[i])) {
                numberPickerView.setValue(i);
                personNumb = numb[i];
                break;
            }
        }
    }

    /**
     * 初始化时间选择器
     */
    private void initNumberPicker() {
        //创造一个存放1到100和无限制的String类型数组用以给数字选择器提供数据
        for (int i = 0; i < 100; i++) {
            numb[i] = String.valueOf(i + 1);
        }
        numb[100] = "∞";
        //传入数组
        numberPickerView.setDisplayedValues(numb);
        numberPickerView.setMinValue(0);
        numberPickerView.setMaxValue(numb.length - 1);
        //设置第一次显示的位置
        numberPickerView.setValue(0);
        //给数字选择器设置监听
        numberPickerView.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                personNumb = numb[newVal];
            }
        });
    }

    /**
     * 初始化时间数据方法
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDateData() {
        //获取时间
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        startDateYear = year;
        endDateYear = year;
        startDateMonth = month;
        endDateMonth = month;
        startDateDay = day;
        endDateDay = day;
        startTimeHour = hour;
        endTimeHour = hour;
        startTimeMin = min;
        endTimeMin = min+1;
        setSelectTime(startDateYear, startDateMonth, startDateDay, startTimeHour, startTimeMin, TIME_START);
        setSelectTime(endDateYear, endDateMonth, endDateDay, endTimeHour, endTimeMin, TIME_END);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_activity_end_time, R.id.tv_activity_start_time, R.id.tv_activity_start_date,
            R.id.tv_activity_end_date, R.id.tv_activity_type_select,
            R.id.imageView_activity_type_next, R.id.btn_newappointment_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_start_time:
                //展示碎片会话窗口（dialog）
                timeChange = TIME_START;
                timePickerFragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.tv_activity_end_time:
                timeChange = TIME_END;
                timePickerFragment.show(getFragmentManager(), "TimePicker");
                break;
            case R.id.tv_activity_start_date:
                timeChange = TIME_START;
                timePickerFragment.show(getFragmentManager(), "DatePicker");
                break;
            case R.id.tv_activity_end_date:
                timeChange = TIME_END;
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
                }
                break;
            default:
                break;
        }
    }

    /**
     * 清空数据方法
     */
    private void clearData() {
        editTextActivityTitle.setText("");
        tvActivityStartDate.setText("2017-01-01");
        tvActivityStartTime.setText("12:00");
        tvActivityEndDate.setText("2017-12-31");
        tvActivityEndTime.setText("12:00");
        editTextActivityPlace.setText("");
        editTextActivityContent.setText("");
        editTextActivityContactWay.setText("");
        tvActivityTypeSelect.setText("未选择");
        numberPickerView.setValue(0);
    }

    /**
     * 传送选择的活动类型的数据
     *
     * @param checkId
     */
    private void sendTypeData(int checkId) {
        switch (checkId) {
            case R.id.radioButton_brpg:
                typeCode = 3;
                break;
            case R.id.radioButton_dining:
                typeCode = 7;
                break;
            case R.id.radioButton_game:
                typeCode = 4;
                break;
            case R.id.radioButton_movies:
                typeCode = 2;
                break;
            case R.id.radioButton_others:
                typeCode = 9;
                break;
            case R.id.radioButton_singing:
                typeCode = 5;
                break;
            case R.id.radioButton_sports:
                typeCode = 6;
                break;
            case R.id.radioButton_study:
                typeCode = 1;
                break;
            case R.id.radioButton_travel:
                typeCode = 8;
                break;
            default:
                break;
        }
    }

    private String getTypeString(int typeCode) {
        String s;
        switch (typeCode) {
            case 1:
                s = "自习";
                break;
            case 2:
                s = "电影";
                break;
            case 3:
                s = "桌游";
                break;
            case 4:
                s = "电竞";
                break;
            case 5:
                s = "唱歌";
                break;
            case 6:
                s = "运动";
                break;
            case 7:
                s = "吃饭";
                break;
            case 8:
                s = "旅行";
                break;
            case 9:
                s = "其他";
                break;
            default:
                s = "错误";
                break;
        }
        return s;
    }

    /**
     * 判断发布信息内容是否合法
     *
     * @return true：不合法 false：合法
     */
    private boolean isDataIllegal() {
        //缺少一个时间合法性检测
        if (editTextActivityTitle.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "标题不可为空！", Toast.LENGTH_SHORT).show();
            //返回不合法
            return true;
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

    /**
     * 数据存储方法
     */
    private void saveData() {
        if (from == 3) {
            // 保存修改后的数据的逻辑
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("请稍等");
            progressDialog.setMessage("发布中...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            //点击完成后 获取发布页填写的数据储存到后台
            BrowserMsgBean browserMsgBean = new BrowserMsgBean();
            browserMsgBean.setValue("title", editTextActivityTitle.getText().toString());
            browserMsgBean.setValue("content", editTextActivityContent.getText().toString());
            browserMsgBean.setValue("personNumberNeed", personNumb);
            browserMsgBean.setValue("personNumberHave", personNumberHave);
            browserMsgBean.setValue("place", editTextActivityPlace.getText().toString());
            browserMsgBean.setValue("typeCode", typeCode);
            browserMsgBean.setValue("startTime", tvActivityStartDate.getText().toString()
                    + "  " + tvActivityStartTime.getText().toString());
            browserMsgBean.setValue("endTime", tvActivityEndDate.getText().toString()
                    + "  " + tvActivityEndTime.getText().toString());
            browserMsgBean.setValue("contactWay", editTextActivityContactWay.getText().toString());
            browserMsgBean.update(editObjectId, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        //编辑完成自动跳转至其活动新发布的地方
                        Toast.makeText(getActivity(), "保存修改成功", Toast.LENGTH_SHORT).show();
                        BrowserActivity browserActivity = (BrowserActivity) getActivity();
                        BrowseFragment browseFragment = (BrowseFragment) browserActivity.getFragment(1);
                        browseFragment.sendSelectType(typeCode);
                        browserActivity.changeFragment(1);
                    } else {
                        Toast.makeText(getActivity(), "保存修改失败 " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            });
        } else {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("请稍等");
            progressDialog.setMessage("发布中...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            //点击完成后 获取发布页填写的数据储存到后台
            BrowserMsgBean browserMsgBean = new BrowserMsgBean();
            browserMsgBean.setTitle(editTextActivityTitle.getText().toString());
            browserMsgBean.setStartTime(tvActivityStartDate.getText().toString()
                    + "  " + tvActivityStartTime.getText().toString());
            browserMsgBean.setEndTime(tvActivityEndDate.getText().toString()
                    + "  " + tvActivityEndTime.getText().toString());
            browserMsgBean.setPlace(editTextActivityPlace.getText().toString());
            browserMsgBean.setContent(editTextActivityContent.getText().toString());
            browserMsgBean.setContactWay(editTextActivityContactWay.getText().toString());
            browserMsgBean.setTypeCode(typeCode);
            browserMsgBean.setPersonNumberNeed(personNumb);
            browserMsgBean.setPersonNumberHave(1);
            //获取当前用户的信息存储为该活动的发布人
            SharedPreferences preferences = getActivity().getSharedPreferences("loginData", Context.MODE_PRIVATE);
            final String userBeanId = preferences.getString("userBeanId", "BUG");
            browserMsgBean.setInviter(userBeanId);
            List<String> memberList = new ArrayList<>();
            memberList.add(userBeanId);
            browserMsgBean.setMembers(memberList);
            List<String> noCommentUser = new ArrayList<>();
            noCommentUser.add(userBeanId);
            browserMsgBean.setNoCommentUser(noCommentUser);
            {}
            browserMsgBean.save(new SaveListener<String>() {
                @Override
                public void done(final String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(getActivity(), "约人信息发布成功", Toast.LENGTH_SHORT).show();
                        BrowserActivity activity = (BrowserActivity) getActivity();
                        //判断当前页从哪里跳转来的
                        //并通过不同方法 刷新并跳转回BrowserFragment
                        if (from == 1) {
                            BrowseFragment browseFragment = (BrowseFragment) activity.getFragment(1);
                            browseFragment.initBrowserData();
                            activity.changeFragment(1);
                        }
                        if (from == 2) {
                            BrowserActivity browserActivity = (BrowserActivity) getActivity();
                            BrowseFragment browseFragment = (BrowseFragment) browserActivity.getFragment(1);
                            browseFragment.sendSelectType(typeCode);
                            browserActivity.changeFragment(1);
                        }
                        progressDialog.dismiss();
                        clearData();
                    } else {
                        Toast.makeText(getActivity(), "发布失败 " + e.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    /**
     * 确保选择的结束时间晚于开始时间方法
     *
     * @param year   用户选择的“年”数据
     * @param month  用户选择的“月”数据
     * @param day    用户选择的“日”数据
     * @param hour   用户选择的“时”数据
     * @param minute 用户选择的“分”数据
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ensureTimeRight(int year, int month, int day, int hour, int minute) {
        long startMillisSec;
        long endMillisSec;
        switch (timeChange) {
            case TIME_START:
                cal.set(year,month,day,hour,minute);
                startMillisSec = cal.getTimeInMillis();
                cal.set(endDateYear,endDateMonth,endDateDay,endTimeHour,endTimeMin);
                endMillisSec = cal.getTimeInMillis();
                if (startMillisSec < endMillisSec) {
                    startDateYear = year;
                    startDateMonth = month;
                    startDateDay = day;
                    startTimeHour = hour;
                    startTimeMin = minute;
                    setSelectTime(year, month, day, hour, minute, timeChange);
                } else {
                    Toast.makeText(getActivity(), "开始时间要早于结束时间噢~", Toast.LENGTH_LONG).show();
                }
                break;
            case TIME_END:
                cal.set(startDateYear,startDateMonth,startDateDay,startTimeHour,startTimeMin);
                startMillisSec = cal.getTimeInMillis();
                cal.set(year,month,day,hour,minute);
                endMillisSec = cal.getTimeInMillis();
                if (startMillisSec < endMillisSec) {
                    endDateYear = year;
                    endDateMonth = month;
                    endDateDay = day;
                    endTimeHour = hour;
                    endTimeMin = minute;
                    setSelectTime(year, month, day, hour, minute, timeChange);
                } else {
                    Toast.makeText(getActivity(), "结束时间要晚于开始时间噢~", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更改选择的时间方法
     *
     * @param year  用户选择的“年”数据
     * @param month 用户选择的“月”数据
     * @param day   用户选择的“日”数据
     * @param hour  用户选择的“时”数据
     * @param min   用户选择的“分”数据
     */
    public void setSelectTime(int year, int month, int day, int hour, int min, int timeChange) {
        if (timeChange == TIME_START) {
            if (month < 10 && day >= 10) {
                tvActivityStartDate.setText(year + "-0" + month + "-" + day);
            }
            if (day < 10 && month >= 10) {
                tvActivityStartDate.setText(year + "-" + month + "-0" + day);
            }
            if (day < 10 && month < 10) {
                tvActivityStartDate.setText(year + "-0" + month + "-0" + day);
            }
            if (day >= 10 && month >= 10) {
                tvActivityStartDate.setText(year + "-" + month + "-" + day);
            }
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
        if (timeChange == TIME_END) {
            if (month < 10 && day >= 10) {
                tvActivityEndDate.setText(year + "-0" + month + "-" + day);
            }
            if (day < 10 && month >= 10) {
                tvActivityEndDate.setText(year + "-" + month + "-0" + day);
            }
            if (day < 10 && month < 10) {
                tvActivityEndDate.setText(year + "-0" + month + "-0" + day);
            }
            if (day >= 10 && month >= 10) {
                tvActivityEndDate.setText(year + "-" + month + "-" + day);
            }
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
}


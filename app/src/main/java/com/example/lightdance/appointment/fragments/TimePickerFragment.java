package com.example.lightdance.appointment.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.lightdance.appointment.R;

/**
 * A simple {@link Fragment} subclass.
 */
//继承自会话碎片 使碎片以会话窗口的形式出现
public class TimePickerFragment extends DialogFragment {


    public TimePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //去掉标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }

}

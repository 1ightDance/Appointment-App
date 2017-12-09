package com.example.lightdance.appointment.fragments;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentTypeFragment extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.radioGroup3)
    RadioGroup radioGroup3;
    @BindView(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @BindView(R.id.radioGroup1)
    RadioGroup radioGroup1;
    @BindView(R.id.radioButton_study)
    RadioButton radioButtonStudy;
    @BindView(R.id.radioButton_movies)
    RadioButton radioButtonMovies;
    @BindView(R.id.radioButton_brpg)
    RadioButton radioButtonBrpg;
    @BindView(R.id.radioButton_game)
    RadioButton radioButtonGame;
    @BindView(R.id.radioButton_singing)
    RadioButton radioButtonSinging;
    @BindView(R.id.radioButton_sports)
    RadioButton radioButtonSports;
    @BindView(R.id.radioButton_dining)
    RadioButton radioButtonDining;
    @BindView(R.id.radioButton_travel)
    RadioButton radioButtonTravel;
    @BindView(R.id.radioButton_others)
    RadioButton radioButtonOthers;

    private OnTypeSelectListener listener;
    private int radioButtonId;

    public AppointmentTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment_type, container, false);
        unbinder = ButterKnife.bind(this, view);

        radioGroup1.setOnCheckedChangeListener(new OnMyRadioGroupOneListener());
        radioGroup2.setOnCheckedChangeListener(new OnMyRadioGroupTwoListener());
        radioGroup3.setOnCheckedChangeListener(new OnMyRadioGroupThreeListener());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imageView_type_select)
    public void onViewClicked() {
        //获取RadioButton的Text并赋值给s
        RadioButton b = ((RadioButton) getView().findViewById(radioButtonId));
        String s = b.getText().toString();
        listener.onSelect(s);
        listener.sendId(b.getId());
        dismiss();
    }
    /**
     * 给碎片提供OnTypeSelectListener类监听实例
     */

    public void setOnTypeSelectListener(OnTypeSelectListener listener) {
        this.listener = listener;
    }

    /**
     * 自定义监听接口
     */
    interface OnTypeSelectListener {
        void onSelect(String text);
        /**
         * 传递ID方法
         */
        void sendId(int checkId);
    }

    private class OnMyRadioGroupOneListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.radioButton_dining:
                    if (radioButtonDining.isChecked()) {
                        radioGroup2.clearCheck();
                        radioGroup3.clearCheck();
                    }
                    break;
                case R.id.radioButton_travel:
                    if (radioButtonTravel.isChecked()) {
                        radioGroup2.clearCheck();
                        radioGroup3.clearCheck();
                    }
                    break;
                case R.id.radioButton_others:
                    if (radioButtonOthers.isChecked()) {
                        radioGroup2.clearCheck();
                        radioGroup3.clearCheck();
                    }
                    break;
                    default:
            }
            radioButtonId = checkedId;
        }
    }

    private class OnMyRadioGroupTwoListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.radioButton_game:
                    if (radioButtonGame.isChecked()) {
                        radioGroup1.clearCheck();
                        radioGroup3.clearCheck();
                    }
                    break;
                case R.id.radioButton_singing:
                    if (radioButtonSinging.isChecked()) {
                        radioGroup1.clearCheck();
                        radioGroup3.clearCheck();
                    }
                    break;
                case R.id.radioButton_sports:
                    if (radioButtonSports.isChecked()) {
                        radioGroup1.clearCheck();
                        radioGroup3.clearCheck();
                    }
                    break;
                default:
            }
            radioButtonId = checkedId;
        }
    }

    private class OnMyRadioGroupThreeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.radioButton_study:
                    if (radioButtonStudy.isChecked()) {
                        radioGroup1.clearCheck();
                        radioGroup2.clearCheck();

                    }
                    break;
                case R.id.radioButton_movies:
                    if (radioButtonMovies.isChecked()) {
                        radioGroup1.clearCheck();
                        radioGroup2.clearCheck();
                    }
                    break;
                case R.id.radioButton_brpg:
                    if (radioButtonBrpg.isChecked()) {
                        radioGroup1.clearCheck();
                        radioGroup2.clearCheck();
                    }
                    break;
                    default:
            }
            radioButtonId = checkedId;
        }
    }
}

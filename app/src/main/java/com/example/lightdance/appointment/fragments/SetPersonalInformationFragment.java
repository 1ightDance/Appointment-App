package com.example.lightdance.appointment.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.PersonalInformationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author pope
 * A simple {@link Fragment} subclass.
 */
public class SetPersonalInformationFragment extends DialogFragment {


    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.tv_edit_title)
    TextView tvEditTitle;
    Unbinder unbinder;

    public SetPersonalInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_personal_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.img_edit_done)
    public void onViewClicked() {
        String s = editText.getText().toString();
        PersonalInformationActivity a = (PersonalInformationActivity) getActivity();
        a.setMsg(s);
        a.updateData(s);
        dismiss();
    }

}

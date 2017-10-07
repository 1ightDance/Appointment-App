package com.example.lightdance.appointment.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends Fragment {

    @BindView(R.id.tv_user_name)
    TextView textViewUserName;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //将碎片膨胀成视图(View)
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this,view);

        return view;

//        textViewUserName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
    }

}

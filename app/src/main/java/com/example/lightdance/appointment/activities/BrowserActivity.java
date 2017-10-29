package com.example.lightdance.appointment.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.BrowseFragment;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        BrowseFragment browseFragment = BrowseFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container_browser, browseFragment);
        transaction.commit();

    }
}

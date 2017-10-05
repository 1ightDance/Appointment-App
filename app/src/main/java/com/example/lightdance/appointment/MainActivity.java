package com.example.lightdance.appointment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.main_bottomnavigationview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_appointment:
                        Toast.makeText(MainActivity.this , "first item" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_browse:
                        Toast.makeText(MainActivity.this , "second item" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_news:
                        Toast.makeText(MainActivity.this , "third item" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_me:
                        Toast.makeText(MainActivity.this , "fourth item" , Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

        });

    }
}

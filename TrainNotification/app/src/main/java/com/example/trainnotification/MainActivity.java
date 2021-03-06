package com.example.trainnotification;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    DashboardFragment2 dashboard_fragment;
    SettingFragment setting_fragment;
    PlatformFragment platform_fragment;
    BottomNavigationView nav;
    Toolbar toolbar;
    long time = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        dashboard_fragment= new DashboardFragment2();
        setting_fragment= new SettingFragment();
        platform_fragment = new PlatformFragment();

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        setFrag(0);
        nav = findViewById(R.id.main_navigationbar);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        return true;
                    case R.id.action_platform:
                        setFrag(1);
                        return true;
                    case R.id.action_setting:
                        setFrag(2);
                        return true;
                }
                return false;
            }
        });


    }

    public void setFrag(int n) {
        switch (n) {
            case 0 :
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, dashboard_fragment).commit();
//                Log.e("Set Fragment --->", String.valueOf(n));
                break;
            case 1 :
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, platform_fragment).commit();
//                Log.e("Set Fragment --->", String.valueOf(n));
                break;
            case 2 :
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, setting_fragment).commit();
//                Log.e("Set Fragment --->", String.valueOf(n));
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_notification:
                Intent intent = new Intent(this, NotificationListActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>2000){
            time= System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "?????? ????????? ?????? ??? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }
}
package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Adapter.AdapterIntro;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.Intro;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    List<Intro> list_intro = new ArrayList<>();

    RequestQueue requestQueue;
    Global global;

    Button btn_go_ro_app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (restoreState()) {

            startActivity(new Intent(IntroActivity.this, VerifyPhoneActivity.class));
            finish();

        }

        requestQueue = Volley.newRequestQueue(this);

        viewPager = findViewById(R.id.view_pager_intro);
        tabLayout = findViewById(R.id.tab_layout_intro);

        btn_go_ro_app = findViewById(R.id.btn_intro);
        findAll();

        global = new Global();
        global.getIntro(this, requestQueue, "", viewPager, list_intro);
        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == list_intro.size() - 1) {

                    tabLayout.setVisibility(View.INVISIBLE);
                    btn_go_ro_app.setVisibility(View.VISIBLE);

                } else {

                    tabLayout.setVisibility(View.VISIBLE);
                    btn_go_ro_app.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn_go_ro_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IntroActivity.this, VerifyPhoneActivity.class);
                startActivity(intent);
                finish();

                saveState();


            }
        });


    }

    private boolean restoreState() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PrefIntro", MODE_PRIVATE);
        boolean activity_open;

        activity_open = sharedPreferences.getBoolean("Opened", false);
        return activity_open;

    }


    private void saveState() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PrefIntro", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Opened", true);
        editor.commit();

    }

    private void findAll() {


    }
}
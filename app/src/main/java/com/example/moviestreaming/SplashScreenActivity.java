package com.example.moviestreaming;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SplashScreenActivity extends AppCompatActivity {

    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        connectionManagerMethod();

    }

    private void connectionManagerMethod() {
        // this or getApplicationContext()

        if (ConnectionManager.checkConnection(this)) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
                    finish();

                }
            }, 4000);

        } else {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    showAlertDialogConnection();

                }
            }, 4000);


        }

    }

    private void showAlertDialogConnection() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog_connection, null);

        builder.setView(view);

        FloatingActionButton fab_retry = view.findViewById(R.id.fab_retry);

        fab_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                connectionManagerMethod();

            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }
}
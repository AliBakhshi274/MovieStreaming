package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;

import org.json.JSONObject;

public class VerifyPhoneActivity extends AppCompatActivity {


    RelativeLayout layout_verify_code, layout_verify_phone;

    EditText edt_phone_number;
    Button btn_check, btn_confirm, btn_retry;
    PinView pinView;

    static String phone_number;
    public static int random_verify_code;
    String url = "https://";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        if (restoreState()) {

            startActivity(new Intent(VerifyPhoneActivity.this, LoginActivity.class));
            finish();

        }


        findAll();

        requestQueue = Volley.newRequestQueue(this);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone_number = edt_phone_number.getText().toString().trim();


                layout_verify_phone.setVisibility(View.GONE);
                layout_verify_code.setVisibility(View.VISIBLE);

                sendCodeVerify();

            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int code = Integer.parseInt(pinView.getText().toString());

                //Color.parseColor("#FFFFFF") or Color.WHITE

                if (code == random_verify_code) {
                    pinView.setLineColor(Color.parseColor("#83F40B"));

                    Intent intent = new Intent(VerifyPhoneActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                    saveState();

                } else {
                    pinView.setLineColor(Color.parseColor("#F30707"));


                }


            }
        });


    }

    private void sendCodeVerify() {

        random_verify_code = (int) (Math.random() * 10000);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + phone_number + "" + random_verify_code + "", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        Log.d("Random", random_verify_code + "");


        requestQueue.add(jsonObjectRequest);

    }


    private boolean restoreState() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PrefVerify", MODE_PRIVATE);
        boolean activity_open;

        activity_open = sharedPreferences.getBoolean("Opened", false);
        return activity_open;

    }


    private void saveState() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PrefVerify", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Opened", true);
        editor.commit();

    }


    private void findAll() {

        layout_verify_phone = findViewById(R.id.layout_verify_phone);
        layout_verify_code = findViewById(R.id.layout_verify_code);

        edt_phone_number = findViewById(R.id.edt_phone);

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_check = findViewById(R.id.btn_check);
        btn_retry = findViewById(R.id.btn_retry);

        pinView = findViewById(R.id.pinView);

    }
}
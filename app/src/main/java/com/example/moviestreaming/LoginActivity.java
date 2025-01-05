package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edt_email, edt_phone, edt_password;
    TextView have_no_account, forgot_password;
    Button btn_login;

    private String url_login;

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        url_login = "http://" +  LoginActivity.this.getString(R.string.CurrentIP) + "/moviestreaming/login.php";


        if (restoreState()) {

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();

        }

        findAll();

        requestQueue = Volley.newRequestQueue(this);


        have_no_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_email.getText().toString().trim();
                String phone = edt_phone.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password))
                    Toast.makeText(LoginActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();

                else loginAccount(email, phone, password);

            }
        });


    }

    private void loginAccount(String email, String phone, String password) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Login was successful")) {

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();

                    saveState();

                } else {

                    Toast.makeText(LoginActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error In Login Activity", error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);

                return params;
            }
        };

        requestQueue.add(stringRequest);

    }


    private boolean restoreState() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PrefLogin", MODE_PRIVATE);
        boolean activity_open;

        activity_open = sharedPreferences.getBoolean("Opened", false);
        return activity_open;

    }


    private void saveState() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PrefLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Opened", true);
        editor.putString("email", edt_email.getText().toString().trim());
        editor.putString("phone", edt_phone.getText().toString().trim());

        editor.commit();

    }


    private void findAll() {

        have_no_account = findViewById(R.id.txt_login_have_no_account);
        forgot_password = findViewById(R.id.txt_login_forgot_password);

        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_password = findViewById(R.id.edt_password);

        btn_login = findViewById(R.id.btn_login);


    }
}
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

import static com.example.moviestreaming.R.string.CurrentIP;

public class RegisterActivity extends AppCompatActivity {


    EditText edt_username, edt_email, edt_phone, edt_password;
    TextView have_account;
    Button btn_register;

    private String url_register;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        url_register = "http://" + this.getString(R.string.CurrentIP) + "/moviestreaming/register.php";

        if (restoreState()){

            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            finish();

            saveState();

        }

        requestQueue = Volley.newRequestQueue(this);

        findAll();

        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edt_username.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String phone = edt_phone.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password))
                    Toast.makeText(RegisterActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();

                else registerAccount(username, email, phone, password);

            }
        });


    }

    private void registerAccount(String username, String email, String phone, String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Register was successful")) {

                    Toast.makeText(RegisterActivity.this, "OK", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error", error.getMessage() + "");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();

                params.put("username", username);
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

        have_account = findViewById(R.id.txt_register_have_account);

        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_password = findViewById(R.id.edt_password);

        btn_register = findViewById(R.id.btn_register);

    }

}
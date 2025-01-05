package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    TextView user_email, user_phone, remaining_subscription, charge_account;
    ImageView img_subscription;
    RelativeLayout layout_charge_account;

    SharedPreferences sharedPreferences;

    RequestQueue requestQueue;

    static long subscription_time;
    static long subscription_time_for_send;
    static String email;
    String URL_SEND_SUSBSCRIPTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findAll();

        URL_SEND_SUSBSCRIPTION = "http://" + this.getString(R.string.CurrentIP) + "/moviestreaming/send_subscription.php";
        requestQueue = Volley.newRequestQueue(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        subscription_time = sharedPreferences.getLong("subscription", 0);
        subscription_time_for_send = sharedPreferences.getLong("subscription", 0);


        sharedPreferences = getApplicationContext().getSharedPreferences("PrefLogin", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");

        subscription_time = subscription_time - System.currentTimeMillis();
        subscription_time = subscription_time / 24;
        subscription_time = subscription_time / 60;
        subscription_time = subscription_time / 60;
        subscription_time = subscription_time / 1000;


        if (subscription_time > 0) {

            remaining_subscription.setText(subscription_time + " Days");

        } else {

            subscription_time = 0;
            remaining_subscription.setText("Nothing");

        }


        user_email.setText(email);
        user_phone.setText(sharedPreferences.getString("phone", ""));

        if (BuyAccountActivity.GO_TO_PROFILE_ACTIVITY) {

            layout_charge_account.setVisibility(View.VISIBLE);
            charge_account.setText(BuyAccountActivity.charge_time);
            img_subscription.setImageResource(R.drawable.done);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND_SUSBSCRIPTION, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("Success");

                        if (success.equals("1")) {

                            Toast.makeText(ProfileActivity.this, "Send Success Subscription", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(ProfileActivity.this, "Send Success Subscription Failed!", Toast.LENGTH_SHORT).show();
                    Log.e("Error", error.getMessage() + "");

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String, String> params = new HashMap<>();

                    params.put("email", email);
                    params.put("subscription", String.valueOf(subscription_time_for_send));

                    return params;

                }
            };


            requestQueue.add(stringRequest);


        } else {

            img_subscription.setImageResource(R.drawable.vip);

        }


    }

    private void findAll() {

        user_email = findViewById(R.id.user_email);
        user_phone = findViewById(R.id.user_phone);
        remaining_subscription = findViewById(R.id.remainaing_subscription);
        charge_account = findViewById(R.id.charge_account);
        layout_charge_account = findViewById(R.id.layout_charge_account);
        img_subscription = findViewById(R.id.img_subscription);
    }
}
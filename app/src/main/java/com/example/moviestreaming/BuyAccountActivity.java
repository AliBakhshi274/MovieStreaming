package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.solver.GoalRow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Adapter.AdapterNewMovie;
import com.example.moviestreaming.Model.NewMovie;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    TextView period1, period2, period3, price1, price2, price3;
    CardView card_buy1, card_buy2, card_buy3;
    String s_price1, s_price2, s_price3;

    String url;

    static int selected_position_card;
    static long unix_time;
    static long subscription_time;
    static String charge_time;
    static boolean GO_TO_PROFILE_ACTIVITY = false;
    final static long ONE_MONTH_UNIX_TIME = 2592000000L;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_account);

        findAll();

        url = "http://" + this.getString(R.string.CurrentIP) + "/moviestreaming/getInformationBuyAccount.php";

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        unix_time = sharedPreferences.getLong("subscription", System.currentTimeMillis());

        requestGetDetailSubscription();

        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {


                if (isPaymentSuccess) {
                    /* When Payment Request is Success :) */
                    String message = "Your Payment is Success :) " + refID;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    switch (selected_position_card) {

                        case 1:
                            GO_TO_PROFILE_ACTIVITY = true;
                            subscription_time = unix_time + ONE_MONTH_UNIX_TIME;
                            editor.putLong("subscription", subscription_time);
                            editor.commit();
                            charge_time = "+ 1 Month";
                            Toast.makeText(getApplicationContext(), "Card 1", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BuyAccountActivity.this, ProfileActivity.class));
                            break;
                        case 2:
                            GO_TO_PROFILE_ACTIVITY = true;
                            subscription_time = unix_time + ONE_MONTH_UNIX_TIME * 3;
                            editor.putLong("subscription", subscription_time);
                            editor.commit();
                            charge_time = "+ 3 Months";
                            Toast.makeText(getApplicationContext(), "Card 2", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BuyAccountActivity.this, ProfileActivity.class));
                            break;
                        case 3:
                            GO_TO_PROFILE_ACTIVITY = true;
                            subscription_time = unix_time + ONE_MONTH_UNIX_TIME * 6;
                            editor.putLong("subscription", subscription_time);
                            editor.commit();
                            charge_time = "+ 6 Months";
                            Toast.makeText(getApplicationContext(), "Card 3", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BuyAccountActivity.this, ProfileActivity.class));
                            break;

                    }

                } else {
                    /* When Payment Request is Failure :) */
                    String message = "Your Payment is Failure :(";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }


            }
        });


        card_buy1.setOnClickListener(this);
        card_buy2.setOnClickListener(this);
        card_buy3.setOnClickListener(this);

    }

    private void requestGetDetailSubscription() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonArray = response.getJSONArray("movie_streaming");

                            s_price1 = jsonArray.getJSONObject(0).getString("price");
                            s_price2 = jsonArray.getJSONObject(1).getString("price");
                            s_price3 = jsonArray.getJSONObject(2).getString("price");

                            String s_month1 = jsonArray.getJSONObject(0).getString("month");
                            String s_month2 = jsonArray.getJSONObject(1).getString("month");
                            String s_month3 = jsonArray.getJSONObject(2).getString("month");

                            price1.setText(s_price1 + "IR");
                            price2.setText(s_price2 + "IR");
                            price3.setText(s_price3 + "IR");

                            period1.setText(s_month1);
                            period2.setText(s_month2);
                            period3.setText(s_month3);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BuyAccountActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    private void findAll() {

        period1 = findViewById(R.id.txt_buy_account_period1);
        period2 = findViewById(R.id.txt_buy_account_period2);
        period3 = findViewById(R.id.txt_buy_account_period3);
        price1 = findViewById(R.id.txt_buy_account_price1);
        price2 = findViewById(R.id.txt_buy_account_price2);
        price3 = findViewById(R.id.txt_buy_account_price3);

        card_buy1 = findViewById(R.id.card_buy1);
        card_buy2 = findViewById(R.id.card_buy2);
        card_buy3 = findViewById(R.id.card_buy3);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();


        switch (id) {

            case R.id.card_buy1:
                selected_position_card = 1;
                Toast.makeText(this, s_price1 + " ", Toast.LENGTH_SHORT).show();
                buy_vip_account(s_price1);
                break;
            case R.id.card_buy2:
                selected_position_card = 2;
                Toast.makeText(this, s_price2 + " ", Toast.LENGTH_SHORT).show();
                buy_vip_account(s_price2);
                break;
            case R.id.card_buy3:
                selected_position_card = 3;
                Toast.makeText(this, s_price3 + " ", Toast.LENGTH_SHORT).show();
                buy_vip_account(s_price3);
                break;

        }
    }

    private void buy_vip_account(String amount) {

        ZarinPal purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment = ZarinPal.getPaymentRequest();
        //If you will test on our sandbox, you can use it:
//        PaymentRequest payment = ZarinPal.getSandboxPaymentRequest();

        payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
        payment.setAmount(Long.parseLong(amount));
//        payment.isZarinGateEnable(true);  // If you actived `ZarinGate`, can handle payment by `ZarinGate`
        payment.setDescription("In App Purchase Test SDK");
        payment.setCallbackURL("app://app");     /* Your App Scheme */
        payment.setMobile("09355106005");            /* Optional Parameters */
        payment.setEmail("imannamix@gmail.com");     /* Optional Parameters */


        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {

                if (status == 100) {
                   /*
                   When Status is 100 Open Zarinpal PG on Browser
                   */
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Your Payment Failure :(", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.Comment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {

    public static String ID_ITEM_FOR_COMMENT = "";
    TextView email, comment;
    RecyclerView recycler_view_comment;
    EditText edt_comment;
    ImageView img_send_comment;

    RequestQueue requestQueue;
    Bundle bundle;
    SharedPreferences sharedPreferences;
    Global global;

    List<Comment> list_comment = new ArrayList<>();
    private String URL_SEND_COMMENT;
    String shared_email;
    String txt_comment;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        findAll();

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();

        id = Integer.parseInt(bundle.getString(ID_ITEM_FOR_COMMENT));

        sharedPreferences = getApplicationContext().getSharedPreferences("PrefLogin", MODE_PRIVATE);
        shared_email = sharedPreferences.getString("email", "");

        recycler_view_comment.setHasFixedSize(true);
        recycler_view_comment.setLayoutManager(new LinearLayoutManager(this));

        global = new Global();
        global.getComment(CommentActivity.this, requestQueue, String.valueOf(id), recycler_view_comment, list_comment);


        img_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_comment = edt_comment.getText().toString().trim();
                send_comment();
                edt_comment.setText("");
            }
        });

    }

    private void send_comment() {

        URL_SEND_COMMENT = "http://" + this.getString(R.string.CurrentIP) + "/moviestreaming/send_comment.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND_COMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(CommentActivity.this, String.valueOf(id) + shared_email + txt_comment, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("Success");

                    if (success.equals("1")) {

                        Toast.makeText(CommentActivity.this, "Comment sent successfully", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CommentActivity.this, "Comment not send!", Toast.LENGTH_SHORT).show();
                Log.e("Error", error.getMessage() + "");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();

                params.put("id_item", String.valueOf(id));
                params.put("email", shared_email);
                params.put("comment", txt_comment);

                return params;

            }
        };


        requestQueue.add(stringRequest);


    }

    private void findAll() {

        email = findViewById(R.id.txt_email);
        comment = findViewById(R.id.txt_comment);
        recycler_view_comment = findViewById(R.id.recycler_comment);
        edt_comment = findViewById(R.id.edt_comment);
        img_send_comment = findViewById(R.id.img_send_comment);

    }
}
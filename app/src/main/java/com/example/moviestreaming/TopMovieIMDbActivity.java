package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.TopMovieIMDb;

import java.util.ArrayList;
import java.util.List;

public class TopMovieIMDbActivity extends AppCompatActivity {


    RecyclerView recycler_top_movie_imdb_complete;
    Global global;
    List<TopMovieIMDb> list_top_movie_imdb = new ArrayList<>();
    RequestQueue requestQueue;
    ImageView btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movie_i_m_db);


        requestQueue = Volley.newRequestQueue(this);


        global = new Global();

        recycler_top_movie_imdb_complete = findViewById(R.id.recycler_viewer_top_movie_imdb_complete);
        recycler_top_movie_imdb_complete.setHasFixedSize(true);
        recycler_top_movie_imdb_complete.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        global.getTopMovieIMDbComplete(this, requestQueue,HomeActivity.CATEGORY_NAME, recycler_top_movie_imdb_complete, list_top_movie_imdb);

        btn_back = findViewById(R.id.btn_back_top_movie_imdb);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }
}
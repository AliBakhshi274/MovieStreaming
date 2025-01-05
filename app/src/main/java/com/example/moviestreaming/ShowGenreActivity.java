package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.ShowGenre;

import java.util.ArrayList;
import java.util.List;

public class ShowGenreActivity extends AppCompatActivity {

    Bundle bundle;
    String genre_name;
    TextView genre_name_tv;
    List<ShowGenre> list_show_genre = new ArrayList<>();

    RecyclerView recyclerView_show_genre;

    RequestQueue requestQueue;
    Global global = new Global();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_genre);


        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();
        genre_name = bundle.getString("name");

        genre_name_tv = findViewById(R.id.genre_name);
        genre_name_tv.setText(genre_name);

        recyclerView_show_genre = findViewById(R.id.recycler_viewer_show_genre);
        recyclerView_show_genre.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_show_genre.setHasFixedSize(true);


        global.getShowGenre(this, requestQueue, genre_name, recyclerView_show_genre, list_show_genre);

    }
}
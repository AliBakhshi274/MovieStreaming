package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Adapter.AdapterGenreComplete;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.Genre;

import java.util.ArrayList;
import java.util.List;



public class GenreActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    ImageView img_back;
    Global global;

    //Genre Complete
    RecyclerView recyclerView_genre_complete;
    List<Genre> list_genre_complete = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        requestQueue = Volley.newRequestQueue(this);
        global = new Global();

        getGenreComplete();

        img_back = findViewById(R.id.btn_back);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GenreActivity.this, HomeActivity.class));
                finish();
                // or we can use finish() for back to previous activity :)

            }
        });

    }

    private void getGenreComplete() {

        recyclerView_genre_complete = findViewById(R.id.recycler_viewer_genre_complete);
        recyclerView_genre_complete.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView_genre_complete.setHasFixedSize(true);

        global.getGenreComplete(this, requestQueue, "", recyclerView_genre_complete, list_genre_complete);

    }


}
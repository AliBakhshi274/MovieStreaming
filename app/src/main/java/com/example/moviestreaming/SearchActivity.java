package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SearchActivity extends AppCompatActivity {

    public static String CATEGORY_NAME = "CATEGORY";
    public static String TITLE = "TITLE";

    CardView card_top_movie_imdb, card_aniamtion, card_movie_2020, card_series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findAll();


        card_top_movie_imdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CATEGORY_NAME = "top_movie_imdb";
                TITLE = "Top Movie IMDb";

                startActivity(new Intent(SearchActivity.this, ShowSearchActivity.class));


            }
        });

        card_aniamtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CATEGORY_NAME = "animation";
                TITLE = "Animation";

                startActivity(new Intent(SearchActivity.this, ShowSearchActivity.class));


            }
        });

        card_movie_2020.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CATEGORY_NAME = "movie_new";
                TITLE = "Movie 2020";

                startActivity(new Intent(SearchActivity.this, ShowSearchActivity.class));


            }
        });

        card_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CATEGORY_NAME = "series";
                TITLE = "Series";

                startActivity(new Intent(SearchActivity.this, ShowSearchActivity.class));


            }
        });

    }

    private void findAll() {

        card_top_movie_imdb = findViewById(R.id.card_top_movie_imdb);
        card_aniamtion = findViewById(R.id.card_animation);
        card_movie_2020 = findViewById(R.id.card_movie_2020);
        card_series = findViewById(R.id.card_series);

    }
}
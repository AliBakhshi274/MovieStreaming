package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesActivity extends AppCompatActivity {

    RecyclerView recyclerView_series_complete;
    Global global;
    RequestQueue requestQueue;
    List<Series> listSeriesComplete = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);


        requestQueue = Volley.newRequestQueue(this);

        recyclerView_series_complete = findViewById(R.id.recycler_viewer_series_complete);
        recyclerView_series_complete.setHasFixedSize(true);
        recyclerView_series_complete.setLayoutManager(new LinearLayoutManager(this));

        global = new Global();

        global.getSeriesComplete(this, requestQueue, HomeActivity.CATEGORY_NAME, recyclerView_series_complete, listSeriesComplete);






    }
}
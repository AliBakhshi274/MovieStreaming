package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeActivity extends AppCompatActivity {

    public static String ID_SEASON;

    Bundle bundle;
    int id;

    Global global;
    RequestQueue requestQueue;
    List<Episode> list_episode = new ArrayList<>();

    TextView season_number;
    RecyclerView recyclerView_episode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);


        requestQueue = Volley.newRequestQueue(this);
        global = new Global();

        bundle = getIntent().getExtras();
        id = Integer.parseInt(getIntent().getStringExtra(ID_SEASON));

        season_number = findViewById(R.id.season_number);

        season_number.setText("Season " + bundle.getString("season_number"));

        recyclerView_episode = findViewById(R.id.recycler_episode_activity);
        recyclerView_episode.setHasFixedSize(true);
        recyclerView_episode.setLayoutManager(new GridLayoutManager(this, 2));
        
        global.getEpisode(this, requestQueue, id + "", recyclerView_episode, list_episode);

    }
}
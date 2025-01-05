package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.AllInformation;
import com.example.moviestreaming.Model.Cast;
import com.example.moviestreaming.Model.Season;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailSeriesActivity extends AppCompatActivity {

    public static String ID_DETAIL_ITEM = "";
    public static String CATEGORY_NAME = "CATEGORY_NAME";
    Bundle bundle;

    String category_name;
    int id;

    Global global = new Global();
    List<Cast> list_cast = new ArrayList<>();
    List<Season> list_season = new ArrayList<>();
    List<AllInformation> list_all_information = new ArrayList<>();
    List<AllInformation> list_all_information_series = new ArrayList<>();


    TextView series_name, series_director, series_rate_imdb, series_time, series_published, series_description;
    ImageView series_image, img_back, img_download, img_comment;
    RecyclerView recyclerview_cast, recyclerview_similar, recyclerview_season;
    FloatingActionButton btn_play;

    RequestQueue requestQueue;
    String link_show_detail;
    String link_series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_series);

        bundle = getIntent().getExtras();

        findAll();

        link_show_detail = "http://" + this.getString(R.string.CurrentIP) + "/moviestreaming/show_detail.php?id_item=";
        id = Integer.parseInt(getIntent().getStringExtra(ID_DETAIL_ITEM));
        category_name = getIntent().getStringExtra(CATEGORY_NAME);

        requestQueue = Volley.newRequestQueue(this);
        String LINK = link_show_detail + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                LINK,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("movie_streaming");
                            System.out.println(jsonArray);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("id_item");
                                String link_img = jsonObject.getString("link_img");
                                String description = jsonObject.getString("description");
                                link_series = jsonObject.getString("link_movie");

                                Picasso.get().load(link_img).into(series_image);
                                series_description.setText(description);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowDetailSeriesActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);


        series_name.setText(bundle.getString("name"));
        series_director.setText("Director : " + bundle.getString("director"));
        series_time.setText(bundle.getString("seasons"));
        series_published.setText("Episodes : " + bundle.getString("episodes"));
        series_rate_imdb.setText("IMDb : " + bundle.getString("rate_imdb"));


        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowDetailSeriesActivity.this, VideoPlayActivity.class);
                intent.putExtra("link_movie", link_series);
                startActivity(intent);

            }
        });

        getCast();

        getSeason();

        getSimilar();

    }

    private void getSimilar() {

        //Random
        recyclerview_similar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerview_similar.setHasFixedSize(true);

        global.getSimilarSeries(this, requestQueue, category_name, recyclerview_similar, list_all_information_series);

    }

    private void getSeason() {

        recyclerview_season.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerview_season.setHasFixedSize(true);

        global.getSeason(this, requestQueue, id + "", recyclerview_season, list_season);

    }


    private void getCast() {
        recyclerview_cast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerview_cast.setHasFixedSize(true);

        global.getCast(this, requestQueue, id + "", recyclerview_cast, list_cast);

    }


    private void findAll() {

        series_name = findViewById(R.id.detail_series_name);
        series_director = findViewById(R.id.detail_series_director);
        series_rate_imdb = findViewById(R.id.series_imdb_rate);
        series_time = findViewById(R.id.detail_seasons);
        series_published = findViewById(R.id.detail_episodes);
        series_description = findViewById(R.id.detail_series_text);

        series_image = findViewById(R.id.detail_series_img_video);
        img_back = findViewById(R.id.detail_series_img_back);
        img_download = findViewById(R.id.detail_img_download);
        img_comment = findViewById(R.id.detail_img_comment);

        btn_play = findViewById(R.id.detail_series_fab_play);

        recyclerview_cast = findViewById(R.id.detail_series_cast_recycler);
        recyclerview_similar = findViewById(R.id.detail_series_similar_recycler);
        recyclerview_season = findViewById(R.id.detail_series_season_recycler);

    }

}
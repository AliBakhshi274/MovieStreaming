package com.example.moviestreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Adapter.AdapterSlider;
import com.example.moviestreaming.Adapter.AdapterTopMovieIMDb;
import com.example.moviestreaming.Database.DataSource.FavoriteRepository;
import com.example.moviestreaming.Database.Local.FavoriteDataSource;
import com.example.moviestreaming.Database.Local.RoomDatabaseApp;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.Animation;
import com.example.moviestreaming.Model.Genre;
import com.example.moviestreaming.Model.NewMovie;
import com.example.moviestreaming.Model.PopularMovie;
import com.example.moviestreaming.Model.Series;
import com.example.moviestreaming.Model.Slider;
import com.example.moviestreaming.Model.TopMovieIMDb;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    RequestQueue requestQueue;

    //Slider
    ViewPager slider_home;
    List<Slider> listSlider = new ArrayList<>();
    TabLayout tabs;

    Global global;

    //Top Movie IMDb
    List<TopMovieIMDb> listTopMovieIMDb = new ArrayList<>();
    RecyclerView recyclerView_topMovieIMDb;

    //New Movie
    List<NewMovie> listNewMovie = new ArrayList<>();
    RecyclerView recyclerView_newMovie;

    //Series
    List<Series> listSeries = new ArrayList<>();
    RecyclerView recyclerView_series;

    //PopularMovie
    List<PopularMovie> listPopularMovie = new ArrayList<>();
    RecyclerView recyclerView_popular_movie;

    //Animation
    List<Animation> listAnimation = new ArrayList<>();
    RecyclerView recyclerView_animation;

    //Genre
    List<Genre> listGenre = new ArrayList<>();
    RecyclerView recyclerView_genre;

    //DrawerLayout
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView btn_menu;
    RelativeLayout parent;


    //Subscription
    long first_subscription;
    String URL_GET_SUBSCRIPTION;
    String email;

    //More
    TextView txt_more_genre, txt_more_top_movie_imdb, txt_more_new_movie, txt_more_series, txt_more_popular_movie, txt_more_animation;

    //Key
    static String CATEGORY_NAME;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestQueue = Volley.newRequestQueue(this);

        URL_GET_SUBSCRIPTION = "http://" + this.getString(R.string.CurrentIP) + "/moviestreaming/getSubscription.php?email=";

        global = new Global();


        getSlider();
        getTopMovieIMDb();
        getNewMovie();
        getSeries();
        getPopularMovie();
        getAnimation();
        getGenre();

        showDrawerLayout();
        txtMoreClickListener();

        Db_create();

        sharedPreferences = getApplicationContext().getSharedPreferences("PrefLogin", MODE_PRIVATE);

        View view = navigationView.getHeaderView(0);
        TextView user_email = view.findViewById(R.id.email_user);
        TextView user_phone = view.findViewById(R.id.phone_user);

        email = sharedPreferences.getString("email", "");

        user_email.setText(email);
        user_phone.setText(sharedPreferences.getString("phone", ""));


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL_GET_SUBSCRIPTION + email,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonArray = response.getJSONArray("movie_streaming");

                            first_subscription = jsonArray.getJSONObject(0).getLong("subscription");

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putLong("subscription", first_subscription);
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);


    }

    private void Db_create() {

        Global.roomDatabaseApp = RoomDatabaseApp.getInstance(this);
        Global.favoriteRepository = FavoriteRepository.getInstance(FavoriteDataSource.getInstance(Global.roomDatabaseApp.favoriteDao()));

    }

    private void txtMoreClickListener() {

        txt_more_genre = findViewById(R.id.txt_more_genre);
        txt_more_top_movie_imdb = findViewById(R.id.txt_more_top_movie_imdb);
        txt_more_new_movie = findViewById(R.id.txt_more_new_movie);
        txt_more_series = findViewById(R.id.txt_more_series);
        txt_more_popular_movie = findViewById(R.id.txt_more_popular_movie);
        txt_more_animation = findViewById(R.id.txt_more_new_animation);


        txt_more_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, GenreActivity.class));

            }
        });

        txt_more_top_movie_imdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CATEGORY_NAME = "top_movie_imdb";

                startActivity(new Intent(HomeActivity.this, TopMovieIMDbActivity.class));


            }
        });


        txt_more_new_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        txt_more_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CATEGORY_NAME = "series";

                startActivity(new Intent(HomeActivity.this, SeriesActivity.class));

            }
        });

        txt_more_popular_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        txt_more_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    private void showDrawerLayout() {

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btn_menu = findViewById(R.id.btn_menu);
        parent = findViewById(R.id.parent);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home); //default checked item

        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                float changeScaleOffset = slideOffset * (1 - 0.7f);
                float OFFSET_SCALE = 1 - changeScaleOffset;

                parent.setScaleX(OFFSET_SCALE);
                parent.setScaleY(OFFSET_SCALE);

                float X_OFFSET_SET = drawerView.getWidth() * slideOffset;
                float X_OFFSET_CHANGE = parent.getWidth() * changeScaleOffset;
                float X_TRANSLATION = X_OFFSET_SET - X_OFFSET_CHANGE;
                parent.setTranslationX(X_TRANSLATION);

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    public void getSlider() {
        slider_home = findViewById(R.id.slider);
        tabs = findViewById(R.id.tab_layout);

//        adapterSlider = new AdapterSlider(HomeActivity.this, listSlider);
        global.getSlider(HomeActivity.this, requestQueue, "", slider_home, listSlider);

//        slider_home.setAdapter(adapterSlider);
        tabs.setupWithViewPager(slider_home, true);

        TimeSlider timeSlider = new TimeSlider();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timeSlider, 3000, 3000);
    }

    private void getTopMovieIMDb() {

        recyclerView_topMovieIMDb = findViewById(R.id.recycler_viewer_top_movie_imdb);
        recyclerView_topMovieIMDb.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_topMovieIMDb.setHasFixedSize(true);

        global.getTopMovieIMDb(HomeActivity.this, requestQueue, "top_movie_imdb", recyclerView_topMovieIMDb, listTopMovieIMDb);

    }

    private void getNewMovie() {

        recyclerView_newMovie = findViewById(R.id.recycler_viewer_new_movie);
        recyclerView_newMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_newMovie.setHasFixedSize(true);

        global.getNewMovie(HomeActivity.this, requestQueue, "movie_new", recyclerView_newMovie, listNewMovie);

    }

    private void getSeries() {

        recyclerView_series = findViewById(R.id.recycler_viewer_series);
        recyclerView_series.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_series.setHasFixedSize(true);

        global.getSeries(HomeActivity.this, requestQueue, "series", recyclerView_series, listSeries);

    }


    private void getPopularMovie() {

        recyclerView_popular_movie = findViewById(R.id.recycler_viewer_popular_movie);
        recyclerView_popular_movie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_popular_movie.setHasFixedSize(true);

        global.getPopularMovie(HomeActivity.this, requestQueue, "popular_movie", recyclerView_popular_movie, listPopularMovie);

    }


    private void getAnimation() {

        recyclerView_animation = findViewById(R.id.recycler_viewer_animation);
        recyclerView_animation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_animation.setHasFixedSize(true);

        global.getAnimation(HomeActivity.this, requestQueue, "animation", recyclerView_animation, listAnimation);

    }


    private void getGenre() {

        recyclerView_genre = findViewById(R.id.recycler_viewer_genre);
        recyclerView_genre.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_genre.setHasFixedSize(true);

        global.getGenre(HomeActivity.this, requestQueue, "", recyclerView_genre, listGenre);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:

                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_genre:

                startActivity(new Intent(HomeActivity.this, GenreActivity.class));
                break;

            case R.id.nav_search:

                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                break;

            case R.id.nav_favorite:

                startActivity(new Intent(HomeActivity.this, FavoriteActivity.class));
                break;

            case R.id.nav_buy_account:
                startActivity(new Intent(HomeActivity.this, BuyAccountActivity.class));
                break;

            case R.id.nav_profile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;

        }


        return true;
    }

    public class TimeSlider extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (slider_home.getCurrentItem() < listSlider.size() - 1) {
                        slider_home.setCurrentItem(slider_home.getCurrentItem() + 1);
                    } else {
                        slider_home.setCurrentItem(0);
                    }

                }
            });

        }
    }
}
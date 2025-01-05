package com.example.moviestreaming.Global;

import android.content.Context;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Adapter.AdapterAnimation;
import com.example.moviestreaming.Adapter.AdapterCast;
import com.example.moviestreaming.Adapter.AdapterComment;
import com.example.moviestreaming.Adapter.AdapterEpisode;
import com.example.moviestreaming.Adapter.AdapterGenre;
import com.example.moviestreaming.Adapter.AdapterGenreComplete;
import com.example.moviestreaming.Adapter.AdapterIntro;
import com.example.moviestreaming.Adapter.AdapterNewMovie;
import com.example.moviestreaming.Adapter.AdapterPopularMovie;
import com.example.moviestreaming.Adapter.AdapterRandom;
import com.example.moviestreaming.Adapter.AdapterRandomSeries;
import com.example.moviestreaming.Adapter.AdapterSearch;
import com.example.moviestreaming.Adapter.AdapterSeason;
import com.example.moviestreaming.Adapter.AdapterSeries;
import com.example.moviestreaming.Adapter.AdapterSeriesComplete;
import com.example.moviestreaming.Adapter.AdapterShowGenre;
import com.example.moviestreaming.Adapter.AdapterSlider;
import com.example.moviestreaming.Adapter.AdapterTopMovieIMDb;
import com.example.moviestreaming.Adapter.AdapterTopMovieIMDbComplete;
import com.example.moviestreaming.Database.DataSource.FavoriteRepository;
import com.example.moviestreaming.Database.Local.RoomDatabaseApp;
import com.example.moviestreaming.HomeActivity;
import com.example.moviestreaming.Model.AllInformation;
import com.example.moviestreaming.Model.Animation;
import com.example.moviestreaming.Model.Cast;
import com.example.moviestreaming.Model.Comment;
import com.example.moviestreaming.Model.Episode;
import com.example.moviestreaming.Model.Genre;
import com.example.moviestreaming.Model.Intro;
import com.example.moviestreaming.Model.NewMovie;
import com.example.moviestreaming.Model.PopularMovie;
import com.example.moviestreaming.Model.Season;
import com.example.moviestreaming.Model.Series;
import com.example.moviestreaming.Model.ShowGenre;
import com.example.moviestreaming.Model.Slider;
import com.example.moviestreaming.Model.TopMovieIMDb;
import com.example.moviestreaming.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Global {

    public static FavoriteRepository favoriteRepository;
    public static RoomDatabaseApp roomDatabaseApp;


    String link;
    RequestQueue requestQueue;

    List<Slider> listSlider = new ArrayList<>();
    AdapterSlider adapterSlider;

    //Top Movie IMDb
    List<TopMovieIMDb> listTopMovieIMDb = new ArrayList<>();
    AdapterTopMovieIMDb adapterTopMovieIMDb;
    String link_home_information;

    //New Movie
    List<NewMovie> listNewMovie = new ArrayList<>();
    AdapterNewMovie adapterNewMovie;

    //Series
    List<Series> listSeries = new ArrayList<>();
    AdapterSeries adapterSeries;

    //Popular Movie
    List<PopularMovie> listPopularMovies = new ArrayList<>();
    AdapterPopularMovie adapterPopularMovie;

    //Animation
    List<Animation> listAnimation = new ArrayList<>();
    AdapterAnimation adapterAnimation;

    //Genre
    List<Genre> listGenre = new ArrayList<>();
    AdapterGenre adapterGenre;
    String link_genre;

    //Genre Complete
    List<Genre> listGenreComplete = new ArrayList<>();
    AdapterGenreComplete adapterGenreComplete;


    //Top Movie IMDb Complete
    List<TopMovieIMDb> listTopMovieIMdbComplete = new ArrayList<>();
    AdapterTopMovieIMDbComplete adapterTopMovieIMdbComplete;
    String link_home_all_information;


    //Series Complete
    AdapterSeriesComplete adapterSeriesComplete;
    List<Series> list_series_complete = new ArrayList<>();


    //Cast
    AdapterCast adapterCast;
    List<Cast> listCast = new ArrayList<>();
    String link_cast;


    //Season
    AdapterSeason adapter_season;
    List<Season> list_season = new ArrayList<>();
    String link_season;


    //Episode
    AdapterEpisode adapter_episode;
    List<Episode> list_episode;
    String link_episode;


    //Random
    AdapterRandom adapter_random;
    List<AllInformation> list_all_information = new ArrayList<>();
    String link_similar;


    //RandomSeries
    AdapterRandomSeries adapter_random_series;
    List<AllInformation> list_all_information_series = new ArrayList<>();


    //ShowGenre
    AdapterShowGenre adapterShowGenre;
    List<ShowGenre> list_show_genre = new ArrayList<>();
    String link_show_genre;

    //Get Search
    AdapterSearch adapter_search;
    List<AllInformation> list_search = new ArrayList<>();
    String link_search;


    //Intro
    AdapterIntro intro_adapter;
    List<Intro> list_intro = new ArrayList<>();
    String link_intro;

    //Comment
    AdapterComment adapter_comment;
    List<Comment> list_comment = new ArrayList<>();
    String link_get_comment;

    public void getSlider(Context context, RequestQueue requestQueue, String url, ViewPager viewPager, List<Slider> list) {

        link = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getSlider.php";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link + url;
        this.listSlider = list;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                link,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("slider");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");

                                Slider slider = new Slider();

                                slider.setId(id);
                                slider.setName(name);
                                slider.setLink_img(link_img);
                                slider.setTime(time);
                                slider.setPublished(published);

                                listSlider.add(slider);

//                                getApplicationContext()

                            }

                            adapterSlider = new AdapterSlider(context, listSlider);
                            viewPager.setAdapter(adapterSlider);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getTopMovieIMDb(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<TopMovieIMDb> list) {

        link_home_information = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_home_information + url;
        this.listTopMovieIMDb = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String rank = jsonObject.getString("rank");
                                String category_name = jsonObject.getString("category_name");
                                String genre = jsonObject.getString("genre");


                                TopMovieIMDb topMovieIMDb = new TopMovieIMDb();

                                topMovieIMDb.setId(id);
                                topMovieIMDb.setName(name);
                                topMovieIMDb.setLink_img(link_img);
                                topMovieIMDb.setTime(time);
                                topMovieIMDb.setPublished(published);
                                topMovieIMDb.setRate_imdb(rate_imdb);
                                topMovieIMDb.setDirector(director);
                                topMovieIMDb.setRank(rank);
                                topMovieIMDb.setCategory_name(category_name);
                                topMovieIMDb.setGenre(genre);

                                listTopMovieIMDb.add(topMovieIMDb);

//                                getApplicationContext()

                            }

                            adapterTopMovieIMDb = new AdapterTopMovieIMDb(context, listTopMovieIMDb);
                            recyclerView.setAdapter(adapterTopMovieIMDb);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getNewMovie(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<NewMovie> list) {

        link_home_information = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_home_information + url;
        this.listNewMovie = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String category_name = jsonObject.getString("category_name");

                                NewMovie newMovie = new NewMovie();

                                newMovie.setId(id);
                                newMovie.setName(name);
                                newMovie.setLink_img(link_img);
                                newMovie.setTime(time);
                                newMovie.setPublished(published);
                                newMovie.setRate_imdb(rate_imdb);
                                newMovie.setDirector(director);
                                newMovie.setCategory_name(category_name);

                                listNewMovie.add(newMovie);

//                                getApplicationContext()

                            }

                            adapterNewMovie = new AdapterNewMovie(context, listNewMovie);
                            recyclerView.setAdapter(adapterNewMovie);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getSeries(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Series> list) {

        link_home_information = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_home_information + url;
        this.listSeries = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String category_name = jsonObject.getString("category_name");

                                Series series = new Series();

                                series.setId(id);
                                series.setName(name);
                                series.setLink_img(link_img);
                                series.setTime(time);
                                series.setPublished(published);
                                series.setRate_imdb(rate_imdb);
                                series.setDirector(director);
                                series.setCategory_name(category_name);

                                listSeries.add(series);

//                                getApplicationContext()

                            }

                            adapterSeries = new AdapterSeries(context, listSeries);
                            recyclerView.setAdapter(adapterSeries);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getPopularMovie(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<PopularMovie> list) {

        link_home_information = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_home_information + url;
        this.listPopularMovies = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String category_name = jsonObject.getString("category_name");

                                PopularMovie popularMovie = new PopularMovie();

                                popularMovie.setId(id);
                                popularMovie.setName(name);
                                popularMovie.setLink_img(link_img);
                                popularMovie.setTime(time);
                                popularMovie.setPublished(published);
                                popularMovie.setRate_imdb(rate_imdb);
                                popularMovie.setDirector(director);
                                popularMovie.setCategory_name(category_name);

                                listPopularMovies.add(popularMovie);

//                                getApplicationContext()

                            }

                            adapterPopularMovie = new AdapterPopularMovie(context, listPopularMovies);
                            recyclerView.setAdapter(adapterPopularMovie);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getAnimation(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Animation> list) {

        link_home_information = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_home_information + url;
        this.listAnimation = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String category_name = jsonObject.getString("category_name");

                                Animation animation = new Animation();

                                animation.setId(id);
                                animation.setName(name);
                                animation.setLink_img(link_img);
                                animation.setTime(time);
                                animation.setPublished(published);
                                animation.setRate_imdb(rate_imdb);
                                animation.setDirector(director);
                                animation.setCategory_name(category_name);

                                listAnimation.add(animation);

//                                getApplicationContext()

                            }

                            adapterAnimation = new AdapterAnimation(context, listAnimation);
                            recyclerView.setAdapter(adapterAnimation);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getGenre(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Genre> list) {


        link_genre = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getGenre.php";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_genre + url;
        this.listGenre = list;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                LINK,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("movie_streaming");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");

                                Genre genre = new Genre();

                                genre.setId(id);
                                genre.setName(name);
                                genre.setLink_img(link_img);

                                listGenre.add(genre);

//                                getApplicationContext()

                            }

                            adapterGenre = new AdapterGenre(context, listGenre);
                            recyclerView.setAdapter(adapterGenre);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getGenreComplete(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Genre> list) {

        link_genre = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getGenre.php";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_genre + url;
        this.listGenreComplete = list;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                LINK,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("movie_streaming");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");

                                Genre genre_complete = new Genre();

                                genre_complete.setId(id);
                                genre_complete.setName(name);
                                genre_complete.setLink_img(link_img);

                                listGenreComplete.add(genre_complete);

//                                getApplicationContext()

                            }

                            adapterGenreComplete = new AdapterGenreComplete(context, listGenreComplete);
                            recyclerView.setAdapter(adapterGenreComplete);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getTopMovieIMDbComplete(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<TopMovieIMDb> list) {

        link_home_all_information = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getAllInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_home_all_information + url;
        this.listTopMovieIMdbComplete = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String rank = jsonObject.getString("rank");
                                String category_name = jsonObject.getString("category_name");
                                String genre = jsonObject.getString("genre");

                                TopMovieIMDb topMovieIMDbComplete = new TopMovieIMDb();

                                topMovieIMDbComplete.setId(id);
                                topMovieIMDbComplete.setName(name);
                                topMovieIMDbComplete.setLink_img(link_img);
                                topMovieIMDbComplete.setTime(time);
                                topMovieIMDbComplete.setPublished(published);
                                topMovieIMDbComplete.setRate_imdb(rate_imdb);
                                topMovieIMDbComplete.setDirector(director);
                                topMovieIMDbComplete.setRank(rank);
                                topMovieIMDbComplete.setCategory_name(category_name);
                                topMovieIMDbComplete.setGenre(genre);

                                listTopMovieIMdbComplete.add(topMovieIMDbComplete);

//                                getApplicationContext()

                            }

                            adapterTopMovieIMdbComplete = new AdapterTopMovieIMDbComplete(context, listTopMovieIMdbComplete);
                            recyclerView.setAdapter(adapterTopMovieIMdbComplete);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getSeriesComplete(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Series> list) {

        link_home_all_information = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getAllInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_home_all_information + url;
        this.list_series_complete = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String category_name = jsonObject.getString("category_name");

                                Series series = new Series();

                                series.setId(id);
                                series.setName(name);
                                series.setLink_img(link_img);
                                series.setTime(time);
                                series.setPublished(published);
                                series.setRate_imdb(rate_imdb);
                                series.setDirector(director);
                                series.setCategory_name(category_name);

                                list_series_complete.add(series);

//                                getApplicationContext()

                            }

                            adapterSeriesComplete = new AdapterSeriesComplete(context, list_series_complete);
                            recyclerView.setAdapter(adapterSeriesComplete);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void getCast(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Cast> list) {

        link_cast = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getCast.php?id_item=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_cast + url;
        this.listCast = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String id_item = jsonObject.getString("id_item");

                                Cast cast = new Cast();

                                cast.setId(id);
                                cast.setName(name);
                                cast.setLink_img(link_img);
                                cast.setId_item(id_item);

                                listCast.add(cast);

//                                getApplicationContext()

                            }

                            adapterCast = new AdapterCast(context, listCast);
                            recyclerView.setAdapter(adapterCast);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    public void getSeason(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Season> list) {

        link_season = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getSeason.php?id_item=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_season + url;
        this.list_season = list;

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
                                String id_item = jsonObject.getString("id_item");
                                String link_img = jsonObject.getString("link_img");
                                String count_episode = jsonObject.getString("count_episode");
                                String number_season = jsonObject.getString("number_season");

                                Season season = new Season();

                                season.setId(id);
                                season.setId_item(id_item);
                                season.setLink_img(link_img);
                                season.setCount_episode(count_episode);
                                season.setNumber_season(number_season);

                                list_season.add(season);

//                                getApplicationContext()

                            }

                            adapter_season = new AdapterSeason(list_season, context);
                            recyclerView.setAdapter(adapter_season);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    public void getEpisode(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Episode> list) {

        link_episode = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getEpisode.php?id_season=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_episode + url;
        this.list_episode = list;

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
                                String id_season = jsonObject.getString("id_season");
                                String name = jsonObject.getString("name");
                                String detail = jsonObject.getString("detail");
                                String time = jsonObject.getString("time");
                                String link_img = jsonObject.getString("link_img");
                                String link_play_episode = jsonObject.getString("link_play_episode");

                                Episode episode = new Episode();

                                episode.setId(id);
                                episode.setId_season(id_season);
                                episode.setLink_img(link_img);
                                episode.setName(name);
                                episode.setDetail(detail);
                                episode.setTime(time);
                                episode.setLink_play_episode(link_play_episode);

                                list_episode.add(episode);

//                                getApplicationContext()

                            }

                            adapter_episode = new AdapterEpisode(context, list_episode);
                            recyclerView.setAdapter(adapter_episode);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    public void getSimilar(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<AllInformation> list) {

        link_similar = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getAllInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_similar + url;
        this.list_all_information = list;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                LINK,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("movie_streaming");

                            double rand_num = Math.random() * jsonArray.length();
                            int rand = (int) rand_num;


                            if (rand < 4) rand = 4;
                            if (rand >= jsonArray.length()) rand = jsonArray.length();


                            for (int i = rand - 4; i < rand; i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String category_name = jsonObject.getString("category_name");
                                String genre = jsonObject.getString("genre");

                                AllInformation allInformation = new AllInformation();

                                allInformation.setId(id);
                                allInformation.setName(name);
                                allInformation.setLink_img(link_img);
                                allInformation.setTime(time);
                                allInformation.setPublished(published);
                                allInformation.setRate_imdb(rate_imdb);
                                allInformation.setDirector(director);
                                allInformation.setCategory_name(category_name);
                                allInformation.setGenre(genre);

                                list_all_information.add(allInformation);

//                                getApplicationContext()

                            }

                            adapter_random = new AdapterRandom(context, list_all_information);
                            recyclerView.setAdapter(adapter_random);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    public void getSimilarSeries(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<AllInformation> list) {

        link_similar = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getAllInformationHome.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_similar + url;
        this.list_all_information_series = list;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                LINK,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("movie_streaming");

                            double rand_num = Math.random() * jsonArray.length();
                            int rand = (int) rand_num;


                            if (rand < 4) rand = 4;
                            if (rand >= jsonArray.length()) rand = jsonArray.length();


                            for (int i = rand - 4; i < rand; i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String category_name = jsonObject.getString("category_name");
                                String genre = jsonObject.getString("genre");

                                AllInformation allInformation = new AllInformation();

                                allInformation.setId(id);
                                allInformation.setName(name);
                                allInformation.setLink_img(link_img);
                                allInformation.setTime(time);
                                allInformation.setPublished(published);
                                allInformation.setRate_imdb(rate_imdb);
                                allInformation.setDirector(director);
                                allInformation.setCategory_name(category_name);
                                allInformation.setGenre(genre);

                                list_all_information_series.add(allInformation);

//                                getApplicationContext()

                            }

                            adapter_random_series = new AdapterRandomSeries(context, list_all_information_series);
                            recyclerView.setAdapter(adapter_random_series);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    public void getShowGenre(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<ShowGenre> list) {

        link_show_genre = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getShowGenre.php?genre=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_show_genre + url;
        this.list_show_genre = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String rank = jsonObject.getString("rank");
                                String category_name = jsonObject.getString("category_name");
                                String genre = jsonObject.getString("genre");

                                ShowGenre showGenre = new ShowGenre();

                                showGenre.setId(id);
                                showGenre.setName(name);
                                showGenre.setLink_img(link_img);
                                showGenre.setTime(time);
                                showGenre.setPublished(published);
                                showGenre.setRate_imdb(rate_imdb);
                                showGenre.setDirector(director);
                                showGenre.setRank(rank);
                                showGenre.setCategory_name(category_name);
                                showGenre.setGenre(genre);

                                list_show_genre.add(showGenre);

//                                getApplicationContext()

                            }

                            adapterShowGenre = new AdapterShowGenre(context, list_show_genre);
                            recyclerView.setAdapter(adapterShowGenre);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    public void getSearch(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<AllInformation> list) {

        link_search = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getSearch.php?category_name=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_search + url;
        this.list_search = list;

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
                                String name = jsonObject.getString("name");
                                String link_img = jsonObject.getString("link_img");
                                String time = jsonObject.getString("time");
                                String published = jsonObject.getString("published");
                                String rate_imdb = jsonObject.getString("rate_imdb");
                                String director = jsonObject.getString("director");
                                String rank = jsonObject.getString("rank");
                                String category_name = jsonObject.getString("category_name");
                                String genre = jsonObject.getString("genre");

                                AllInformation all_information = new AllInformation();

                                all_information.setId(id);
                                all_information.setName(name);
                                all_information.setLink_img(link_img);
                                all_information.setTime(time);
                                all_information.setPublished(published);
                                all_information.setRate_imdb(rate_imdb);
                                all_information.setDirector(director);
                                all_information.setRank(rank);
                                all_information.setCategory_name(category_name);
                                all_information.setGenre(genre);

                                list_search.add(all_information);

//                                getApplicationContext()

                            }

                            adapter_search = new AdapterSearch(context, list_search);
                            recyclerView.setAdapter(adapter_search);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    public void getIntro(Context context, RequestQueue requestQueue, String url, ViewPager viewPager, List<Intro> list) {

        link_intro = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/getIntro.php";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_intro + url;
        this.list_intro = list;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                LINK,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("movie_streaming");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String dscription = jsonObject.getString("description");
                                String link_img = jsonObject.getString("link_img");

                                Intro intro = new Intro();

                                intro.setId(id);
                                intro.setDescription(dscription);
                                intro.setLink_img(link_img);

                                list_intro.add(intro);

//                                getApplicationContext()

                            }

                            intro_adapter = new AdapterIntro(context, list_intro);
                            viewPager.setAdapter(intro_adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


    public void getComment(Context context, RequestQueue requestQueue, String url, RecyclerView recyclerView, List<Comment> list) {

        link_get_comment = "http://" + context.getString(R.string.CurrentIP) + "/moviestreaming/get_comment.php?id_item=";

        this.requestQueue = requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String LINK = link_get_comment + url;
        this.list_comment = list;

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

                                String email = jsonObject.getString("email");
                                String comment = jsonObject.getString("comment");

                                Comment comment1 = new Comment();

                                comment1.setEmail(email);
                                comment1.setComment(comment);

                                list_comment.add(comment1);

                            }

                            adapter_comment = new AdapterComment(context, list_comment);
                            recyclerView.setAdapter(adapter_comment);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


}

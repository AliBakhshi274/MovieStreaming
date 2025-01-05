package com.example.moviestreaming;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailMovieActivity extends AppCompatActivity {

    public static String ID_DETAIL_ITEM = "";
    public static String CATEGORY_NAME = "CATEGORY_TEST";
    Bundle bundle;

    int id;
    String category_name;

    TextView movie_name, movie_director, movie_rate_imdb, movie_time, movie_published, movie_description, movie_genre;
    ImageView movie_image, img_back, img_download, img_comment;
    RecyclerView recyclerview_cast, recyclerview_similar;
    FloatingActionButton btn_play;

    Global global;
    List<Cast> list_cast = new ArrayList<>();

    RequestQueue requestQueue;
    String link_show_detail;

    String link_movie = "";

    //Random
    List<AllInformation> list_all_information = new ArrayList<>();


    //Subscription
    SharedPreferences sharedPreferences;
    long subscription_time;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_movie);

        link_show_detail = "http://" + this.getString(R.string.CurrentIP) + "/moviestreaming/show_detail.php?id_item=";
        findAll();


        bundle = getIntent().getExtras();
        id = Integer.parseInt(bundle.getString(ID_DETAIL_ITEM));
        category_name = bundle.getString(CATEGORY_NAME);


//        Toast.makeText(this, id + "", Toast.LENGTH_SHORT).show();

        //SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        subscription_time = sharedPreferences.getLong("subscription", 0);


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
                                link_movie = jsonObject.getString("link_movie");

                                Picasso.get().load(link_img).into(movie_image);
                                movie_description.setText(description);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowDetailMovieActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);

        movie_name.setText(bundle.getString("name"));
        movie_time.setText(bundle.getString("time"));
        movie_director.setText("Director: " + bundle.getString("director"));
        movie_published.setText("Published: " + bundle.getString("published"));
        movie_rate_imdb.setText("IMDb: " + bundle.getString("rate_imdb"));
        movie_genre.setText(bundle.getString("genre"));


        recyclerview_cast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerview_cast.setHasFixedSize(true);

        global = new Global();

        global.getCast(this, requestQueue, String.valueOf(id), recyclerview_cast, list_cast);


        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subscription_time = subscription_time - System.currentTimeMillis();
                subscription_time = subscription_time / 24;
                subscription_time = subscription_time / 60;
                subscription_time = subscription_time / 60;
                subscription_time = subscription_time / 1000;

                if (subscription_time > 0) {

                    Intent intent = new Intent(ShowDetailMovieActivity.this, VideoPlayActivity.class);
                    intent.putExtra("link_movie", link_movie);
                    startActivity(intent);


                } else {

                    alertShowBuyAccount();

                }

            }
        });


        //Random
        recyclerview_similar.setHasFixedSize(true);
        recyclerview_similar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        global.getSimilar(this, requestQueue, category_name, recyclerview_similar, list_all_information);

        img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(ShowDetailMovieActivity.this)
                        .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                                if (multiplePermissionsReport.areAllPermissionsGranted()) {

                                    showAlertDialogDownloadMovie();

                                }

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                            }
                        }).check();

            }
        });


        img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowDetailMovieActivity.this, CommentActivity.class);
                intent.putExtra(CommentActivity.ID_ITEM_FOR_COMMENT, bundle.getString(ID_DETAIL_ITEM));
                startActivity(intent);


            }
        });

    }

    private void showAlertDialogDownloadMovie() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailMovieActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_show_download, null);
        builder.setView(view);

        CardView card720 = view.findViewById(R.id.card_quality_720);
        CardView card1080 = view.findViewById(R.id.card_quality_1080);

        card720.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downlaod_movie();
                alertDialog.dismiss();

            }
        });


        card1080.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downlaod_movie();
                alertDialog.dismiss();

            }
        });


        alertDialog = builder.create();
        alertDialog.show();

    }

    private void alertShowBuyAccount() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowDetailMovieActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.show_buy_account, null);
        builder.setView(view);

        TextView txt_alert_no = view.findViewById(R.id.txt_no);
        TextView txt_alert_yes = view.findViewById(R.id.txt_yes);


        txt_alert_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        txt_alert_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowDetailMovieActivity.this, BuyAccountActivity.class));
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }

    private void downlaod_movie() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link_movie));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalFilesDir(ShowDetailMovieActivity.this, Environment.getExternalStorageState() + "Movie Streaming", "");

        DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }


    private void findAll() {

        movie_name = findViewById(R.id.detail_movie_name);
        movie_director = findViewById(R.id.detail_movie_director);
        movie_rate_imdb = findViewById(R.id.movie_imdb_rate);
        movie_time = findViewById(R.id.detail_movie_time);
        movie_published = findViewById(R.id.detail_movie_published);
        movie_description = findViewById(R.id.detail_movie_text);
        movie_genre = findViewById(R.id.detail_movie_genre);

        movie_image = findViewById(R.id.detail_movie_img_video);
        img_back = findViewById(R.id.detail_movie_img_back);
        img_download = findViewById(R.id.detail_movie_img_download);
        img_comment = findViewById(R.id.detail_movie_img_comment);

        btn_play = findViewById(R.id.detail_movie_fab_play);

        recyclerview_cast = findViewById(R.id.detail_movie_cast_recycler);
        recyclerview_similar = findViewById(R.id.detail_movie_similar_recycler);

    }
}
package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.halilibo.bettervideoplayer.BetterVideoPlayer;

public class VideoPlayActivity extends AppCompatActivity {

    BetterVideoPlayer videoPlayer;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_p_l_ay);

        videoPlayer = findViewById(R.id.video_player);

        bundle = getIntent().getExtras();

        videoPlayer.setSource(Uri.parse(bundle.getString("link_movie")));
        videoPlayer.setAutoPlay(true);
        videoPlayer.start();

    }
}
package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.AndroidException;

import com.example.moviestreaming.Adapter.AdapterFavorite;
import com.example.moviestreaming.Database.ModelDB.Favorite;
import com.example.moviestreaming.Global.Global;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerview_fav;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        compositeDisposable = new CompositeDisposable();

        recyclerview_fav = findViewById(R.id.recycler_viewer_favorite);
        recyclerview_fav.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_fav.setHasFixedSize(true);

        compositeDisposable.add(Global.favoriteRepository.getAllFavoriteItem()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Favorite>>() {
            @Override
            public void accept(@NonNull List<Favorite> favorites) throws Exception {

                AdapterFavorite adapterFavorite = new AdapterFavorite(getApplicationContext(), favorites);
                recyclerview_fav.setAdapter(adapterFavorite);

            }
        }));


    }
}
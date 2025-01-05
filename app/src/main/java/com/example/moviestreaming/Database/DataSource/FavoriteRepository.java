package com.example.moviestreaming.Database.DataSource;

import com.example.moviestreaming.Database.ModelDB.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public class FavoriteRepository implements IFavoriteDataSource {

    private IFavoriteDataSource iFavoriteDataSource;
    public static FavoriteRepository instance;


    public FavoriteRepository(IFavoriteDataSource iFavoriteDataSource) {
        this.iFavoriteDataSource = iFavoriteDataSource;
    }

    public static FavoriteRepository getInstance(IFavoriteDataSource iFavoriteDataSource) {

        if (instance == null) {
            return new FavoriteRepository(iFavoriteDataSource);
        }
        return instance;

    }


    @Override
    public Flowable<List<Favorite>> getAllFavoriteItem() {
        return iFavoriteDataSource.getAllFavoriteItem();
    }

    @Override
    public void insertFavoriteItem(Favorite... favorites) {
        iFavoriteDataSource.insertFavoriteItem(favorites);
    }

    @Override
    public void deleteFavoriteItem(Favorite favorite) {
        iFavoriteDataSource.deleteFavoriteItem(favorite);
    }

    @Override
    public int isFavorite(int itemFavID) {
        int happ = iFavoriteDataSource.isFavorite(itemFavID);

        System.out.println(happ);

        return happ;
    }
}

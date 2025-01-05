package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreaming.Database.ModelDB.Favorite;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Interface.ItemClickListener;
import com.example.moviestreaming.Model.TopMovieIMDb;
import com.example.moviestreaming.R;
import com.example.moviestreaming.ShowDetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTopMovieIMDbComplete extends RecyclerView.Adapter<AdapterTopMovieIMDbComplete.MyViewholder> {

    Context context;
    List<TopMovieIMDb> data;


    public AdapterTopMovieIMDbComplete(Context context, List<TopMovieIMDb> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_top_movie_imdb_complete, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.time.setText(data.get(position).getTime());
        holder.director.setText(new StringBuilder("Director:") + data.get(position).getDirector());
        holder.published.setText(new StringBuilder("Published:") + data.get(position).getPublished());
        holder.rate_imdb.setText(new StringBuilder("IMDb:") + data.get(position).getRate_imdb());
        holder.rank_movie.setText(new StringBuilder("Rank:") + data.get(position).getRank());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_top_movie);

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ShowDetailMovieActivity.class);
                intent.putExtra(ShowDetailMovieActivity.ID_DETAIL_ITEM, data.get(position).getId());
                intent.putExtra(ShowDetailMovieActivity.CATEGORY_NAME, data.get(position).getCategory_name());
                intent.putExtra("name", data.get(position).getName());
                intent.putExtra("director", data.get(position).getDirector());
                intent.putExtra("time", data.get(position).getTime());
                intent.putExtra("published", data.get(position).getPublished());
                intent.putExtra("rate_imdb", data.get(position).getRate_imdb());
                intent.putExtra("genre", data.get(position).getGenre());

                context.startActivity(intent);

            }
        });


        holder.rank_movie.setText(new StringBuilder("Rank:") + data.get(position).getRank());



        //Before Click
        if (Global.favoriteRepository.isFavorite(Integer.parseInt(data.get(position).getId()))==1){
            holder.img_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else{
            holder.img_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }


        holder.img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Global.favoriteRepository.isFavorite(Integer.parseInt(data.get(position).getId()))!=1){

                    holder.img_fav.setImageResource(R.drawable.ic_baseline_favorite_24);

                    Favorite favorite = new Favorite();

                    favorite.id = data.get(position).getId();
                    favorite.published = data.get(position).getPublished();
                    favorite.genre = data.get(position).getGenre();
                    favorite.director = data.get(position).getDirector();
                    favorite.link_img = data.get(position).getLink_img();
                    favorite.name = data.get(position).getName();
                    favorite.rate_imdb = data.get(position).getRate_imdb();
                    favorite.category_name = data.get(position).getCategory_name();
                    favorite.time = data.get(position).getTime();

                    Global.favoriteRepository.insertFavoriteItem(favorite);


                }else{

                    holder.img_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);


                    Favorite favorite = new Favorite();

                    favorite.id = data.get(position).getId();
                    favorite.published = data.get(position).getPublished();
                    favorite.genre = data.get(position).getGenre();
                    favorite.director = data.get(position).getDirector();
                    favorite.link_img = data.get(position).getLink_img();
                    favorite.name = data.get(position).getName();
                    favorite.rate_imdb = data.get(position).getRate_imdb();
                    favorite.category_name = data.get(position).getCategory_name();
                    favorite.time = data.get(position).getTime();

                    Global.favoriteRepository.deleteFavoriteItem(favorite);





                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView time, name, rank_movie, rate_imdb, director, published;
        ImageView img_top_movie, img_fav;

        ItemClickListener listener;


        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }


        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time_movie_imdb);
            name = itemView.findViewById(R.id.movie_name);
            rank_movie = itemView.findViewById(R.id.rank_movie);
            rate_imdb = itemView.findViewById(R.id.movie_imdb_rate);
            director = itemView.findViewById(R.id.movie_director);
            published = itemView.findViewById(R.id.movie_published);
            img_top_movie = itemView.findViewById(R.id.movie_img);
            img_fav = itemView.findViewById(R.id.item_fav);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            listener.onClick(v);
        }
    }
}

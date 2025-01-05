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

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyViewholder> {

    Context context;
    List<Favorite> data;


    public AdapterFavorite(Context context, List<Favorite> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {


        holder.name.setText(data.get(position).name);
        holder.time.setText(data.get(position).time);
        holder.director.setText(new StringBuilder("Director:") + data.get(position).director);
        holder.published.setText(new StringBuilder("Published:") + data.get(position).published);
        holder.rate_imdb.setText(new StringBuilder("IMDb:") + data.get(position).rate_imdb);
        Picasso.get().load(data.get(position).link_img).into(holder.img_top_movie);



        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ShowDetailMovieActivity.class);
                intent.putExtra(ShowDetailMovieActivity.ID_DETAIL_ITEM, data.get(position).id);
                intent.putExtra(ShowDetailMovieActivity.CATEGORY_NAME, data.get(position).category_name);
                intent.putExtra("name", data.get(position).name);
                intent.putExtra("director", data.get(position).director);
                intent.putExtra("time", data.get(position).time);
                intent.putExtra("published", data.get(position).published);
                intent.putExtra("rate_imdb", data.get(position).rate_imdb);
                intent.putExtra("genre", data.get(position).genre);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView time, name, rate_imdb, director, published;
        ImageView img_top_movie, img_fav;

        ItemClickListener listener;


        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }


        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time_movie_imdb);
            name = itemView.findViewById(R.id.movie_name);
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

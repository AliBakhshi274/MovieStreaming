package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreaming.Model.NewMovie;
import com.example.moviestreaming.Model.PopularMovie;
import com.example.moviestreaming.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPopularMovie extends RecyclerView.Adapter<AdapterPopularMovie.MyViewholder> {

    Context context;
    List<PopularMovie> data;


    public AdapterPopularMovie(Context context, List<PopularMovie> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_popular_movie, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.time.setText(data.get(position).getTime());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_top_movie);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView time, name;
        ImageView img_top_movie;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time_movie_imdb);
            name = itemView.findViewById(R.id.name_top_movie_imdb);
            img_top_movie = itemView.findViewById(R.id.img_top_movie_imdb);

        }
    }
}

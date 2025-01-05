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

import com.example.moviestreaming.Interface.ItemClickListener;
import com.example.moviestreaming.Model.Series;
import com.example.moviestreaming.Model.ShowGenre;
import com.example.moviestreaming.R;
import com.example.moviestreaming.ShowDetailSeriesActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterShowGenre extends RecyclerView.Adapter<AdapterShowGenre.MyViewholder> {

    Context context;
    List<ShowGenre> data;


    public AdapterShowGenre(Context context, List<ShowGenre> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_top_show_genre, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {


        holder.name.setText(data.get(position).getName());
        holder.time.setText(new StringBuilder("Seasons: ") + data.get(position).getTime());
        holder.director.setText(new StringBuilder("Director:") + data.get(position).getDirector());
        holder.published.setText(new StringBuilder("Episodes:") + data.get(position).getPublished());
        holder.rate_imdb.setText(new StringBuilder("IMDb:") + data.get(position).getRate_imdb());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_top_movie);


        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ShowDetailSeriesActivity.class);
                intent.putExtra("name", data.get(position).getName());
                intent.putExtra("director", data.get(position).getDirector());
                intent.putExtra(ShowDetailSeriesActivity.ID_DETAIL_ITEM, data.get(position).getId());
                intent.putExtra(ShowDetailSeriesActivity.CATEGORY_NAME, data.get(position).getCategory_name());
                intent.putExtra("episodes", data.get(position).getPublished());
                intent.putExtra("seasons", data.get(position).getTime());
                intent.putExtra("rate_imdb", data.get(position).getRate_imdb());
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
        ImageView img_top_movie;

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        ItemClickListener clickListener;


        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time_movie_imdb);
            name = itemView.findViewById(R.id.movie_name);
            rate_imdb = itemView.findViewById(R.id.movie_imdb_rate);
            director = itemView.findViewById(R.id.movie_director);
            published = itemView.findViewById(R.id.movie_published);
            img_top_movie = itemView.findViewById(R.id.movie_img);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v);
        }
    }
}

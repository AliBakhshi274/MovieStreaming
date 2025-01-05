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
import com.example.moviestreaming.Model.TopMovieIMDb;
import com.example.moviestreaming.R;
import com.example.moviestreaming.ShowDetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTopMovieIMDb extends RecyclerView.Adapter<AdapterTopMovieIMDb.MyViewholder> {

    Context context;
    List<TopMovieIMDb> data;


    public AdapterTopMovieIMDb(Context context, List<TopMovieIMDb> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_top_movie_imdb, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.time.setText(data.get(position).getTime());
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


        holder.rank_movie.setText(new StringBuilder("Rank:")+data.get(position).getRank());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView time, name, rank_movie;
        ImageView img_top_movie;

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        ItemClickListener listener;


        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time_movie_imdb);
            name = itemView.findViewById(R.id.name_top_movie_imdb);
            rank_movie = itemView.findViewById(R.id.rank_movie);
            img_top_movie = itemView.findViewById(R.id.img_top_movie_imdb);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            listener.onClick(v);

        }
    }
}

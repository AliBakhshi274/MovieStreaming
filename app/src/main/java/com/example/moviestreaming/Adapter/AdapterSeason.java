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

import com.example.moviestreaming.EpisodeActivity;
import com.example.moviestreaming.Interface.ItemClickListener;
import com.example.moviestreaming.Model.Season;
import com.example.moviestreaming.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSeason extends RecyclerView.Adapter<AdapterSeason.MyViewHolder> {

    Context context;
    List<Season> data;


    public AdapterSeason(List<Season> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_season, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.number_season.setText("Season: " + data.get(position).getNumber_season());
        holder.count_episode.setText("Episodes: " + data.get(position).getCount_episode());

        Picasso.get().load(data.get(position).getLink_img()).into(holder.img);

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EpisodeActivity.class);
                intent.putExtra(EpisodeActivity.ID_SEASON, data.get(position).getId());
                intent.putExtra("season_number", data.get(position).getNumber_season());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView count_episode, number_season;
        ImageView img;

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        ItemClickListener listener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            count_episode = itemView.findViewById(R.id.count_episode);
            number_season = itemView.findViewById(R.id.number_season);
            img = itemView.findViewById(R.id.img_season);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v);
        }
    }

}

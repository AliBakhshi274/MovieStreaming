package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreaming.Interface.ItemClickListener;
import com.example.moviestreaming.Model.Episode;
import com.example.moviestreaming.R;
import com.example.moviestreaming.VideoPlayActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterEpisode extends RecyclerView.Adapter<AdapterEpisode.MyViewHolder> {

    Context context;
    List<Episode> data;

    public AdapterEpisode(Context context, List<Episode> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_episode, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.detail.setText(data.get(position).getDetail());
        holder.time.setText(data.get(position).getTime());

        Picasso.get().load(data.get(position).getLink_img()).into(holder.image);

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("link_movie", data.get(position).getLink_play_episode());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, detail, time;
        ImageView image;

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        ItemClickListener listener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img_episode);

            detail = itemView.findViewById(R.id.detail_episode);
            time = itemView.findViewById(R.id.time_episode);
            name = itemView.findViewById(R.id.name_episode);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v);
        }
    }
}

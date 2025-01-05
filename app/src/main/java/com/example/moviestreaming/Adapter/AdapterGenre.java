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
import com.example.moviestreaming.Model.Genre;
import com.example.moviestreaming.R;
import com.example.moviestreaming.ShowGenreActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterGenre extends RecyclerView.Adapter<AdapterGenre.MyViewholder> {

    Context context;
    List<Genre> data;


    public AdapterGenre(Context context, List<Genre> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.name.setText(data.get(position).getName());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_top_movie);

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ShowGenreActivity.class);
                intent.putExtra("name", data.get(position).getName());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView img_top_movie;


        ItemClickListener listener;

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }


        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_genre);
            img_top_movie = itemView.findViewById(R.id.img_genre);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            listener.onClick(v);
        }
    }
}

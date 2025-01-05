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
import com.example.moviestreaming.Model.Cast;
import com.example.moviestreaming.R;
import com.example.moviestreaming.ShowDetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCast extends RecyclerView.Adapter<AdapterCast.MyViewholder> {

    Context context;
    List<Cast> data;


    public AdapterCast(Context context, List<Cast> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.name.setText(data.get(position).getName());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView name;
        CircleImageView image;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cast_img);
            name = itemView.findViewById(R.id.cast_name);

        }

    }
}

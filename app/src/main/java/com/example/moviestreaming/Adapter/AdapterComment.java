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
import com.example.moviestreaming.Model.Comment;
import com.example.moviestreaming.Model.TopMovieIMDb;
import com.example.moviestreaming.R;
import com.example.moviestreaming.ShowDetailMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.MyViewholder> {

    Context context;
    List<Comment> data;


    public AdapterComment(Context context, List<Comment> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.email.setText(data.get(position).getEmail());
        holder.comment.setText(data.get(position).getComment());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView email, comment;


        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.txt_email);
            comment = itemView.findViewById(R.id.txt_comment);

        }

    }
}

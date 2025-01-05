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
import com.example.moviestreaming.Model.AllInformation;
import com.example.moviestreaming.R;
import com.example.moviestreaming.ShowDetailSeriesActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterRandomSeries extends RecyclerView.Adapter<AdapterRandomSeries.MyViewHolder> {


    Context context;
    List<AllInformation> data = new ArrayList<>();

    public AdapterRandomSeries(Context context, List<AllInformation> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_series, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.time.setText(data.get(position).getTime());

        Picasso.get().load(data.get(position).getLink_img()).into(holder.img);

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ShowDetailSeriesActivity.class);
                intent.putExtra("name", data.get(position).getName());
                intent.putExtra("director", data.get(position).getDirector());
                intent.putExtra(ShowDetailSeriesActivity.CATEGORY_NAME, data.get(position).getCategory_name());
                intent.putExtra(ShowDetailSeriesActivity.ID_DETAIL_ITEM, data.get(position).getId());
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView time, name;
        ImageView img;

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        ItemClickListener listener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time_movie_imdb);
            name = itemView.findViewById(R.id.name_top_movie_imdb);
            img = itemView.findViewById(R.id.img_top_movie_imdb);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            listener.onClick(v);
        }
    }


}

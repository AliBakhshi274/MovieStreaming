package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreaming.Model.AllInformation;
import com.example.moviestreaming.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.MyViewHolder> {

    Context context;
    List<AllInformation> data;

    public AdapterSearch(Context context, List<AllInformation> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AdapterSearch.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.MyViewHolder holder, int position) {

        holder.txt.setText(data.get(position).getName());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt = itemView.findViewById(R.id.name_search_item);
            img = itemView.findViewById(R.id.img);

        }

    }
}

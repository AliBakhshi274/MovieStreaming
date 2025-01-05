package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.moviestreaming.Model.Intro;
import com.example.moviestreaming.Model.Slider;
import com.example.moviestreaming.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterIntro extends PagerAdapter {

    Context context;
    List<Intro> data;

    public AdapterIntro(Context context, List<Intro> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_intro, container, false);

        CircleImageView img = view.findViewById(R.id.img_intro);
        TextView description = view.findViewById(R.id.txt_description);

        description.setText(data.get(position).getDescription());

        Picasso.get().load(data.get(position).getLink_img()).into(img);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}

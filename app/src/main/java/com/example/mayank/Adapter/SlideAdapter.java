package com.example.mayank.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.mayank.Domain.SliderItems;
import com.example.mayank.R;
import com.google.ai.client.generativeai.type.RequestOptions;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.cli.Options;

import java.util.ArrayList;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {
    private final ArrayList<SliderItems> sliderItems;
    private final ViewPager2 viewPager2;
    private ImageView imageView;
    private Context context;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };

    public SlideAdapter(ArrayList<SliderItems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideAdapter.SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SlideViewHolder(LayoutInflater.from(context).inflate(R.layout.slide_item_container,parent,false));
     }

    @Override
    public void onBindViewHolder(@NonNull SlideAdapter.SlideViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if(position==sliderItems.size() - 2){
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder{
        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
        void setImage(SliderItems sliderItems){
            RequestOptions requestOptions=new RequestOptions();


            Glide.with(context)
                    .load(sliderItems.getUrl())
                    .transform(new CenterCrop())
                    .into(imageView);

        }
    }
}

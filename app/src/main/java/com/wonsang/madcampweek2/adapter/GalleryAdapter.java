package com.wonsang.madcampweek2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.model.Image;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    private Context context;
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_view_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Glide.with(context).load(images.get(position).getValue()).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

class GalleryViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.gallery_view_item_image);
    }

    public ImageView getImageView() {
        return imageView;
    }
}

package com.wonsang.madcampweek2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.api.JsonHeaderRequest;
import com.wonsang.madcampweek2.model.Image;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> implements ApiCallable {

    private Context context;
    private List<Image> images;
    private int deletedImageId;

    public List<Image> getImages() {
        return images;
    }
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public GalleryAdapter(Context context) {
        this.context = context;
        this.images = new ArrayList<>();
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
       holder.getImageView().setOnClickListener(v -> {
           ImageDialog dialog = new ImageDialog(context, images.get(position).getValue(), images.get(position).getId(), this);
           dialog.setCancelable(true);
           dialog.show();
           dialog.setOnDismissListener(dialog1 -> {
               deletedImageId = dialog.getId();
           });
       });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        if(type == ApiProvider.RequestType.DELETE_IMAGE){
            for(int i = 0; i < getImages().size(); i++){
                if(getImages().get(i).getId() == deletedImageId) {
                    getImages().remove(i);
                    notifyItemRemoved(i);
                    break;
                }
            }
        }
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println(error.toString());
    }
}

class GalleryViewHolder extends RecyclerView.ViewHolder {

    private Image image;
    private ImageView imageView;

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.gallery_view_item_image);
    }

    public ImageView getImageView() {
        return imageView;
    }
}

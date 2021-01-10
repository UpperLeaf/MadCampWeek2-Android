package com.wonsang.madcampweek2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wonsang.madcampweek2.R;

public class ImageDialog extends Dialog {
    private ImageView imageView;
    private byte[] image;

    public ImageDialog(Context context, byte[] image) {
        super(context);
        this.image = image;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowAsBehind();
        setContentView(R.layout.image_dialog);

        this.imageView = findViewById(R.id.image_dialog_image);
        Glide.with(getContext()).load(image).into(imageView);
    }

    private void setWindowAsBehind() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
    }
}

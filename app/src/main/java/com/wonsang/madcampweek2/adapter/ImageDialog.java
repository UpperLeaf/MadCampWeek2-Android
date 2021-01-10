package com.wonsang.madcampweek2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.bumptech.glide.Glide;
import com.wonsang.madcampweek2.LoginManagement;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;

public class ImageDialog extends Dialog {
    private ImageView imageView;
    private Button button;
    private ApiProvider apiProvider;
    private ApiCallable apiCallable;
    private byte[] image;
    private int id;


    public int getId() {
        return id;
    }

    public ImageDialog(Context context, byte[] image, int id, ApiCallable apiCallable) {
        super(context);
        this.image = image;
        this.apiProvider = new ApiProvider(context);
        this.apiCallable = apiCallable;
        this.id = id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowAsBehind();
        setContentView(R.layout.image_dialog);

        this.imageView = findViewById(R.id.image_dialog_image);
        this.button = findViewById(R.id.image_dialog_delete_button);

        Glide.with(getContext()).load(image).into(imageView);
        button.setOnClickListener(v -> {
            try {
                apiProvider.deleteImage(LoginManagement.getInstance().getToken(getContext()), id, apiCallable);
                dismiss();
            } catch (AuthFailureError error) {
                error.printStackTrace();
            }
        });
    }

    private void setWindowAsBehind() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
    }
}

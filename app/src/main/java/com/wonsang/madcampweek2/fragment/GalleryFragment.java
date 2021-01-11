package com.wonsang.madcampweek2.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wonsang.madcampweek2.LoginManagement;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.adapter.GalleryAdapter;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class GalleryFragment extends Fragment implements ApiCallable, View.OnClickListener {

    private GalleryAdapter adapter;
    private RecyclerView galleryView;
    private ApiProvider apiProvider;
    private String currentPhotoPath;
    private Animation fab_open, fab_close;
    private FloatingActionButton fab_main, fab_sub1, fab_sub2;

    private boolean isFabOpen = false;
    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();
    public static int CAMERA_REQUEST_CODE = 100;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.apiProvider = new ApiProvider(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context mContext = getActivity().getApplicationContext();
        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryView = view.findViewById(R.id.gallery_view);
        galleryView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new GalleryAdapter(getContext());
        galleryView.setAdapter(adapter);
        fab_main =  view.findViewById(R.id.fab_main);
        fab_sub1 =  view.findViewById(R.id.fab_sub1);
        fab_sub2 =  view.findViewById(R.id.fab_sub2);
        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        apiProvider.getAllImages(LoginManagement.getInstance().getToken(getContext()),this);
        fab_sub1.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = createImageFile();
            Uri photoUri = FileProvider.getUriForFile(getContext(), "com.wonsang.madcampweek2.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            getActivity().startActivityForResult(intent, CAMERA_REQUEST_CODE);
        });
    }

    private File createImageFile() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public void getResponse(ApiProvider.RequestType type, Object response) {
        if(type == ApiProvider.RequestType.GET_ALL_IMAGES){
            List<Image> images = new ArrayList<>();
            try {
                JSONArray jsonArray = (JSONArray)response;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String image = jsonObject.getString("image");
                    int id = jsonObject.getInt("id");
                    byte[] decodeImage = decoder.decode(image);
                    images.add(new Image(title, decodeImage, id));
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }
            adapter.setImages(images);
            adapter.notifyDataSetChanged();
        } else if(type == ApiProvider.RequestType.ADD_IMAGE){
            try {
                JSONObject object = (JSONObject)response;
                String title = object.getString("title");
                String image = object.getString("image");
                int id = object.getInt("id");
                byte[] decodeImage = decoder.decode(image);
                int pos = adapter.getItemCount();
                adapter.getImages().add(new Image(title, decodeImage, id));
                adapter.notifyItemInserted(pos);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println(error);
    }


    public void capture() {
        Uri uri = Uri.fromFile(new File(currentPhotoPath));
        byte[] image = getImageContent(uri);
        byte[] encodedImage = encoder.encode(image);
        String token = LoginManagement.getInstance().getToken(getContext());
        apiProvider.addImage(token
                ,currentPhotoPath
                ,new String(encodedImage)
                ,this);
    }


    public byte[] getImageContent(Uri uri){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fab_main:
                toggleFab();
                break;

            case R.id.fab_sub1:
                toggleFab();
                Toast.makeText(getActivity(), "Camera Open-!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sub2:
                toggleFab();
                Toast.makeText(getActivity(), "Gallery Open-!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    private void toggleFab() {
        Log.d("hi", "hi");

        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.ic_add);
            fab_sub1.startAnimation(fab_close);
            fab_sub2.startAnimation(fab_close);
            fab_sub1.setClickable(false);
            fab_sub2.setClickable(false);
            isFabOpen = false;

        } else {
            fab_main.setImageResource(R.drawable.ic_close);
            fab_sub1.startAnimation(fab_open);
            fab_sub2.startAnimation(fab_open);
            fab_sub1.setClickable(true);
            fab_sub2.setClickable(true);
            isFabOpen = true;

        }
    }
}
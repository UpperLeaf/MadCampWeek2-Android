package com.wonsang.madcampweek2.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.wonsang.madcampweek2.AccountDatabase;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.adapter.GalleryAdapter;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.api.JsonHeaderRequest;
import com.wonsang.madcampweek2.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment implements ApiCallable {

    private GalleryAdapter adapter;
    private RecyclerView galleryView;
    private ApiProvider apiProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.apiProvider = new ApiProvider(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryView = view.findViewById(R.id.gallery_view);
        galleryView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new GalleryAdapter(getContext());
        apiProvider.getAllImages(AccountDatabase.getAppDatabase(getContext()).AccountDataDao().findAccountDataLimitOne().getToken(),this);
    }

    @Override
    public void getResponse(ApiProvider.RequestType type, JsonHeaderRequest.JsonHeaderObject response) {
        if(type == ApiProvider.RequestType.GET_ALL_IMAGES){
            List<Image> images = new ArrayList<>();
            try {
                JSONArray jsonArray = response.getResponse();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String image = jsonObject.getString("image");
                    images.add(new Image(title, image));
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }
            adapter.setImages(images);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getError(VolleyError error) {
        System.out.println(error);
    }
}
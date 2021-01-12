package com.wonsang.madcampweek2.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wonsang.madcampweek2.AddPostActivity;
import com.wonsang.madcampweek2.LoginManagement;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.adapter.BlogContactAdapter;
import com.wonsang.madcampweek2.adapter.BlogPostAdapter;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Blog;
import com.wonsang.madcampweek2.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Fragment3 extends Fragment implements ApiCallable<JSONObject> , View.OnClickListener {

    private ApiProvider apiProvider;
    private BlogContactAdapter contactAdapter;
    private RecyclerView postRecyclerView;
    private BlogPostAdapter postListAdapter;
    private ImageView profileImageView;
    private ImageView bannerImageView;
    private TextView description;
    private TextView blogTitle;
    private Animation fab_open, fab_close;
    private FloatingActionButton fab_main, fab_sub1, fab_sub2;

    private TextView postTitle;
    private TextView postContent;

    private static Base64.Decoder decoder;

    public static int POST_ADD_REQUEST = 400;
    public static int PROFILE_EDIT_REQUEST = 319;
    private boolean isFabOpen = false;

    public Fragment3() {
        decoder = Base64.getDecoder();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext = getActivity().getApplicationContext();
        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        apiProvider = new ApiProvider(getContext());
    }



      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_3, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview3);
        description = rootView.findViewById(R.id.description);
        blogTitle = rootView.findViewById(R.id.title);
        profileImageView = rootView.findViewById(R.id.profileImage);
        bannerImageView = rootView.findViewById(R.id.bannerImage);

        recyclerView.setHasFixedSize(true);
        String token = LoginManagement.getInstance().getToken(getContext());


        contactAdapter = new BlogContactAdapter(getContext());

        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postListAdapter = new BlogPostAdapter(getContext(), this);

        postRecyclerView = rootView.findViewById(R.id.recyclerview2);
        postRecyclerView.setHasFixedSize(true);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postRecyclerView.setAdapter(postListAdapter);

        apiProvider.getMyBlog(token, this);
        postTitle = rootView.findViewById(R.id.post_title);
        postContent = rootView.findViewById(R.id.post_content);
        fab_main =  rootView.findViewById(R.id.fab_main3);
        fab_sub1 =  rootView.findViewById(R.id.fab_sub13);
        fab_sub2 =  rootView.findViewById(R.id.fab_sub23);
        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
//        fab_sub1.setOnClickListener(v -> {
//            //TODO: EditProfileActivity
//        });
//        fab_sub1.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), AddPostActivity.class);
//            getActivity().startActivityForResult(intent, POST_ADD_REQUEST);
//        });

        return rootView;
    }

    private void setBlogData(Blog blog) {
        blogTitle.setText(blog.getTitle());
        description.setText(blog.getDescription());
        if(!blog.getBannerImage().equals("null")){
            byte[] bannerImage = decoder.decode(blog.getBannerImage());
            Glide.with(getContext()).load(bannerImage).into(bannerImageView);
        }
        else
            Glide.with(getContext()).load(R.drawable.banner_image3).into(bannerImageView);
        Glide.with(getContext()).load(blog.getProfileImageUrl()).into(profileImageView);
        postListAdapter.setList(blog.posts);
        postListAdapter.notifyDataSetChanged();
    }

    public void update(String title, String content) {
        postTitle.setText(title);
        postContent.setText(content);
    }


    @Override
    public void getResponse(ApiProvider.RequestType type, JSONObject response) {
        Blog blog = new Blog();
        try {
            String blogTitle = response.getString("title");
            String description = response.getString("description");
            String bannerImage = response.getString("bannerImage");
            String profileImageUrl = response.getString("userImageUrl");
            List<Post> postList = new ArrayList<>();
            JSONArray jsonArray = response.getJSONArray("post");
            for (int i = 0; i < jsonArray.length(); i++) {
                String postTitle = jsonArray.getJSONObject(i).getString("title");
                String postContent = jsonArray.getJSONObject(i).getString("content");
                Post post = new Post(postTitle, postContent);
                postList.add(post);
            }
            blog.setTitle(blogTitle);
            blog.setDescription(description);
            blog.setBannerImage(bannerImage);
            blog.setProfileImageUrl(profileImageUrl);
            blog.setPosts(postList);

            int postSize = postList.size();
            if(postSize >= 1){
                Post post = postList.get(postSize - 1);
                postTitle.setText(post.getTitle());
                postContent.setText(post.getContent());
            }
            setBlogData(blog);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void getError(VolleyError error) {

    }

    public void notifyAddPost(Post post) {
        int pos = postListAdapter.getList().size();
        postListAdapter.getList().add(post);
        postListAdapter.notifyItemInserted(pos);

        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
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
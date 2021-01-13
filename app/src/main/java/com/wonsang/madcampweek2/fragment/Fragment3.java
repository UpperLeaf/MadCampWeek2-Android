package com.wonsang.madcampweek2.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wonsang.madcampweek2.AddPostActivity;
import com.wonsang.madcampweek2.EditPostActivity;
import com.wonsang.madcampweek2.EditProfileActivity;
import com.wonsang.madcampweek2.LoginManagement;
import com.wonsang.madcampweek2.R;
import com.wonsang.madcampweek2.Updatable;
import com.wonsang.madcampweek2.adapter.BlogContactAdapter;
import com.wonsang.madcampweek2.adapter.BlogPostAdapter;
import com.wonsang.madcampweek2.api.ApiCallable;
import com.wonsang.madcampweek2.api.ApiProvider;
import com.wonsang.madcampweek2.model.Blog;
import com.wonsang.madcampweek2.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Fragment3 extends Fragment implements ApiCallable<JSONObject> , View.OnClickListener, Updatable {

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

    private CardView postCard;

    private Post post;
    private TextView postTitle;
    private TextView postContent;

    private static Base64.Decoder decoder;

    public static int POST_ADD_REQUEST = 400;
    public static int EDIT_POST_REQUEST = 401;
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
        postCard = rootView.findViewById(R.id.post_card);

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

        fab_main = rootView.findViewById(R.id.fab_main3);
        fab_sub1 = rootView.findViewById(R.id.fab_sub23);
        fab_sub2 = rootView.findViewById(R.id.fab_sub13);

        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);

        fab_sub1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            getActivity().startActivityForResult(intent, PROFILE_EDIT_REQUEST);
        });
        fab_sub2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddPostActivity.class);
            getActivity().startActivityForResult(intent, POST_ADD_REQUEST);
        });
        postCard.setOnLongClickListener(v -> {
              Context context = v.getContext();
              apiProvider = new ApiProvider(context);

              AlertDialog.Builder builder =
                      new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert)
                              .setMessage(post.getTitle() + "을 " + "삭제 하시겠습니까?")
                              .setPositiveButton("네", (dialog, which) -> {
                                  apiProvider.deletePost(token, post.id);
                                  for(int i = 0; i < postListAdapter.getList().size(); i++){
                                      if(postListAdapter.getList().get(i).id == post.id){
                                          postListAdapter.getList().remove(i);
                                          postListAdapter.notifyItemRemoved(i);
                                          break;
                                      }
                                  }
                                  if(postListAdapter.getList().size() >= 1) {
                                      Post post = postListAdapter.getList().get(postListAdapter.getList().size() - 1);
                                      this.post = post;
                                      this.postContent.setText(post.getContent());
                                      this.postTitle.setText(post.getTitle());
                                  }else {
                                      this.post = null;
                                      this.postContent.setText("");
                                      this.postTitle.setText("");
                                  }

                                  dialog.dismiss();
                              })
                              .setNegativeButton("아니오",
                                      (dialog, id) -> dialog.cancel());

              AlertDialog alert = builder.create();
              alert.setTitle("게시물 삭제");
              alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 102, 102, 102)));
              alert.show();

              return true;
          });
        postCard.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditPostActivity.class);
            intent.putExtra("id", post.id);
            intent.putExtra("title", post.getTitle());
            getActivity().startActivityForResult(intent, EDIT_POST_REQUEST);
        });
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

    @Override
    public void update(Post post) {
        this.post = post;
        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
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
                int postId = jsonArray.getJSONObject(i).getInt("id");
                String postTitle = jsonArray.getJSONObject(i).getString("title");
                String postContent = jsonArray.getJSONObject(i).getString("content");
                Post post = new Post(postTitle, postContent, postId);
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
                this.post = post;
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
        System.out.println(error);
    }

    public void notifyAddPost(Post post) {
        int pos = postListAdapter.getList().size();
        postListAdapter.getList().add(post);
        postListAdapter.notifyItemInserted(pos);

        this.post = post;
        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main3:
                toggleFab();
                break;
            case R.id.fab_sub13:
                toggleFab();
                Toast.makeText(getActivity(), "Camera Open-!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_sub23:
                toggleFab();
                Toast.makeText(getActivity(), "Gallery Open-!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    private void toggleFab() {
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

    public void notifyEditProfile(String title, String description) {
        this.blogTitle.setText(title);
        this.description.setText(description);
    }

    public void notifyEditPost(String title, String content) {
        this.postTitle.setText(title);
        this.postContent.setText(content);

        this.post.title = title;
        this.post.content = content;

        for(int i = 0; i < postListAdapter.getList().size(); i++){
            if(postListAdapter.getList().get(i).id == post.id){
                postListAdapter.notifyItemChanged(i);
                break;
            }
        }
    }
}